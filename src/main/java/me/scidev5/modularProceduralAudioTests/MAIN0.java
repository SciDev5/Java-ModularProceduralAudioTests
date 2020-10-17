package me.scidev5.modularProceduralAudioTests;

public class MAIN0 {

    public static void main(String... args) throws InterruptedException {
        AudioPlayerThread player = new AudioPlayerThread();
        player.start();

        //AudioNode osc = null;
        //AudioNode dest = player.destination;
        //dest.connect(0,osc,0);

        Thread.sleep(2000);
        player.interrupt();
    }
}
