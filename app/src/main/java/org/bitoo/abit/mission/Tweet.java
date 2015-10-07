package org.bitoo.abit.mission;

/**
 * A tweet is a tiny message noted for daily check.
 * You can note what you did or what you obtained that day to keep
 * the day you check your progress in memory.
 */
public class Tweet {
    int position;
    String text;

    /**
     * Constructor.
     * @param position The position in tweet array, so that
     *                 the date of tweet is able to be calculated as the
     *                 first day is known.
     * @param text The content of the tweet.
     */
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