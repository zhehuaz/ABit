package org.bitoo.abit.mission.image;

/**
 * Created by langley on 6/8/15.
 */
public class Tweet {
    int position;
    String text;

    public Tweet(int position, String text) {
        this.position = position;
        this.text = text;
    }

    public int getPosition() {
        return position;
    }

    public String getText() {
        return text;
    }
}