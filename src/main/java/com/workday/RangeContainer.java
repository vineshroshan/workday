package com.workday;

/**
 * Created by VineshRoshan on 3/26/2016.
 */
public interface RangeContainer {
    /**
     * @return the Ids of all instances found in the container that\
     * have data value between fromValue and toValue with optional
     * inclusivity.
     */

    Ids findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive);
}
