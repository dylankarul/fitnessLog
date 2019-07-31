package com.sample;


import java.util.ArrayList;
import java.lang.Math;
import java.util.List;
import java.util.TreeMap;


public class maleXmlReader extends xmlReader{

    public maleXmlReader(String filename){
        super(filename);
    }

    public void changeMeasurements(List<Float> measurements){
        //0 is neck
        //1 is abdomen
        changeVal((Float.toString(measurements.get(0))), "Neck");
        changeVal(Float.toString(measurements.get(1)), "Abdomen");
    }

    public List<Float> getBodyMeasurements(){
        //need a get amounts function
        List<Float> retList = new ArrayList<>();
        List<Float> retList2 = getBodyMeasurementsHelper(retList, "Neck");
        retList = getBodyMeasurementsHelper(retList2, "Abdomen");
        return retList;
    }

    public float calcBFP(){
        List<Float> measList = getBodyMeasurements();
        TreeMap<String, Float> heightMap = getHeights();
        if(measList.size()<2 || heightMap.keySet().size()==0){
            return (float)0.0;
        }
        float neckVal = measList.get(0);
        float abdomVal = measList.get(1);
        float recentHeight = heightMap.lastEntry().getValue();
        float bodyFatPercentage = (float) (86.010 * Math.log10(abdomVal-neckVal) - 70.041 * Math.log10(recentHeight) + 36.76);

        addBFPs(bodyFatPercentage, getToday());
        return bodyFatPercentage;
    }

}
