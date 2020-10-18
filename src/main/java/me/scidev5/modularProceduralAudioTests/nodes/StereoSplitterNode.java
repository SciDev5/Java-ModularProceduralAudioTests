package me.scidev5.modularProceduralAudioTests.nodes;

import me.scidev5.modularProceduralAudioTests.AudioPlayerThread;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.AudioData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatStereoData;

public class StereoSplitterNode extends AudioNode {

    public StereoSplitterNode(AudioPlayerThread context) {
        super(context);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String[] getInputNames() {
        return new String[] { "Data In", "Panning (1=R;-1=L)" };
    }

    @Override
    public String[] getOutputNames() {
        return new String[] { "Data Out" };
    }

    @Override
    public Class<? extends AudioData>[] getInputTypes() {
        return new Class[] { FloatData.class, FloatData.class };
    }

    @Override
    public Class<? extends AudioData>[] getOutputTypes() {
        return new Class[] { FloatStereoData.class };
    }

    @Override
    public void createOutputs() {
        outputData[0] = new FloatStereoData(context.getChunkLength());
    }

    @Override
    protected void internalExecute() {
        int len = context.getChunkLength();
        FloatStereoData output_ = (FloatStereoData) outputData[0];
        FloatData input_ = (FloatData) inputData[0];
        FloatData panning_ = (FloatData) inputData[1];
        float[] input = input_.getData(new float[len]);
        float[] panning = panning_.getData(new float[len]);
        float[] outputL = new float[len];
        float[] outputR = new float[len];

        final float sqrt2over2 = 0.707106781f;
        for (int i = 0; i < len; i++) {
            double pan = Math.min(1,Math.max(-1,panning[i])) * Math.PI / 4;
            float sin = (float) Math.sin(pan);
            float cos = (float) Math.cos(pan);
            outputL[i] = sqrt2over2 * input[i] * (cos - sin);
            outputR[i] = sqrt2over2 * input[i] * (cos + sin);
        }

        output_.setData(outputL,outputR);
    }
}
