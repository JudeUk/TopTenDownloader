package com.JukanaCodes.toptendownloader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class XmlParser {

    private static final String TAG = "XmlParser";
    private ArrayList<FeedResponseEntry> applications;

    public XmlParser(){

        this.applications = new ArrayList<>();
    }

    public ArrayList<FeedResponseEntry> getApplications() {
        return applications;
    }




    public boolean parser(String xmlData){
        boolean status = true;
        FeedResponseEntry currentRecord = null;
        boolean inEntry = false;
        String textValue = "";

        try{

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(xmlData));
            int eventType = xpp.getEventType();

            while(eventType != XmlPullParser.END_DOCUMENT){

                String tagName = xpp.getName();
                switch(eventType){

                    case XmlPullParser.START_TAG:
                        Log.d(TAG, "parser: starting tag for " + tagName);

                        if("entry".equalsIgnoreCase(tagName)){

                            inEntry=true;
                            currentRecord= new FeedResponseEntry();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        textValue = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "parse: Ending tag for " + tagName);
                        if(inEntry){

                            if("entry".equalsIgnoreCase(tagName))
                            {
                                applications.add(currentRecord);
                                inEntry=false;
                            } else if("name".equalsIgnoreCase(tagName)){

                                currentRecord.setName(textValue);

                            }
                            else if("artist".equalsIgnoreCase(tagName)){

                                currentRecord.setArtist(textValue);
                            }

                            else if("releaseDate".equalsIgnoreCase(tagName)){

                                currentRecord.setReleaseDate(textValue);

                            }

                            else if("summary".equalsIgnoreCase(tagName)){

                                currentRecord.setSummary(textValue);

                            }

                            else if("image".equalsIgnoreCase(tagName)){

                                currentRecord.setImageUrl(textValue);

                            }

                        }
                        break;

                    default:
                        // Nothing else to do
                }

                eventType = xpp.next();
            }

            for(FeedResponseEntry app: applications){

                Log.d(TAG, "***********************");
                Log.d(TAG, app.toString());
            }
        }
        catch(Exception e){

            status = false;
            e.printStackTrace();

        }

        return status;
    }


}
