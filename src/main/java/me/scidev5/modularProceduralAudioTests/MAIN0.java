package me.scidev5.modularProceduralAudioTests;

import me.scidev5.modularProceduralAudioTests.nodes.AudioNode;
import me.scidev5.modularProceduralAudioTests.nodes.LambdaNode;
import me.scidev5.modularProceduralAudioTests.nodes.OscillatorNode;
import me.scidev5.modularProceduralAudioTests.nodes.StereoSplitterNode;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatConstData;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatData;

public class MAIN0 {

    public static void main(String... args) throws InterruptedException {
        AudioPlayerThread player = new AudioPlayerThread();

        AudioNode osc = new OscillatorNode(player);
        AudioNode oscFreq = new OscillatorNode(player);
        AudioNode oscFreqMapper = new LambdaNode(player, (a,b,c,d,e,f,g,h) -> (float) (Math.sin(a) * 100f + 200f));
        AudioNode splitter = new StereoSplitterNode(player);
        AudioNode dest = player.destination;
        splitter.connect(0, osc, 0);
        splitter.connectConst(1, new FloatConstData(0));
        osc.connect(0,oscFreqMapper,0);
        oscFreq.connectConst(0, new FloatConstData(5));
        oscFreqMapper.connect(0,oscFreq,0);
        dest.connect(0, splitter, 0);

        new Thread(() ->
            player.start()
        ).start();

        Thread.sleep(5000);

        player.stop();
    }
}
