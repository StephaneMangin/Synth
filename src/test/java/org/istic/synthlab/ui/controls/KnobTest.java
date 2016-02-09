package org.istic.synthlab.ui.controls;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class KnobTest {

    private Knob knob;
    private Random random;
    /**
     * Arbitrary delta for floating point comparisons
     * Taken from here: https://stackoverflow.com/a/8385364/1544176 and tested empirically, 1e-16 is too small
     */
    public static final double DELTA = 1e-15;

    @Before
    public void setUp() throws Exception {
        knob = new Knob();
        random = new Random();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testMinAndMaxAngle() {
        assertEquals(0, knob.angleToValue(Knob.MIN_ANGLE), DELTA);
        assertEquals(1, knob.angleToValue(Knob.MAX_ANGLE), DELTA);
    }

    @Test
    public void testAngleToValueIsOppositeOfValueToAngle() {
        for (int i = 0; i < 1000; i++) {
            final double expected = random.nextDouble();
            final double actual = knob.angleToValue(knob.valueToAngle(expected));
            assertEquals(expected, actual, DELTA);
        }
    }
}