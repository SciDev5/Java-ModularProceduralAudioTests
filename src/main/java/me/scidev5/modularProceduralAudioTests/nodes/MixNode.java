package me.scidev5.modularProceduralAudioTests.nodes;

import me.scidev5.modularProceduralAudioTests.AudioContext;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.AudioData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatData;

public class MixNode extends AudioNode {

    public enum Mode {
        CONST_POWER("Constant Power"),
        LINEAR("Linear");

        private final String str;
        Mode (String str) { this.str = str; }

        public String toString() { return str; }
    }
    protected final Mode mode;

    public MixNode(AudioContext context, Mode mode) {
        super(context);
        this.mode = mode;
    }

    @Override
    public String getName() {
        return "Mix ("+mode+")";
    }

    @Override
    public String[] getInputNames() {
        return new String[] { "In A", "In B", "Mix Factor"};
    }

    @Override
    public String[] getOutputNames() {
        return new String[] { "Mixed" };
    }

    @Override
    public String[] getInputTypes() {
        return new String[] {AudioData.FLOAT_ID, AudioData.FLOAT_ID, AudioData.FLOAT_ID};
    }

    @Override
    public String[] getOutputTypes() {
        return new String[] {AudioData.FLOAT_ID};
    }

    @Override
    public void createOutputs() {
        outputData[0] = new FloatData();
    }

    @Override
    protected void internalExecute() {
        int len = context.getChunkLength();
        float[] inputA = getFloatData(0,len);
        float[] inputB = getFloatData(1,len);
        float[] blendFactor = getFloatData(2,len);
        float[] out = new float[len];

        switch (mode) {
            case CONST_POWER:
                final float SQRT2OVER2 = 0.707106781f;
                for (int i = 0; i < len; i++) {
                    double blendFac = (Math.max(0,Math.min(1,blendFactor[i])) - 0.5) * Math.PI;
                    float sin = (float) Math.sin(blendFac);
                    float cos = (float) Math.cos(blendFac);
                    float aFactor = SQRT2OVER2*(cos-sin);
                    float bFactor = SQRT2OVER2*(cos+sin);
                    out[i] = inputA[i] * aFactor + inputB[i] * bFactor;
                }
                break;
            case LINEAR:
                for (int i = 0; i < len; i++) {
                    float blendFac = Math.max(0, Math.min(1, blendFactor[i]));
                    out[i] = inputA[i] + blendFac * (inputB[i] - inputA[i]);
                }
                break;
        }

        setFloatData(0,out);
    }
}
