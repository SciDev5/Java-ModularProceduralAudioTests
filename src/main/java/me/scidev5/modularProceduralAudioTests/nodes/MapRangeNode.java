package me.scidev5.modularProceduralAudioTests.nodes;

import me.scidev5.modularProceduralAudioTests.AudioContext;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.AudioData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatData;

public class MapRangeNode extends AudioNode {
    public MapRangeNode(AudioContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "Map Range";
    }

    @Override
    public String[] getInputNames() {
        return new String[] {"Input","Min In","Max In","Min Out","Max Out"};
    }

    @Override
    public String[] getOutputNames() {
        return new String[] {"Output"};
    }

    @Override
    public String[] getInputTypes() {
        return new String[] {AudioData.FLOAT_ID,AudioData.FLOAT_ID,AudioData.FLOAT_ID,AudioData.FLOAT_ID,AudioData.FLOAT_ID};
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
        float[] valueIn = getFloatData(0,len);
        float[] minIn = getFloatData(1,len);
        float[] maxIn = getFloatData(2,len);
        float[] minOut = getFloatData(3,len);
        float[] maxOut = getFloatData(4,len);
        float[] out = new float[len];

        for (int i = 0; i < len; i++) {
            float intermediate = (valueIn[i] - minIn[i]) / (maxIn[i] - minIn[i]);
            out[i] = intermediate * (maxOut[i] - minOut[i]) + minOut[i];
        }

        setFloatData(0,out);
    }
}
