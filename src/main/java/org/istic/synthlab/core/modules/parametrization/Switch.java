package org.istic.synthlab.core.modules.parametrization;

public class Switch extends GenericsParam<Boolean> {

    private boolean defaultValue = false;

    public Switch(String label, Boolean max, Boolean min, Boolean value) {
        super(label, max, min, value);
    }

}
