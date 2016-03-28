package com.workday;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by VineshRoshan on 3/26/2016.
 */
public class WorkDayRangeContainerFactory implements RangeContainerFactory {
    private static volatile Map<Object,RangeContainer> instancesMap = new HashMap<>();

    /*
     * creates instance for the first time
     */
    private RangeContainer createContainerInstanceFor(long[] data) {
        RangeContainer rangeContainer = null;
        try {
            rangeContainer = new BalancedTreeRangeContainer(data);
        } catch (Exception e) {
            e.printStackTrace();
            //Use Appropriate logging mechanism here
        }
        return  rangeContainer;

    }

    /*
    * The implementation below ensures that the Factory returns the same object for repeated requests , or creates the instance for the first time
    */
    @Override
    public RangeContainer createContainer(long[] data)  {

        if (instancesMap.containsKey(data)) {
            return instancesMap.get(data);
        } else {
            //It is better to implement it this way as it gives flexibility for already cached requests to not enclose them within a synchronised block and avoid the race condition for creating duplicate requests
            synchronized (this) {
                if(!instancesMap.containsKey(data)) {
                    RangeContainer rangeContainer = createContainerInstanceFor(data);
                    instancesMap.put(data, rangeContainer);
                }
                return instancesMap.get(data);
            }
        }

    }
}
