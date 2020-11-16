package com.JukanaCodes.toptendownloader;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class XmlParser {

    private static final String TAG = "XmlParser";

    public ArrayList<FeedResponseEntry> getApplications() {
        return applications;
    }

    private ArrayList<FeedResponseEntry> applications;

    public XmlParser(){

        this.applications = new ArrayList<>();
    }


    public boolean parser(String xmlData){
        boolean status = true;
        FeedResponseEntry currentRecord;
        boolean inEntry = false;
        String textValue = "";

        try{

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        }
        catch(Exception e){

            status = false;
            e.printStackTrace();

        }

        return false;
    }


}
