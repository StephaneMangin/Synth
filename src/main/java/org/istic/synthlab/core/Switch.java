package org.istic.synthlab.core;

public class Switch extends Params<Boolean> {

    private boolean defaultValue = false;

    public Switch(String label, Boolean max, Boolean min, Boolean value) {
        super(label, max, min, value);
    }

}
