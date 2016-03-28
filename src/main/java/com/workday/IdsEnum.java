package com.workday;

import java.util.Enumeration;

/**
 * Created by VineshRoshan on 3/26/2016.
 */
public class IdsEnum implements Ids{

    private Enumeration<Short> idCollection;

    public IdsEnum(Enumeration<Short> idCollection){
        this.idCollection = idCollection;
    }

    @Override
    public short nextId() {
        if(idCollection.hasMoreElements())
            return idCollection.nextElement();
        else
            return END_OF_IDS;
    }
}
