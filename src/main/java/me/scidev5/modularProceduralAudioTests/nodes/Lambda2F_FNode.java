package me.scidev5.modularProceduralAudioTests.nodes;

import me.scidev5.modularProceduralAudioTests.AudioContext;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.AudioData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatData;

public class Lambda2F_FNode extends AudioNode {

    Function2f_f lambda;

    @FunctionalInterface public interface Function2f_f { float eval(float a, float b); }

    public Lambda2F_FNode(AudioContext context, Function2f_f lambda) {
        super(context);
        this.lambda = lambda;
    }

    @Override
    public String getName() {
        return "Lambda Node";
    }

    @Override
    public String[] getInputNames() {
        return new String[] { "Input A (float)", "Input B (float)" };
    }

    @Override
    public String[] getOutputNames() {
        return new String[] { "Output (float)" };
    }

    @Override
    public String[] getInputTypes() {
        return new String[] { AudioData.FLOAT_ID, AudioData.FLOAT_ID };
    }

    @Override
    public String[] getOutputTypes() {
        return new String[] { AudioData.FLOAT_ID };
    }

    @Override
    public void createOutputs() {
        outputData[0] = new FloatData();
    }

    @Override
    protected void internalExecute() {
        int len = context.getChunkLength();
        float[] a = getFloatData(0,len);
        float[] b = getFloatData(1,len);
        float[] out = new float[len];

        for (int i = 0; i < len; i++)
            out[i] = lambda.eval(a[i],b[i]);

        setFloatData(0,out);
    }
}
