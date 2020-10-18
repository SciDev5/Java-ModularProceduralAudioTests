package me.scidev5.modularProceduralAudioTests.nodes;

import me.scidev5.modularProceduralAudioTests.AudioPlayerThread;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.AudioData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatStereoData;

public class OscillatorNode extends AudioNode {

    private float distThroughSample = 0;

    public OscillatorNode(AudioPlayerThread context) {
        super(context);
    }

    @Override
    public String getName() {
        return "Stereo Oscillator";
    }

    @Override
    public String[] getInputNames() {
        return new String[] {"Frequency"};
    }

    @Override
    public String[] getOutputNames() {
        return new String[] {"Oscillation"};
    }

    @Override
    public Class<? extends AudioData>[] getInputTypes() {
        return new Class[] {FloatData.class};
    }

    @Override
    public Class<? extends AudioData>[] getOutputTypes() {
        return new Class[] { FloatData.class };
    }

    @Override
    public void createOutputs() {
        outputData[0] = new FloatData(context.getChunkLength());
    }

    @Override
    protected void internalExecute() {
        int len = context.getChunkLength();
        FloatData output = (FloatData) outputData[0];
        FloatData frequency = (FloatData) inputData[0];
        float[] out = new float[len];
        float[] freq = new float[len];
        frequency.getData(freq);

        for (int i = 0; i < output.getLength(); i++) {
            out[i] = distThroughSample * 2 - 1;
            distThroughSample = (distThroughSample + freq[i] / context.getSampleRate()) % 1;
        }

        output.setData(out);
    }

    @Override
    protected void resetInternal(int sampleNumber) {
        distThroughSample = 0f;
    }
}
