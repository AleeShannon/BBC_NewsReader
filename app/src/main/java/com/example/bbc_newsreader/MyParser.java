package com.example.bbc_newsreader;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class MyParser {

    private static final String TAG_TITLE = "title";
    private static final String TAG_LINK = "link";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_PUB_DATE = "pubDate";
    private static final String TAG_ITEM = "item";

    public static ArrayList<Headline> parse(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Headline> Headlines = new ArrayList<>();

        int eventType = parser.getEventType();
        Headline currentItem = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;

            switch (eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();

                    if (name.equals(TAG_ITEM)) {
                        currentItem = new Headline();
                    } else if (currentItem != null) {
                        switch (name) {
                            case TAG_TITLE:
                                currentItem.setTitle(parser.nextText());
                                break;
                            case TAG_DESCRIPTION:
                                currentItem.setDescription(parser.nextText());
                                break;
                            case TAG_LINK:
                                currentItem.setLink(parser.nextText());
                                break;
                            case TAG_PUB_DATE:
                                currentItem.setDate(parser.nextText());
                                break;
                        }
                    }
                    break;

                case XmlPullParser.END_TAG:
                    name = parser.getName();

                    if (name.equalsIgnoreCase(TAG_ITEM) && currentItem != null) {
                        Headlines.add(currentItem);
                    }
                    break;
            }
            try {
                eventType = parser.next();
            } catch (XmlPullParserException e) {
                Log.e("MyParser", "Error Parsing XML: " + e.getMessage());
                throw e;
            }
        }

        return Headlines;
    }
}

