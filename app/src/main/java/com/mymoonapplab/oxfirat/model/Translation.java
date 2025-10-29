package com.mymoonapplab.oxfirat.model;

import com.google.gson.annotations.SerializedName;

public class Translation {
    @SerializedName("id")
    private String id;

    @SerializedName("content_id")
    private String contentId;

    @SerializedName("locale")
    private String locale;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
