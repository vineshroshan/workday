package com.workday;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by VineshRoshan on 3/26/2016.
 */
public class RangeQueryBasicTest {
    private RangeContainer rc;
    @Before
    public void setUp(){
        RangeContainerFactory rf = new WorkDayRangeContainerFactory();
        rc = rf.createContainer(new long[]{10,12,17,21,2,15,16});
    }
    @Test
    public void runARangeQuery(){
        Ids ids = rc.findIdsInRange(14, 17, true, true);
        Assert.assertEquals(2, ids.nextId());
        Assert.assertEquals(5, ids.nextId());
        Assert.assertEquals(6, ids.nextId());
        Assert.assertEquals(Ids.END_OF_IDS, ids.nextId());
        ids = rc.findIdsInRange(14, 17, true, false);
        Assert.assertEquals(5, ids.nextId());
        Assert.assertEquals(6, ids.nextId());
        Assert.assertEquals(Ids.END_OF_IDS, ids.nextId());
        ids = rc.findIdsInRange(20, Long.MAX_VALUE, false, true);
        Assert.assertEquals(3, ids.nextId());
        Assert.assertEquals(Ids.END_OF_IDS, ids.nextId());
    }
}
