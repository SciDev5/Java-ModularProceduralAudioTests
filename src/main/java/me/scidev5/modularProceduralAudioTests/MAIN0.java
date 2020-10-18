package me.scidev5.modularProceduralAudioTests;

import me.scidev5.modularProceduralAudioTests.nodes.AudioNode;
import me.scidev5.modularProceduralAudioTests.nodes.OscillatorNode;
import me.scidev5.modularProceduralAudioTests.nodes.StereoSplitterNode;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatConstData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatData;

public class MAIN0 {

    public static void main(String... args) throws InterruptedException {
        AudioPlayerThread player = new AudioPlayerThread();

        AudioNode osc = new OscillatorNode(player);
        AudioNode oscFreq = new OscillatorNode(player) {
            @Override
            protected void internalExecute() {
                super.internalExecute();
                int len = context.getChunkLength();
                FloatData output = (FloatData) outputData[0];
                float[] out = new float[len];
                output.getData(out);
                for (int i = 0; i < len; i++)
                    out[i] = (float) (Math.sin(out[i]*2*Math.PI) * 100f + 400f);
                output.setData(out);
            }
        };
        AudioNode splitter = new StereoSplitterNode(player);
        AudioNode dest = player.destination;
        splitter.connect(0, osc, 0);
        splitter.connectConst(1, new FloatConstData(0));
        osc.connect(0,oscFreq,0);
        oscFreq.connectConst(0, new FloatConstData(5));
        dest.connect(0, splitter, 0);

        player.start();

        Thread.sleep(5000);

        player.interrupt();
    }
}
