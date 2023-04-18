package com.example.bbc_newsreader;

/**
 * A class that represents a single favorite headline.
 */
public class Favorite {
        private final String title;
        private final String date;
        private final String description;
        private final String link;

    /**
     * Constructor for a Favorite object.
     *
     * @param title       The title of the article.
     * @param date        The date of the article.
     * @param description A brief description of the article.
     * @param link        The URL link to the article.
     */
        public Favorite(String title, String date, String description, String link) {
            this.title = title;
            this.date = date;
            this.description = description;
            this.link = link;
        }

    /**
     * Gets the title of the article.
     *
     * @return The title of the article.
     */
        public String getTitle() {
            return title;
        }
    /**
     * Gets the date of the article.
     *
     * @return The date of the article.
     */
        public String getDate() {
            return date;
        }
    /**
     * Gets the description of the article.
     *
     * @return The description of the article.
     */
        public String getDescription() {
            return description;
        }
    /**
     * Gets the link to the article.
     *
     * @return The link to the article.
     */
        public String getLink() {
            return link;
        }
    }

