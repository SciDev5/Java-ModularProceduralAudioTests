package me.scidev5.modularProceduralAudioTests.nodes;

import me.scidev5.modularProceduralAudioTests.AudioContext;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.AudioData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatData;

public class DelayNode extends AudioNode {

    public static final int maxDelaySamples = 96000;

    private float[] savedData = new float[maxDelaySamples];

    public DelayNode(AudioContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "Delay";
    }

    @Override
    public String[] getInputNames() {
        return new String[] { "Input", "Delay Samples"};
    }

    @Override
    public String[] getOutputNames() {
        return new String[] { "Output" };
    }

    @Override
    public String[] getInputTypes() {
        return new String[] {AudioData.FLOAT_ID,AudioData.INT_ID};
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
        int now = (int) (sampleNumber % maxDelaySamples); if (now < 0) return;
        int[] delayIn = getIntData(1,len);
        float[] dataIn = getFloatData(0,len);

        if (now + len < maxDelaySamples)
            System.arraycopy(dataIn,0,savedData,now,len);
        else {
            int len2 = now + len - maxDelaySamples;
            int len1 = len - len2;
            System.arraycopy(dataIn,0,savedData,now,len2);
            System.arraycopy(dataIn,len2,savedData,0,len1);
        }

        float[] out = new float[len];
        for (int i = 0; i < len; i++) {
            int delayAmount = Math.max(0,Math.min(maxDelaySamples-1,delayIn[i]));
            out[i] = savedData[(now + i - delayAmount) % maxDelaySamples];
        }

        setFloatData(0,out);
    }

    @Override
    protected void resetInternal(int sampleNumber) {
        savedData = new float[maxDelaySamples];
    }
}
