package me.scidev5.modularProceduralAudioTests.nodes.dataTypes;

public class FloatStereoData extends AudioData {
    private float[] dataL = null;
    private float[] dataR = null;

    @Override
    protected void updateDataLength(int length) {
        dataL = new float[length];
        dataR = new float[length];
    }

    @Override
    public boolean ofType(String id) {
        return AudioData.FLOAT_STEREO_ID.equals(id);
    }

    public void getData(float[] dataLOut, float[] dataROut) {
        if (dataLOut.length != getLength() || dataROut.length != getLength())
            throw new IllegalArgumentException("data length mismatch");
        System.arraycopy(dataL,0,dataLOut,0,getLength());
        System.arraycopy(dataR,0,dataROut,0,getLength());
    }

    public void setData(float[] dataLIn, float[] dataRIn) {
        if (dataLIn.length != getLength() || dataRIn.length != getLength())
            throw new IllegalArgumentException("data length mismatch");
        System.arraycopy(dataLIn,0, dataL,0,getLength());
        System.arraycopy(dataRIn,0, dataR,0,getLength());
    }
}
