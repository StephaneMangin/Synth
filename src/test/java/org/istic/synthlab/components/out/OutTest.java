package org.istic.synthlab.components.out;

import org.junit.Assert;

/**
 * Created by stephane on 02/02/16.
 */
public class OutTest {

    private Out out;

    @org.junit.Before
    public void setUp() throws Exception {
        this.out = new Out("OUT");
    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testActivate() throws Exception {
        this.out.activate();
        Assert.assertTrue(this.out.isEnable());
    }

    @org.junit.Test
    public void testDesactivate() throws Exception {
        this.out.deactivate();
        Assert.assertFalse(this.out.isEnable());
    }

    @org.junit.Test
    public void testInit() throws Exception {

    }

    @org.junit.Test
    public void testRun() throws Exception {

    }

    @org.junit.Test
    public void testGetInput() throws Exception {
        Assert.assertNotNull(this.out.getInput());
    }

    @org.junit.Test
    public void testGetLineout() throws Exception {
        //Assert.assertNotNull(this.out.getLineOut());
    }

}