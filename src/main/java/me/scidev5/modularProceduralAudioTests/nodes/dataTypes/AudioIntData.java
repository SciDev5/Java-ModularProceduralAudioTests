package me.scidev5.modularProceduralAudioTests.nodes.dataTypes;

public class AudioIntData extends AudioData {
    private int[] data = null;

    protected AudioIntData(int length) {
        super(length);
    }


    @Override
    protected void updateDataLength(int length) {
        data = new int[length];
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
