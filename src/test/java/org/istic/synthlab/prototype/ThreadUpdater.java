package org.istic.synthlab.prototype;

import com.jsyn.unitgen.SineOscillator;

/**
 * Created by cyprien on 11/02/16.
 */
public class ThreadUpdater implements Runnable {

    SineOscillator sine;

    public ThreadUpdater(SineOscillator sine){
        this.sine = sine;
    }

    @Override
    public synchronized void run() {

        int n = 50;

        while ( n > 0){

            try {
                this.wait(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (n % 2 == 0){
                sine.amplitude.set(0.0);
            } else {
                sine.amplitude.set(1.0);
            }


            n--;
        }

    }
}
