package com.example.bbc_newsreader;

public class Favorite {
        private String title;
        private String date;
        private String description;
        private String link;

        public Favorite(String title, String date, String description, String link) {
            this.title = title;
            this.date = date;
            this.description = description;
            this.link = link;
        }

        public String getTitle() {
            return title;
        }

        public String getDate() {
            return date;
        }

        public String getDescription() {
            return description;
        }

        public String getLink() {
            return link;
        }
    }

