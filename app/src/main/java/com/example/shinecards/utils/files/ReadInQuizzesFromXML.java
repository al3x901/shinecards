package com.example.shinecards.utils.files;

import android.app.Activity;
import android.content.res.AssetManager;


import com.example.shinecards.model.Question;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ReadInQuizzesFromXML {

    private static final String XML_TAG_ITEM = "Item";
    private static final String XML_TAG_CATEGORY = "category";
    private static final String XML_TAG_DIFFICULTY = "difficulty";
    private static final String XML_TAG_HEADING = "heading";
    private static final String XML_TAG_QUESTION = "question";
    private static final String XML_TAG_ANSWER = "answer";

    /**
     * Returns an ArrayList of ArrayList of Question objects for displaying in app
     *
     * @param activity the activity that calls this method
     * @param fileName the file name with extension to be opened
     * @return and ArrayList of Question objects
     */
    public static ArrayList<Question> importQuizzes(Activity activity, String fileName) {
        AssetManager assetManager = activity.getAssets();
        ArrayList<Question> quizzes = readInQuizzes(activity, assetManager, fileName);
        //return the quizzes data store
        return quizzes;
    }

    /**
     * Returns an ArrayList of ArrayList of Question objects for displaying in app
     *
     * @param assetManager for opening the file from the assets directory
     * @param fileName     the file name with extension to be opened
     * @return and ArrayList of Question objects
     */
    private static ArrayList<Question> readInQuizzes(Activity activity, AssetManager assetManager, String fileName) {

        XmlPullParserFactory pullParserFactory;
        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            InputStream in_s = assetManager.open(fileName);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in_s, null);

            return parseXML(parser);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            //
            e.printStackTrace();
            return null;
        }
    }

    private static ArrayList<Question> parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList quizzes = null;
        int eventType = parser.getEventType();
        Question currentQuestion = null;

        //parse xml document while parser has not reached the document's end
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;

            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    quizzes = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    //get the name of the start tag
                    name = parser.getName();

                    if (name.equalsIgnoreCase(ReadInQuizzesFromXML.XML_TAG_ITEM)) {
                        currentQuestion = new Question();
                    } else if (currentQuestion != null) {
                        //if the current question is unfinished and the start tag's name is not "Item"
                        if (equalsStringIgC(name, ReadInQuizzesFromXML.XML_TAG_CATEGORY)) {
                            currentQuestion.setCategory(parseString(parser));
                        } else if (equalsStringIgC(name, ReadInQuizzesFromXML.XML_TAG_DIFFICULTY)) {
                            currentQuestion.setDifficulty(parseInt(parser));
                        } else if (equalsStringIgC(name, ReadInQuizzesFromXML.XML_TAG_HEADING)) {
                            currentQuestion.setHeading(parseString(parser));
                        } else if (equalsStringIgC(name, ReadInQuizzesFromXML.XML_TAG_QUESTION)) {
                            currentQuestion.setQuestion(parseString(parser));
                        } else if (equalsStringIgC(name, ReadInQuizzesFromXML.XML_TAG_ANSWER)) {
                            currentQuestion.setAnswer(parseString(parser));
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    //end tag of question reached
                    name = parser.getName();
                    if (name.equalsIgnoreCase(ReadInQuizzesFromXML.XML_TAG_ITEM) && currentQuestion != null) {
                        quizzes.add(currentQuestion);
                    }
                    break;
            }
            //get next line in xml doc
            eventType = parser.next();
        }

        //entire xml document has been parsed, return the data store
        return quizzes;
    }

    private static boolean equalsStringIgC(String string, String compareTo) {
        return string.equalsIgnoreCase(compareTo);
    }

    private static String parseString(XmlPullParser parser) throws XmlPullParserException, IOException {
        return parser.nextText().trim().replaceAll("\n", "");
    }

    private static int parseInt(XmlPullParser parser) throws XmlPullParserException, IOException {
        return Integer.parseInt(parseString(parser));
    }
}
