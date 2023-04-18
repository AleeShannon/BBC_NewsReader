package com.example.bbc_newsreader;

import java.io.Serializable;

/**
 * Class representing a news headline.
 */
public class Headline implements Serializable {
    private String title;
    private String description;
    private String link;
    private String date;
    /**
     * Default constructor for the Headline class.
     */

    public Headline() {}
    /**
     * Gets the headline's title.
     *
     * @return The title of the headline.
     */
    public String getTitle() {
        return title;
    }
    /**
     * Sets the headline's title.
     *
     * @param title The title to be set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the headline's description.
     *
     * @return The description of the headline.
     */
    public String getDescription() {
        return description;
    }
    /**
     * Sets the headline's description.
     *
     * @param description The description to be set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * Gets the headline's link.
     *
     * @return The link of the headline.
     */
    public String getLink() {
        return link;
    }
    /**
     * Sets the headline's link.
     *
     * @param link The link to be set.
     */
    public void setLink(String link) {
        this.link = link;
    }
    /**
     * Gets the headline's date.
     *
     * @return The date of the headline.
     */
    public String getDate() {
        return date;
    }
    /**
     * Sets the headline's date.
     *
     * @param date The date to be set.
     */
    public void setDate(String date) {
        this.date = date;
    }
}
