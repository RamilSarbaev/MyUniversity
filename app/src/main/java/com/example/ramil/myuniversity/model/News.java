package com.example.ramil.myuniversity.model;

public class News {
    private String id;
    private String title;
    private String text;
    private String date;
    private String link;
    private String raw_text;

    public News() {
    }

    public News(String title, String text, String date, String link, String raw_text) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.link = link;
        this.raw_text = raw_text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRaw_text() {
        return raw_text;
    }

    public void setRaw_text(String raw_text) {
        this.raw_text = raw_text;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                ", link='" + link + '\'' +
                ", raw_text='" + raw_text + '\'' +
                '}';
    }
}
