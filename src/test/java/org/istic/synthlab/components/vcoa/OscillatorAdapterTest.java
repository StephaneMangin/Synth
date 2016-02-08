package org.istic.synthlab.components.vcoa;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.*;
import org.junit.Test;

/**
 * @author stephane
 */
public class OscillatorAdapterTest {

    Synthesizer synth;
    Synthesizer inst2;

    @org.junit.Before
    public void setUp() throws Exception {
        synth = JSyn.createSynthesizer();
        inst2 = JSyn.createSynthesizer();

    }

    @org.junit.After
    public void tearDown() throws Exception {
        synth.stop();

    }

    @Test
    public void test1() throws InterruptedException {

        LineOut myOut = new LineOut();
        WhiteNoise myNoise = new WhiteNoise();
        FilterStateVariable myFilter = new FilterStateVariable();

        SineOscillator myOsc = new SineOscillator();
        //myOsc.amplitude.set(3.0);
        myOsc.amplitude.setup(0.0, 1.0, 10.0);
        myOsc.frequency.set(100);

        Add add = new Add();


        synth.add(myOut);
        synth.add(myNoise);
        synth.add(myFilter);
        synth.add(myOsc);

        System.out.println("frame period : " + myOsc.getFramePeriod());
        myNoise.output.connect(myFilter.input);

        myFilter.amplitude.set(0.1);

        myOsc.output.connect(add.inputA);
        myFilter.output.connect(add.inputB);


        add.output.connect(0, myOut.input, 0); /* Left side */
        add.output.connect(0, myOut.input, 1); /* Right side */
        myOut.start();
        synth.start();

        int n = 20;
        while (n > 0) {
            n--;
            synth.sleepFor(0.1); // Dort une seconde =/= sleepUntil où on fixe une DATE (via timestamp?)


            // Alteration du volume
/*            if (n % 2 == 0) {
                myNoise.amplitude.set(0.5);
            } else {
                myNoise.amplitude.set(1.5);
            }*/

        }
    }

    @Test
    public void test2() throws InterruptedException {

        LineOut myOut = new LineOut();
        SineOscillator myOsc = new SineOscillator();

        synth.add(myOut);
        synth.add(myOsc);

        myOsc.frequency.set(40);
        myOsc.frequency.setMaximum(20000);
        myOsc.frequency.setMinimum(40);
        myOsc.frequency.setDefault(40);
        myOsc.frequency.setValueInternal(40);
        //myOsc.frequency.setup(150, 300, 450);
        myOsc.amplitude.set(1.1);

        myOsc.output.connect(0, myOut.input, 0);
        myOsc.output.connect(0, myOut.input, 1);

        myOut.start();
        synth.start();

        int n = 2000;
        while (n > 0) {

            if (n % 50 == 0){
                System.out.println(myOsc.frequency.getValue());
                // System.out.println(synth.getCurrentTime());
            }

            if (n < 1000){
                //myOsc.frequency.set(Math.pow(myOsc.frequency.getValue(), 0.5));

                myOsc.frequency.set(myOsc.frequency.getValue() / 1.005);
            } else {
                //myOsc.frequency.set(Math.pow(myOsc.frequency.getValue(), 2.0));
                myOsc.frequency.set(myOsc.frequency.getValue() * 1.005);

            }

            n--;
            synth.sleepFor(0.0001);
        }
    }

    @Test
    public void test3() throws InterruptedException {
        LineOut myOut = new LineOut();
        SineOscillator myOsc = new SineOscillator();

        synth.add(myOut);
        synth.add(myOsc);

        myOsc.frequency.set(40);
        myOsc.frequency.setMaximum(20000);
        myOsc.frequency.setMinimum(40);
        myOsc.amplitude.set(1.0);

        myOsc.output.connect(0, myOut.input, 0);
        myOsc.output.connect(0, myOut.input, 1);

        myOut.start();
        synth.start();

        int n = 50;

        double m;
        double frequency = 1;
        double jsynREALFrequency = 600;
        int phase = 0;

        double jsynSTEP = 0.01; // steps in second of the loop

        int pass = 200;
        while(true){

            if (pass <= 0) {
                break;
            }
            n++;

            if (n % 10 == 0){
                n = 0;
                System.out.println(myOsc.frequency.getValue());
                // myOsc.flattenOutputs(); -> ne fait pas ce qu'on croit, ça sert à couper l'output alors que
                // JSyon est coupé (pour éviter les fluctuations intempestives ? Pas très clair)
                System.out.println("output : " + myOsc.output.getValue());
            }

            m = (jsynREALFrequency * (Math.sin(2 * Math.PI * frequency * synth.getCurrentTime() + phase) + 1));
            myOsc.frequency.set(m);

            synth.sleepFor(jsynSTEP);
            pass--;
        }
    }

    @Test
    public void test4() throws InterruptedException {
        // Objectif : deux oscillators A et B
        // debut : activation de A
        // milieu : activation de B
        // fin : desactivation de A

        LineOut myOut = new LineOut();
        SineOscillator oscA = new SineOscillator();
        SineOscillator oscB = new SineOscillator();

        synth.add(myOut);
        synth.add(oscA);
        synth.add(oscB);

        oscA.frequency.set(160);
        oscA.amplitude.set(1.0);

        oscB.frequency.set(320);
        oscB.amplitude.set(0.0);

        oscA.output.connect(0, myOut.input, 0);
        oscA.output.connect(0, myOut.input, 1);

        oscB.output.connect(0, myOut.input, 0);
        oscB.output.connect(0, myOut.input, 1);

        myOut.start();
        synth.start();

        synth.sleepFor(1);

        oscB.amplitude.set(1.0);
        synth.sleepFor(1);

        oscA.amplitude.set(0.0);
        synth.sleepFor(1);

    }

    @Test
    public void test5() throws InterruptedException {
        // Objectif : Tester la modulation de phase
        // Pas concluant auditivement, aucune différence détectée selon les réglages
        // (En tout cas pour le SineOscillator)

        LineOut myOut = new LineOut();
        SineOscillator oscA = new SineOscillator();

        synth.add(myOut);
        synth.add(oscA);

        oscA.frequency.set(240);
        oscA.amplitude.set(1.0);

        oscA.output.connect(0, myOut.input, 0);
        oscA.output.connect(0, myOut.input, 1);

        myOut.start();
        synth.start();

        synth.sleepFor(1);

        oscA.phase.set(0.5);
        synth.sleepFor(1);

        oscA.phase.set(1.0);
        synth.sleepFor(1);

        oscA.phase.set(500.0);
        synth.sleepFor(5);

    }

    @Test
    public void test6() throws InterruptedException {
        // Exemple de code pour l'utilisation de setEnabled
        // désactivation et activation d'un composant à la volée.

        LineOut myOut = new LineOut();
        SineOscillator oscA = new SineOscillator();

        synth.add(myOut);
        synth.add(oscA);

        oscA.frequency.set(666);
        oscA.amplitude.set(1.0);

        oscA.output.connect(0, myOut.input, 0);
        oscA.output.connect(0, myOut.input, 1);

        myOut.start();
        synth.start();

        oscA.setEnabled(false);
        synth.sleepFor(1);

        oscA.setEnabled(false);
        synth.sleepFor(1);

        oscA.setEnabled(true);
        synth.sleepFor(3);

    }

    @Test
    public void test7() {
        if (synth.getClass().isInstance(inst2)){
            System.out.println("OUI");
        } else {
            System.out.println("NON");
        }
    }

    @Test
    public void test8() throws InterruptedException {
        LineOut myOut = new LineOut();
        SineOscillator oscA = new SineOscillator();

        synth.add(myOut);
        synth.add(oscA);

        inst2.add(myOut);
        inst2.add(oscA);

        oscA.frequency.set(666);
        oscA.amplitude.set(1.0);

        oscA.output.connect(0, myOut.input, 0);
        oscA.output.connect(0, myOut.input, 1);

        myOut.start();
        synth.start();
        inst2.start();

        oscA.setEnabled(false);
        synth.sleepFor(1);
        inst2.sleepFor(2);

        oscA.setEnabled(false);
        synth.sleepFor(1);
        inst2.sleepFor(2);

        oscA.setEnabled(true);
        synth.sleepFor(3);
        inst2.sleepFor(4);

    }
}