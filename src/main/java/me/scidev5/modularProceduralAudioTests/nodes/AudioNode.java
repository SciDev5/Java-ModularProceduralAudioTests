package me.scidev5.modularProceduralAudioTests.nodes;

import me.scidev5.modularProceduralAudioTests.AudioPlayerThread;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.AudioData;

public abstract class AudioNode {

    protected final AudioPlayerThread context;

    protected final AudioData[] inputData = new AudioData[getNumInputs()];
    protected final AudioData[] outputData = new AudioData[getNumOutputs()];

    public abstract Class<? extends AudioData>[] getInputTypes();
    public abstract Class<? extends AudioData>[] getOutputTypes();
    public final int getNumInputs() {
        Object[] dataTypes = getInputTypes();
        if (dataTypes == null) return 0;
        return dataTypes.length;
    }
    public final int getNumOutputs() {
        Object[] dataTypes = getOutputTypes();
        if (dataTypes == null) return 0;
        return dataTypes.length;
    }

    public abstract void createOutputs();
    public final AudioData getOutput(int index) {
        if (index >= outputData.length || index < 0) return null;
        return outputData[index];
    }
    public final AudioData setInput(int index, AudioData data) {
        if (index >= inputData.length || index < 0) return null;
        if (data == null) throw new IllegalArgumentException("AudioData given is null.");
        if (getInputTypes()[index].isInstance(data))
            throw new IllegalArgumentException("Data type for given AudioData does not match required type.");
        return outputData[index] = data;
    }

    public abstract void execute();

    protected AudioNode(AudioPlayerThread context) {
        this.context = context;
        createOutputs();
    }
}
