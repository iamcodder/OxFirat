package com.mymoonapplab.oxfirat.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Event {
    @SerializedName("id")
    private String id;

    @SerializedName("user_id")
    private String userId;

    @SerializedName("updated_user_id")
    private String updatedUserId;

    @SerializedName("is_published")
    private String isPublished;

    @SerializedName("is_slider")
    private String isSlider;

    @SerializedName("order")
    private String order;

    @SerializedName("event_start_date")
    private String eventStartDate;

    @SerializedName("event_end_date")
    private String eventEndDate;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("approve")
    private String approve;

    @SerializedName("content_image")
    private String contentImage;

    @SerializedName("content_slug")
    private String contentSlug;

    @SerializedName("content_page_url")
    private String contentPageUrl;

    @SerializedName("content_type")
    private String contentType;

    @SerializedName("content_id")
    private String contentId;

    @SerializedName("locale")
    private String locale;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("translations")
    private List<Translation> translations;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpdatedUserId() {
        return updatedUserId;
    }

    public void setUpdatedUserId(String updatedUserId) {
        this.updatedUserId = updatedUserId;
    }

    public String getIsPublished() {
        return isPublished;
    }

    public void setIsPublished(String isPublished) {
        this.isPublished = isPublished;
    }

    public String getIsSlider() {
        return isSlider;
    }

    public void setIsSlider(String isSlider) {
        this.isSlider = isSlider;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getEventStartDate() {
        return eventStartDate;
    }

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public String getEventEndDate() {
        return eventEndDate;
    }

    public void setEventEndDate(String eventEndDate) {
        this.eventEndDate = eventEndDate;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String getContentImage() {
        return contentImage;
    }

    public void setContentImage(String contentImage) {
        this.contentImage = contentImage;
    }

    public String getContentSlug() {
        return contentSlug;
    }

    public void setContentSlug(String contentSlug) {
        this.contentSlug = contentSlug;
    }

    public String getContentPageUrl() {
        return contentPageUrl;
    }

    public void setContentPageUrl(String contentPageUrl) {
        this.contentPageUrl = contentPageUrl;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
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

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }
}
