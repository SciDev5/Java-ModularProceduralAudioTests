package me.scidev5.modularProceduralAudioTests.nodes;

import me.scidev5.modularProceduralAudioTests.AudioContext;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.AudioData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatData;

public class Lambda4F4I_FNode extends AudioNode {

    Function4f4i_f lambda;

    @FunctionalInterface public interface Function4f4i_f { float eval(float a, float b, float c, float d, int e, int f, int g, int h); }

    public Lambda4F4I_FNode(AudioContext context, Function4f4i_f lambda) {
        super(context);
        this.lambda = lambda;
    }

    @Override
    public String getName() {
        return "Lambda Node";
    }

    @Override
    public String[] getInputNames() {
        return new String[] { "Input A (float)", "Input B (float)", "Input C (float)", "Input D (float)", "Input E (int)", "Input F (int)", "Input G (int)", "Input H (int)" };
    }

    @Override
    public String[] getOutputNames() {
        return new String[] { "Output (float)" };
    }

    @Override
    public String[] getInputTypes() {
        return new String[] { AudioData.FLOAT_ID, AudioData.FLOAT_ID, AudioData.FLOAT_ID, AudioData.FLOAT_ID, AudioData.INT_ID, AudioData.INT_ID, AudioData.INT_ID, AudioData.INT_ID };
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
        float[] c = getFloatData(2,len);
        float[] d = getFloatData(3,len);
        int[] e = getIntData(4,len);
        int[] f = getIntData(5,len);
        int[] g = getIntData(6,len);
        int[] h = getIntData(7,len);
        float[] out = new float[len];

        for (int i = 0; i < len; i++)
            out[i] = lambda.eval(a[i],b[i],c[i],d[i],e[i],f[i],g[i],h[i]);

        setFloatData(0,out);
    }
}
