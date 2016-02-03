package org.istic.synthlab.core;

import java.util.Map;

/**
 * Created by seb on 03/02/16.
 */
public interface IObserver {
    void update(Map<String, String> arg);
}
