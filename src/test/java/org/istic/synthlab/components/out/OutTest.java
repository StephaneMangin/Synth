package org.istic.synthlab.components.out;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by stephane on 02/02/16.
 * Module OUT Tester.
 */
public class OutTest {

    private Out out;

    @Before
    public void setUp() {
        this.out = new Out("OUT");
    }

    @Test
    public void testActivate() throws Exception {
        this.out.activate();
        Assert.assertTrue(this.out.isActivated());
    }

    @Test
    public void testDesactivate() throws Exception {
        this.out.deactivate();
        Assert.assertFalse(this.out.isActivated());
    }

    @Test
    public void testInit() throws Exception {

    }

    @Test
    public void testRun() throws Exception {

    }

    @Test
    public void testGetInput() throws Exception {
        Assert.assertNotNull(this.out.getInput());
    }

    @Test
    public void testGetLineout() throws Exception {
        Assert.assertNotNull(this.out.getLineOut());
    }

}