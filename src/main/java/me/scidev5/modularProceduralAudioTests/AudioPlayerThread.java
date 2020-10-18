package me.scidev5.modularProceduralAudioTests;

import me.scidev5.modularProceduralAudioTests.nodes.AudioNode;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.AudioData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatStereoData;

import javax.sound.sampled.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class AudioPlayerThread extends Thread {

    public final DestinationNode destination;
    private final List<Float> outBufferL;
    private final List<Float> outBufferR;

    public static final int executionIDResetValue = -1;
    private int executionID = executionIDResetValue;

    private SourceDataLine sdl = null;

    public AudioPlayerThread() {
        outBufferL = new ArrayList<>();
        outBufferR = new ArrayList<>();
        destination = new DestinationNode();
    }

    @Override
    public void run() {
        AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,getSampleRate(),16,2,4,getSampleRate(),false);
        if (sdl != null) throw new IllegalStateException("SDL already created!");
        try {
            sdl = AudioSystem.getSourceDataLine(audioFormat);
            sdl.open();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }
        sdl.start();
        int startAvailability = sdl.available();
        try {
            while (true) {
                int len = getChunkLength();
                ByteBuffer buffer = ByteBuffer.allocate(len*4);
                buffer.order(ByteOrder.LITTLE_ENDIAN);

                execute();
                for (int i = 0; i < len; i++) {
                    buffer.putShort((short)(((outBufferL.get(i) + 1f) / 2f) * 0xFFFF - 0x8000));// L
                    buffer.putShort((short)(((outBufferR.get(i) + 1f) / 2f) * 0xFFFF - 0x8000));// R
                }

                sdl.write(buffer.array(),0,len*4);

                try { // I am sorry there is no better way to do this.
                    while (startAvailability-sdl.available() > 5 * getChunkLength() * 4) {
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        }  finally {
            sdl.stop();
            sdl.close();
        }
    }

    private void execute() {
        executionID++;
        destination.execute();
    }


    // ----- GETTERS ----- //
    public int getChunkLength() {
        return 512;
    }
    public int getExecutionID() {
        return executionID;
    }
    public float getSampleRate() {
        return 48000f;
    }


    public class DestinationNode extends AudioNode {

        private final List<Float> outBufferL;
        private final List<Float> outBufferR;

        private DestinationNode() {
            super(AudioPlayerThread.this);
            outBufferL = AudioPlayerThread.this.outBufferL;
            outBufferR = AudioPlayerThread.this.outBufferR;
        }

        @Override
        public String getName() {
            return "Destination [48khz]";
        }

        @Override
        public String[] getInputNames() {
            return new String[] { "Stereo" };
        }

        @Override
        public String[] getOutputNames() {
            return new String[0];
        }

        @Override
        public Class<? extends AudioData>[] getInputTypes() {
            return new Class[] { FloatStereoData.class };
        }

        @Override
        public Class<? extends AudioData>[] getOutputTypes() {
            return new Class[0];
        }

        @Override
        public void createOutputs() { }

        @Override
        public void internalExecute() {
            outBufferL.clear();
            outBufferR.clear();
            final int len = context.getChunkLength();
            float[] dataL = new float[len];
            float[] dataR = new float[len];
            FloatStereoData input = (FloatStereoData) inputData[0];
            if (input == null) {
                for (int i = 0; i < len; i++) {
                    outBufferL.add(i, 0f);
                    outBufferR.add(i, 0f);
                }
                return;
            }
            input.getData(dataL,dataR);
            for (int i = 0; i < len; i++) {
                outBufferL.add(i, dataL[i]);
                outBufferR.add(i, dataR[i]);
            }
        }
    }
}
