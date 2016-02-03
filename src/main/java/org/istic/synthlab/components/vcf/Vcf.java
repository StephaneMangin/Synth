package org.istic.synthlab.components.vcf;

import org.istic.synthlab.core.AComponent;
import org.istic.synthlab.core.modules.filters.IFilter;

/**
 * Created by stephane on 02/02/16.
 */
public class Vcf extends AComponent {

    private IFilter filter;

    public Vcf(String name, IFilter filter) {
        super(name);
        this.filter = filter;
    }

    public void start() {

    }

    public void stop() {

    }

    public void init() {

    }

    public void run() {

    }
}
