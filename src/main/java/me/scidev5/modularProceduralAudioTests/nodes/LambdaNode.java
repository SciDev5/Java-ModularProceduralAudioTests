package me.scidev5.modularProceduralAudioTests.nodes;

import me.scidev5.modularProceduralAudioTests.AudioContext;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.AudioData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.IntData;

public class LambdaNode extends AudioNode {

    Function4f4i_f lambda = null;

    @FunctionalInterface public interface Function4f4i_f { float eval(float a, float b, float c, float d, int e, int f, int g, int h); }

    public LambdaNode(AudioContext context, Function4f4i_f lambda) {
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
        outputData[0] = new FloatData(context.getChunkLength());
    }

    @Override
    protected void internalExecute() {
        int len = context.getChunkLength();
        float[] a = new float[len]; if (inputData[0] != null) ((FloatData)inputData[0]).getData(a);
        float[] b = new float[len]; if (inputData[1] != null) ((FloatData)inputData[1]).getData(b);
        float[] c = new float[len]; if (inputData[2] != null) ((FloatData)inputData[2]).getData(c);
        float[] d = new float[len]; if (inputData[3] != null) ((FloatData)inputData[3]).getData(d);
        int[] e = new int[len]; if (inputData[4] != null) ((IntData)inputData[4]).getData(e);
        int[] f = new int[len]; if (inputData[5] != null) ((IntData)inputData[5]).getData(f);
        int[] g = new int[len]; if (inputData[6] != null) ((IntData)inputData[6]).getData(g);
        int[] h = new int[len]; if (inputData[7] != null) ((IntData)inputData[7]).getData(h);
        float[] out = new float[len];

        for (int i = 0; i < len; i++)
            out[i] = lambda.eval(a[i],b[i],c[i],d[i],e[i],f[i],g[i],h[i]);

        ((FloatData)outputData[0]).setData(out);
    }
}
