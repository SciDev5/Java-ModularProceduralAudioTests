package me.scidev5.modularProceduralAudioTests;

import me.scidev5.modularProceduralAudioTests.nodes.AudioNode;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.AudioData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatStereoData;

public abstract class AudioContext {
    public static final int executionIDResetValue = -1;
    private int executionID = executionIDResetValue;

    protected float[] dataL;
    protected float[] dataR;
    public final DestinationNode destination;

    protected AudioContext() {
        destination = new DestinationNode();
    }


    public int getExecutionID() {
        return executionID;
    }
    public abstract int getChunkLength();
    public abstract float getSampleRate();

    protected void execute() {
        this.executionID++;
        this.destination.execute();
    }

    public class DestinationNode extends AudioNode {
        private DestinationNode() {
            super(AudioContext.this);
        }

        @Override
        public String getName() {
            return "Destination [" + getSampleRate() / 1000 + "khz]";
        }

        @Override
        public String[] getInputNames() {
            return new String[] { "Stereo" };
        }

        @Override
        public String[] getOutputNames() {
            return new String[] { };
        }

        @Override
        public String[] getInputTypes() {
            return new String[] { AudioData.FLOAT_STEREO_ID };
        }

        @Override
        public String[] getOutputTypes() {
            return new String[] { };
        }

        @Override
        public void createOutputs() { }

        @Override
        public void internalExecute() {
            FloatStereoData input = (FloatStereoData) inputData[0];
            if (input == null) return;

            final int len = getChunkLength();
            dataL = new float[len];
            dataR = new float[len];
            input.getData(dataL, dataR);
        }
    }
}
