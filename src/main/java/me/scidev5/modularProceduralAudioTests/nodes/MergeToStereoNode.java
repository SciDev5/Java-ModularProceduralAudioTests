package me.scidev5.modularProceduralAudioTests.nodes;

import me.scidev5.modularProceduralAudioTests.AudioContext;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.AudioData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatStereoData;

public class MergeToStereoNode extends AudioNode {

    public MergeToStereoNode(AudioContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String[] getInputNames() {
        return new String[] { "Data L", "Data R" };
    }

    @Override
    public String[] getOutputNames() {
        return new String[] { "Combined" };
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
        float[] inputL = getFloatData(0,len);
        float[] inputR = getFloatData(1,len);

        setStereoFloatData(0,inputL,inputR);
    }
}
