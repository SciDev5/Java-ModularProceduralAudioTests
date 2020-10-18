package me.scidev5.modularProceduralAudioTests.nodes.dataTypes;

public class FloatData extends AudioData {
    private float[] data = null;

    public FloatData(int length) {
        super(length);
    }

    @Override
    protected void updateDataLength(int length) {
        data = new float[length];
    }

    public float[] getData(float[] dataOut) {
        if (dataOut.length != getLength()) throw new IllegalArgumentException("data length mismatch");
        System.arraycopy(data,0,dataOut,0,getLength());
        return dataOut;
    }

    public void setData(float[] dataIn) {
        if (dataIn.length != getLength()) throw new IllegalArgumentException("data length mismatch");
        System.arraycopy(dataIn,0,data,0,getLength());
    }
}
