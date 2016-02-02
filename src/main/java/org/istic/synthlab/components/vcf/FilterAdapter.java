package org.istic.synthlab.components.vcf;

import com.jsyn.unitgen.UnitFilter;

/**
 * Created by stephane on 02/02/16.
 */
public class FilterAdapter implements Filter {

    private UnitFilter unitFilter;

    public FilterAdapter(UnitFilter unitFilter) {
        this.unitFilter = unitFilter;
    }
}
