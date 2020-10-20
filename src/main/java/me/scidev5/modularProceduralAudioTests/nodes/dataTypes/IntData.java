package me.scidev5.modularProceduralAudioTests.nodes.dataTypes;

public class IntData extends AudioData {
    private int[] data = null;

    @Override
    protected void updateDataLength(int length) {
        data = new int[length];
    }

    @Override
    public boolean ofType(String id) {
        return AudioData.INT_ID.equals(id);
    }

    public int[] getData(int[] dataOut) {
        if (dataOut.length != getLength()) throw new IllegalArgumentException("data length mismatch");
        System.arraycopy(data,0,dataOut,0,getLength());
        return dataOut;
    }

    public void setData(int[] dataIn) {
        if (dataIn.length != getLength()) throw new IllegalArgumentException("data length mismatch");
        System.arraycopy(dataIn,0,data,0,getLength());
    }
}
