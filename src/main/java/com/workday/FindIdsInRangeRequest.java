package com.workday;

import java.util.HashMap;
import java.util.Map;

public class FindIdsInRangeRequest {
    private static volatile Map<String, FindIdsInRangeRequest> mapOfRequests = new HashMap<>();
    long fromValue;
    long toValue;
    boolean fromInclusive;
    boolean toInclusive;

    private FindIdsInRangeRequest(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.fromInclusive = fromInclusive;
        this.toInclusive = toInclusive;
    }

    public static FindIdsInRangeRequest getInstance(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(fromValue);
        stringBuffer.append(toValue);
        stringBuffer.append(fromInclusive);
        stringBuffer.append(toInclusive);
        String concatenatedStringRangeRequest = stringBuffer.toString();
        if (mapOfRequests.containsKey(concatenatedStringRangeRequest)) {
            return mapOfRequests.get(concatenatedStringRangeRequest);
        } else {
            synchronized (FindIdsInRangeRequest.class) {
                //It is better to implement it this way as it gives flexibility for already cached requests to not enclose them within a synchronised block and avoid the race condition for creating duplicate requests
                if (!mapOfRequests.containsKey(concatenatedStringRangeRequest)) {
                    FindIdsInRangeRequest findIdsInRangeRequest = new FindIdsInRangeRequest(fromValue, toValue, fromInclusive, toInclusive);
                    mapOfRequests.put(concatenatedStringRangeRequest, findIdsInRangeRequest);
                }
            }
            return mapOfRequests.get(concatenatedStringRangeRequest);
        }
    }
}
