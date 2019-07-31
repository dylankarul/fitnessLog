package com.sample;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.List;
import java.util.Map;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public abstract class xmlReader {

    private Document doc;
    File infoFile;

    public xmlReader(String filename){
        try {
            File inputFile = new File(filename);
            System.out.println(inputFile);
            infoFile = inputFile;
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(inputFile);
        }
        catch (ParserConfigurationException | IOException  | SAXException e ) {
            System.out.println(e);
        }
    }


    private TreeMap<String, Float> getAmountandDate(String valueType){
        TreeMap<String, Float> retMap = new TreeMap<>();
        String subTag = valueType.substring(0, valueType.length()-1);
        Element mainTag = (Element) doc.getElementsByTagName(valueType).item(0);
        NodeList numList = doc.getElementsByTagName(subTag);
        NodeList dateList = mainTag.getElementsByTagName("date");
        for(int x=0; x<numList.getLength(); x++){
            Node numNode = numList.item(x);
            Node dateNode = dateList.item(x);
            if(numNode.getNodeType() == Node.ELEMENT_NODE){
                Element numVal = (Element) numNode;
                Element dateVal = (Element) dateNode;
                Float w = Float.valueOf(numVal.getTextContent());
                retMap.put(dateVal.getTextContent(), Float.valueOf(numVal.getTextContent()));
            }
        }
        return retMap;
    }

    public List getOnlyAmounts(String valueType){
        List<Float> retNums = new ArrayList<>();
        NodeList numList = doc.getElementsByTagName(valueType);
        for(int x=0; x<numList.getLength(); x++){
            Node numNode = numList.item(x);
            if(numNode.getNodeType() == Node.ELEMENT_NODE){
                Element numVal = (Element) numNode;
                Float w = Float.valueOf(numVal.getTextContent());
                System.out.println("value is" + w);
                retNums.add(w);
            }
        }
        return retNums;
    }

    public TreeMap<String, Float> getWeights(){
        return getAmountandDate("Weights");
    }

    public TreeMap<String, Float>  getBMIs(){
        return getAmountandDate("BMIs");
    }

    public TreeMap<String, Float>  getHeights(){
        return getAmountandDate("Heights");
    }

    public TreeMap<String, Float>  getBFPs(){
        return getAmountandDate("BFPs");
    }

    private void addAmount(float amount, String entryDate, String dataType){
        try {

            String tagName = dataType.substring(0, dataType.length()-1);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(infoFile);
            Element root = document.getDocumentElement();
            System.out.println(root.getTagName());

            Element weights = (Element)document.getElementsByTagName(dataType).item(0);
            Element newVal = document.createElement("Entry");
            Element weight = document.createElement(tagName);
            weight.appendChild(document.createTextNode(String.valueOf(amount)));
            newVal.appendChild(weight);

            Element date = document.createElement("date");
            date.appendChild(document.createTextNode(entryDate));
            newVal.appendChild(date);
            weights.appendChild(newVal);

            writeToFile(document);
        }
        catch (ParserConfigurationException | IOException | SAXException e){
            System.out.println(e);
        }
    }

    public void addWeights(float amount, String entryDate){
        addAmount(amount, entryDate, "Weights");
    }

    public void addBMIs(float amount, String entryDate){
        addAmount(amount, entryDate, "BMIs");
    }

    public void addHeights(float amount, String entryDate){
        addAmount(amount, entryDate, "Heights");
    }

    public void addBFPs(float amount, String entryDate){
        addAmount(amount, entryDate, "BFPs");
    }

    public void changeGender(String genVal){
        changeVal(genVal, "Gender");
    }

    protected void changeVal(String genVal, String tagName) {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(infoFile);
            Element root = document.getDocumentElement();
            Element gen = (Element)document.getElementsByTagName(tagName).item(0);
            gen.setTextContent(genVal);
            System.out.println(root.getElementsByTagName(tagName));

            writeToFile(document);
        }
        catch (ParserConfigurationException | IOException | SAXException e){
            System.out.println(e);
        }


    }

    private void writeToFile(Document document){
        try{
        DOMSource source = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(infoFile);
        transformer.transform(source, result);
        doc = document;
        }
        catch (TransformerException e){
            System.out.println(e);
        }
    }

    public abstract void changeMeasurements(List<Float> measurements);
    public abstract List getBodyMeasurements();
    public abstract float calcBFP();


    public Gender getGender(){
        Element gen = (Element) doc.getElementsByTagName("Gender").item(0);
        String gender  = gen.getTextContent();
        return Gender.valueOf(gender);
    }

    protected List getBodyMeasurementsHelper(List retList, String valueType){
        List<Float> measurementVal = getOnlyAmounts(valueType);
        if(measurementVal.size()>=1){
            retList.add((float)measurementVal.get(0));
        }
        return retList;
    }

    public float calcBMI(){
        TreeMap<String, Float> weightMap = getWeights();
        TreeMap<String, Float> heightMap = getHeights();

        if(weightMap.keySet().size()==0 || heightMap.keySet().size()==0){
            return (float) 0.0;
        }
        float weight = weightMap.lastEntry().getValue();


        float height = heightMap.lastEntry().getValue();

        float bmi = (weight/(height*height))*703;
        addBMIs(bmi, getToday());
        return bmi;
    }

    public String getToday(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
        //System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43
    }

}
