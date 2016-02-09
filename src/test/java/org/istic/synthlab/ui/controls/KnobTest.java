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
     * Arbitrary epsilon for floating point comparisons
     * Taken from here: https://stackoverflow.com/a/8385364/1544176 and tested empirically, 1e-16 is too small
     */
    public static final double EPSILON = 1e-15;

    @Before
    public void setUp() {
        knob = new Knob();
        random = new Random(123456789L); // We use a fixed seed so the tests are repeatable
    }

    @After
    public void tearDown() {
        knob = null;
        random = null;
    }

    @Test
    public void testMinAndMaxAngle() {
        assertEquals(0, knob.angleToValue(Knob.MIN_ANGLE), EPSILON);
        assertEquals(1, knob.angleToValue(Knob.MAX_ANGLE), EPSILON);
    }

    @Test
    public void testAngleToValueIsOppositeOfValueToAngle() {
        for (int i = 0; i < 1_000_000; i++) {
            final double expected = random.nextDouble();
            final double actual = knob.angleToValue(knob.valueToAngle(expected));
            assertEquals(expected, actual, EPSILON);
        }
    }
}