package me.scidev5.modularProceduralAudioTests;

import javax.sound.sampled.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class AudioPlayerThread extends AudioContext {

    private SourceDataLine sdl = null;

    public AudioPlayerThread() {
        super();
    }

    public void start() {
        AudioFormat audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,getSampleRate(),16,2,4,getSampleRate(),false);
        if (sdl != null) throw new IllegalStateException("SDL already created!");
        try {
            sdl = AudioSystem.getSourceDataLine(audioFormat);
            sdl.open(audioFormat,512*8);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            return;
        }
        sdl.start();
        try {
            while (sdl.isOpen()) {
                int len = getChunkLength();
                ByteBuffer buffer = ByteBuffer.allocate(len*4);
                buffer.order(ByteOrder.LITTLE_ENDIAN);

                execute();
                for (int i = 0; i < len; i++) {
                    buffer.putShort((short)(((dataL[i] + 1f) / 2f) * 0xFFFF - 0x8000));
                    buffer.putShort((short)(((dataR[i] + 1f) / 2f) * 0xFFFF - 0x8000));
                }

                sdl.write(buffer.array(),0,len*4);
            }
        } finally {
            sdl.stop();
            sdl.close();
        }
    }

    public void stop() {
        sdl.stop();
        sdl.close();
    }

    public int getChunkLength() {
        return 512;
    }
    public float getSampleRate() {
        return 48000f;
    }
}
