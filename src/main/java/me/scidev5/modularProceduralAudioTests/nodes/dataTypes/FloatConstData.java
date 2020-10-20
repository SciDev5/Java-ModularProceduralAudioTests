package me.scidev5.modularProceduralAudioTests.nodes.dataTypes;

import java.util.Arrays;

public class FloatConstData extends FloatData {
    private final float value;

    public FloatConstData(float value) {
        this.value = value;
    }

    @Override
    protected void updateDataLength(int length) { }

    public float[] getData(float[] dataOut) {
        Arrays.fill(dataOut,value);
        return dataOut;
    }

    public void setData(float[] dataIn) {
        throw new IllegalStateException("Cannot set data for FloatConstData");
    }
}
