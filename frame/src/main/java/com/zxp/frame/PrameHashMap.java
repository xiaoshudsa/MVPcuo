package com.zxp.frame;

import java.util.HashMap;

public class PrameHashMap extends HashMap<String ,Object> {

    public PrameHashMap  add(String key,Object value){
        this.put(key,value);
        return this;
    }
}
