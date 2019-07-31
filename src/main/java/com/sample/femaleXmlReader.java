package com.sample;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class femaleXmlReader extends xmlReader{

    public femaleXmlReader(String filename){
        super(filename);
    }

    public void changeMeasurements(List<Float> measurements){
        //0 is neck
        //1 is waist
        //2 is hips
        changeVal(Float.toString(measurements.get(0)), "Neck");
        changeVal(Float.toString(measurements.get(1)), "Waist");
        changeVal(Float.toString(measurements.get(2)), "Hips");
    }

    public List<Float> getBodyMeasurements(){
        //need a get amounts function
        List<Float> retList = new ArrayList<>();
        List<Float> retList2 = getBodyMeasurementsHelper(retList, "Neck");
        retList = getBodyMeasurementsHelper(retList2, "Waist");
        retList2 = getBodyMeasurementsHelper(retList, "Hips");
        return retList2;
    }

    public float calcBFP(){
        List<Float> measList = getBodyMeasurements();
        TreeMap<String, Float> heightMap = getHeights();
        if(measList.size()<3 || heightMap.keySet().size()==0){
            return (float)0.0;
        }
        float recentHeight = heightMap.lastEntry().getValue();
        float neckVal = measList.get(0);
        float waistVal = measList.get(1);
        float hipVal = measList.get(2);
        float bodyFatPercentage = (float) (163.205 * Math.log10(waistVal+hipVal-neckVal) - 97.684 * Math.log10(recentHeight) - 78.387);

        addBFPs(bodyFatPercentage, getToday());
        return bodyFatPercentage;
    }
}
