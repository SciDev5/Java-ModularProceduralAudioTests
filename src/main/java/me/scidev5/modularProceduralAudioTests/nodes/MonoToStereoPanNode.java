package me.scidev5.modularProceduralAudioTests.nodes;

import me.scidev5.modularProceduralAudioTests.AudioContext;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.AudioData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatStereoData;

public class MonoToStereoPanNode extends AudioNode {

    public MonoToStereoPanNode(AudioContext context) {
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
    public String[] getInputTypes() {
        return new String[] { AudioData.FLOAT_ID, AudioData.FLOAT_ID };
    }

    @Override
    public String[] getOutputTypes() {
        return new String[] { AudioData.FLOAT_STEREO_ID };
    }

    @Override
    public void createOutputs() {
        outputData[0] = new FloatStereoData();
    }

    @Override
    protected void internalExecute() {
        int len = context.getChunkLength();
        float[] input = getFloatData(0,len);
        float[] panning = getFloatData(1,len);
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

        setStereoFloatData(0,outputL,outputR);
    }
}
