package me.scidev5.modularProceduralAudioTests;

import me.scidev5.modularProceduralAudioTests.nodes.*;
import me.scidev5.modularProceduralAudioTests.nodes.dataTypes.FloatConstData;

public class MAIN0 {

    public static void main(String... args) throws InterruptedException {
        AudioPlayerThread player = new AudioPlayerThread();

        AudioNode osc = new OscillatorNode(player);
        AudioNode oscFreq = new OscillatorNode(player);
        AudioNode oscFreqMapper = new MapRangeNode(player);
        AudioNode splitter = new MonoToStereoPanNode(player);

        AudioNode dest = player.destination;

        splitter.connect(0, osc, 0);
        splitter.connectConst(1, new FloatConstData(0));

        osc.connect(0,oscFreqMapper,0);

        oscFreq.connectConst(0, new FloatConstData(5));

        oscFreqMapper.connect(0,oscFreq,0);
        oscFreqMapper.connectConst(1,new FloatConstData(-1)); // MinIn
        oscFreqMapper.connectConst(1,new FloatConstData(1)); // MaxIn
        oscFreqMapper.connectConst(1,new FloatConstData(100)); // MinOut
        oscFreqMapper.connectConst(1,new FloatConstData(200)); // MaxOut

        dest.connect(0, splitter, 0);

        new Thread(player::start).start();

        Thread.sleep(5000);

        player.stop();
    }
}
