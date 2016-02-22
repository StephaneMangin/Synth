package org.istic.synthlab.prototype;

/**
 * Created by cyprien on 08/02/16.
 */
public class Main {

    public static void main(String args[]) throws Exception {

        //EnveloppeGeneratorTest egt = new EnveloppeGeneratorTest();
        //egt.setUp();
        //egt.envelopeLongTiming();

        /*PassThroughTesting ptt = new PassThroughTesting();
        ptt.setUp();
        ptt.SimpleTestOneInputToThreeOutput();
        ptt.SimpleTest();*/

        /*EgPrototype egp = new EgPrototype();
        egp.simpleEgTest2();*/

        /*ProofDisconnectInput pdi = new ProofDisconnectInput();
        pdi.run();*/

        //JSynSynchronizeTesting jst = new JSynSynchronizeTesting();

        //ProofFilterAmplitude pfa = new ProofFilterAmplitude();
        ProofWriteToFile pwtf = new ProofWriteToFile();
        pwtf.writeToMultipleFileWithComponent();

    }

}
