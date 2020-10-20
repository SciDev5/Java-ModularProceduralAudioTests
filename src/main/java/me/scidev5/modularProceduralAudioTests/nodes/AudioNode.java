package me.scidev5.modularProceduralAudioTests.nodes;

import me.scidev5.modularProceduralAudioTests.AudioContext;
import me.scidev5.modularProceduralAudioTests.AudioPlayerThread;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.AudioData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatStereoData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.IntData;

public abstract class AudioNode {

    protected final AudioContext context;
    protected long sampleNumber = 0;
    private int executionIndex = AudioPlayerThread.executionIDResetValue;

    private boolean executing = false;
    private boolean resetting = false;
    private final AudioNode[] children = new AudioNode[getNumInputs()];

    protected final AudioData[] inputData = new AudioData[getNumInputs()];
    protected final AudioData[] outputData = new AudioData[getNumOutputs()];

    public abstract String getName();
    public abstract String[] getInputNames();
    public abstract String[] getOutputNames();
    public abstract String[] getInputTypes();
    public abstract String[] getOutputTypes();
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
    protected final void setInput(int index, AudioData data) {
        if (index >= inputData.length || index < 0) return;
        if (data == null) throw new IllegalArgumentException("AudioData given is null.");
        if (!data.ofType(getInputTypes()[index]))
            throw new IllegalArgumentException("Data type for given AudioData does not match required type.");
        inputData[index] = data;
    }
    public final void connect(int thisInI, AudioNode other, int otherOutI) {
        this.children[thisInI] = other;
        this.setInput(thisInI, other.getOutput(otherOutI));
    }
    public final void connectConst(int thisInI, AudioData data) {
        this.children[thisInI] = null;
        this.setInput(thisInI, data);
    }

    public final void reset(int sampleNumber) {
        this.sampleNumber = sampleNumber;
        this.executionIndex = AudioPlayerThread.executionIDResetValue;
        this.resetInternal(sampleNumber);
        this.resetChildren(sampleNumber);
    }
    private void resetChildren(int sampleNumber) {
        if (this.resetting) return;
        this.resetting = true;
        for (AudioNode child : children)
            if (child != null)
                child.reset(sampleNumber);
        this.resetting = false;
    }
    protected void resetInternal(int sampleNumber) { }

    public final void execute() {
        if (this.executing)
            throw new IllegalStateException("Called AudioNode.execute() while an execution was in progress. (Probably circular referencing)");
        if (this.executionIndex == this.context.getExecutionID())
            return;
        this.executionIndex = this.context.getExecutionID();
        this.executing = true;
        this.executeChildren();
        for (AudioData data : outputData)
            data.setLength(context.getChunkLength());
        this.internalExecute();
        this.sampleNumber += context.getChunkLength();
        this.executing = false;
    }
    private void executeChildren() {
        for (AudioNode child : children)
            if (child != null)
                child.execute();
    }
    protected abstract void internalExecute();

    protected AudioNode(AudioContext context) {
        this.context = context;
        createOutputs();
    }

    protected final float[] getFloatData(int i) {
        return getFloatData(i,context.getChunkLength());
    }
    protected final float[] getFloatData(int i, int len) {
        float[] dataIn = new float[len];
        if (inputData[i] != null) ((FloatData) inputData[i]).getData(dataIn);
        return dataIn;
    }
    protected final void setFloatData(int i, float[] data) {
        ((FloatData)outputData[i]).setData(data);
    }

    protected final void getStereoFloatData(int i, float[] dataL, float[] dataR) {
        if (inputData[i] != null) ((FloatStereoData) inputData[i]).getData(dataL,dataR);
    }
    protected final void setStereoFloatData(int i, float[] dataL, float[] dataR) {
        ((FloatStereoData)outputData[i]).setData(dataL,dataR);
    }

    protected final int[] getIntData(int i) {
        return getIntData(i,context.getChunkLength());
    }
    protected final int[] getIntData(int i, int len) {
        int[] dataIn = new int[len];
        if (inputData[i] != null) ((IntData) inputData[i]).getData(dataIn);
        return dataIn;
    }
    protected final void setIntData(int i, int[] data) {
        ((IntData)outputData[i]).setData(data);
    }
}
