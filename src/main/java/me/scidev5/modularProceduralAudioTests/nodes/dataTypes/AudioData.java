package me.scidev5.modularProceduralAudioTests.nodes.dataTypes;

public abstract class AudioData {

    private int length;
    protected AudioData(int length) {
        if (length <= 0) throw new IllegalArgumentException("Length must be greater than 0.");
        this.setLength(length);
    }

    /**
     * Set the length of the AudioData. (Clears the data)
     * @param length The new length to set.
     */
    public void setLength(int length) {
        if (length <= 0) throw new IllegalArgumentException("Length must be greater than 0.");
        this.length = length;
        updateDataLength(length);
    }
    protected abstract void updateDataLength(int length);

    /**
     * Get the length of the AudioData.
     * @return The length.
     */
    public final int getLength() {
        return length;
    }

    public abstract <T> void getData(T[] type);
    public abstract <T> void setData(T[] data);
}
