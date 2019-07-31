package com.sample;

import com.sample.model.WorkoutType;

import java.util.ArrayList;
import java.util.List;

public class WorkoutService {

    public List getAvailableBrands(WorkoutType type){

        List brands = new ArrayList();

        if(type.equals(WorkoutType.WINE)){
            brands.add("Adrianna Vineyard");
            brands.add(("J. P. Chenet"));

        }else if(type.equals(WorkoutType.WHISKY)){
            brands.add("Glenfiddich");
            brands.add("Johnnie Walker");

        }else if(type.equals(WorkoutType.BEER)){
            brands.add("Corona");

        }else {
            brands.add("No Brand Available");
        }
        return brands;
    }
}
