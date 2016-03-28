package com.workday;

import java.util.*;

/**
 * Created by VineshRoshan on 3/26/2016.
 */
public class BalancedTreeRangeContainer implements RangeContainer {

    private volatile TreeMap<Long, Short> rangeTreeMap = new TreeMap<>();
    //This objectRequestCache should be updated/purged appropriately when the underlying datastructure gets modified and after a particular amount of predefined time/size this cache should be destroyed and created new
    private volatile Map<FindIdsInRangeRequest, Ids> objectRequestCache = new HashMap<>();

    public BalancedTreeRangeContainer(long[] data) throws Exception {
        if (data.length > 32000) throw new Exception("id range out of bounds!!! Array size should be less than 32k");
        for (short i = 0; i < data.length; i++) {
            rangeTreeMap.put(data[i], i);
        }
    }

    @Override
    public Ids findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
        FindIdsInRangeRequest request = FindIdsInRangeRequest.getInstance(fromValue, toValue, fromInclusive, toInclusive);
        if (objectRequestCache.containsKey(request)) {
            return objectRequestCache.get(request);
        } else {
            SortedMap<Long, Short> subMap = rangeTreeMap.subMap(fromValue, fromInclusive, toValue, toInclusive);
            List<Short> idList = new ArrayList<>(subMap.values());
            Collections.sort(idList);
            Ids ids = new IdsEnum(Collections.enumeration(idList));
            //It is better to implement it this way as it gives flexibility for already cached requests to not enclose them within a synchronised block and avoid the race condition for creating duplicate requests
            synchronized (this) {
                if (!objectRequestCache.containsKey(request)) {
                    objectRequestCache.put(request, ids);
                }
            }
            return objectRequestCache.get(request);
        }

    }
}

