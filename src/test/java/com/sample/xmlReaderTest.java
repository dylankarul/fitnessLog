package com.sample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Type;
import java.util.*;

class xmlReaderTest {
    //CHANGE!!
    xmlReader testRead;

    @BeforeEach
    void setUp(){
        testRead = new maleXmlReader("/Users/DylanKarul/Desktop/fitnessLog/data/bodyInfoOld.xml");
    }

    @org.junit.jupiter.api.Test
    void addGender() {
        testRead.changeGender("MALE");
        assertEquals(testRead.getGender(), Gender.valueOf("MALE"));

    }

    @org.junit.jupiter.api.Test
    void addBMIs() {
        testRead.addBMIs((float) 21.4, "today");
        testRead.addBMIs((float) 23.0, "tomorrow");
        Map bmiMap = testRead.getBMIs();
        System.out.println( bmiMap.get("today"));
        Object todayVal = bmiMap.get("today");
        assertEquals((float)21.4, todayVal);
        Object tomVal = bmiMap.get("tomorrow");
        assertEquals((float)23.0, tomVal);
        //assertEquals(testRead.getGender(), Gender.valueOf("MALE"));

    }

    @org.junit.jupiter.api.Test
    void addMaleMeasurements() {
        List<Float> measList = new ArrayList<>();
        measList.add((float) 30.0);
        measList.add((float) 66.3);
        testRead.changeMeasurements(measList);
        assertEquals((float)30.0, testRead.getBodyMeasurements().get(0));
        assertEquals((float)66.3, testRead.getBodyMeasurements().get(1));
    }



/*    @org.junit.jupiter.api.Test
    void addWeight() {
        xmlReader testRead = new xmlReader("/Users/DylanKarul/Desktop/projects/fitnessTracker/data/bodyInfoOld.xml");
        testRead.addWeight((float)153.4, "today");

    }

    @org.junit.jupiter.api.Test
    void getWeight() {
        xmlReader testRead = new xmlReader("/Users/DylanKarul/Desktop/projects/fitnessTracker/data/bodyInfoOld.xml");
        addWeight();
        testRead.getWeights();

    }*/

}