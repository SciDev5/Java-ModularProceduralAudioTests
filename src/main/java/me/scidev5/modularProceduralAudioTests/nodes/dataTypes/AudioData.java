package me.scidev5.modularProceduralAudioTests.nodes.dataTypes;

public abstract class AudioData {

    public static final String FLOAT_ID = "float";
    public static final String INT_ID = "int";
    public static final String FLOAT_STEREO_ID = "float-s";

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

    /**
     * Checks if the AudioData is of the given type.
     * @param id The id to check type by.
     * @return If the id matches the type.
     */
    public abstract boolean ofType(String id);
}
