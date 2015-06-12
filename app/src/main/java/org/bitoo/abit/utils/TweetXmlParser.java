package org.bitoo.abit.utils;

import android.content.Context;

import org.bitoo.abit.mission.image.Tweet;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

/**
 * Parse xml file to get access items and handle them.
 * Using SAX parsing.
 */
public class TweetXmlParser {
    private String filePath;
    private Context context;

    public TweetXmlParser(Context context, String filePath) {
        this.context = context;
        this.filePath = filePath;
    }

    /**
     * Add a tweet in a specific day to xml file.
     * @param tweet that is about to store.
     * @throws IOException in XML file.
     */
    public void addTweet(Tweet tweet) throws IOException {
        SAXReader reader = new SAXReader();
        try {
            Document document = reader.read(context.openFileInput(filePath));
            Element root = document.getRootElement();
            if(root != null) {
                Element tweetElement = DocumentHelper.createElement("tweet");
                tweetElement.addAttribute("position", tweet.getPosition() + "");
                root.add(tweetElement);

                Element textElement = DocumentHelper.createElement("text");
                textElement.setText(tweet.getText());
                tweetElement.add(textElement);
                XMLWriter output = new XMLWriter(context.openFileOutput(filePath, Context.MODE_PRIVATE));
                output.write(document);
                output.close();
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get ad tweet with index of position.
     * You can translate the Date into position.
     * @param position of the tweet you want, in {@link org.bitoo.abit.mission.image.BitMapImage}.
     * @return the tweet get
     * @throws FileNotFoundException
     */
    public Tweet query(int position) throws FileNotFoundException {
        SAXReader reader = new SAXReader();
        Document document;
        String tweetText;
        int tweetPosition;
        try {
            document = reader.read(context.openFileInput(filePath));
            Element root = document.getRootElement();
            Iterator<Element> iterator = root.elements("tweet").iterator();
            while (iterator.hasNext()) {
                Element tweetElement = iterator.next();
                tweetText = tweetElement.element("text").getText();
                tweetPosition = Integer.parseInt(tweetElement.attributeValue("position"));
                if(tweetPosition == position) {
                    Tweet tweet = new Tweet(tweetPosition, tweetText);
                    return tweet;
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * To create a new empty xml file to store tweets.
     * The root tag is <tweets></tweets>.
     */
    public void generateXmlFile() {
        Document document = DocumentHelper.createDocument();
        document.addElement("tweets");

        try {
            XMLWriter output = new XMLWriter(context.openFileOutput(filePath, Context.MODE_PRIVATE));
            output.write(document);
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
