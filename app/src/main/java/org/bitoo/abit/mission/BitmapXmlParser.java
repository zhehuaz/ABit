package org.bitoo.abit.mission;

import android.graphics.Color;

import org.bitoo.abit.mission.image.BitColor;
import org.bitoo.abit.mission.image.BitmapImage;
import org.bitoo.abit.mission.image.Pixel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Parse {@link BitmapImage} from a xml file.
 */
public class BitmapXmlParser {
    private List<Pixel> bitmap;
    private int height = 0;
    private int width = 0;
    private int amount = 0;

    /**
     * Parse XML file into Document.
     * @param xmlInputStream Input Stream of XML file.
     * @return Document object for further use in DOM Parser.
     */
    public Document parse(InputStream xmlInputStream) {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            document = builder.parse(xmlInputStream);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    /**
     * Load the image directly from XML file stored as {@link #bitmap}.
     * Information of the image is {@link #height}, and {@link #width}.
     * @param xmlInputStream Path of the XML file.
     */
    public void loadImage(InputStream xmlInputStream) {
        Document document = parse(xmlInputStream);
        Element rootElement = document.getDocumentElement();// <image>
        System.out.println(rootElement.getNodeName());
        Node bitColor = null;
        Element eBitColor = null;
        int xValue;
        int yValue;

        // Attributes
        //id = Integer.parseInt(rootElement.getAttribute("id"));
        height = Integer.parseInt(rootElement.getAttribute("height"));
        width = Integer.parseInt(rootElement.getAttribute("width"));
        amount = Integer.parseInt(rootElement.getAttribute("amount"));
        //bitmap = new BitColor[height * width];
        bitmap = new ArrayList<Pixel>();

        // Traverse <bitcolor>s
        NodeList bitColors = rootElement.getChildNodes();// list of <bitcolor>
        int length = bitColors.getLength();
        for (int i = 0;i < length;i ++) {
            bitColor = bitColors.item(i);// for each <bitcolor>

            if(bitColor.getNodeType() == Node.ELEMENT_NODE) {
                eBitColor = (Element)bitColor;
                xValue = Integer.parseInt(
                        eBitColor.getElementsByTagName("x").item(0).getTextContent());
                yValue = Integer.parseInt(
                        eBitColor.getElementsByTagName("y").item(0).getTextContent());

                bitmap.add(xValue * width + yValue, new BitColor(xValue, yValue,
                        Color.parseColor(eBitColor.getElementsByTagName("color").item(0).getTextContent())));
            }
        }


    }

    public List<Pixel> getBitmap() {
        return bitmap;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getAmount() {
        return amount;
    }
}
