package com.example.mojal2ndproject2.exchangepost.model.dto.request;

public class CreateExchangePostReq {
    private String title;
    private String contents;
    private String timeStamp;
    private String modifyTime;
    private Boolean status;
    private Boolean postType;
    private String giveBtmCategory;
    private String takeBtmCategory;

    public CreateExchangePostReq(String title, String contents, String timeStamp, String modifyTime, Boolean status, Boolean postType, String giveBtmCategory, String takeBtmCategory) {
        this.title = title;
        this.contents = contents;
        this.timeStamp = timeStamp;
        this.modifyTime = modifyTime;
        this.status = status;
        this.postType = postType;
        this.giveBtmCategory = giveBtmCategory;
        this.takeBtmCategory = takeBtmCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getPostType() {
        return postType;
    }

    public void setPostType(Boolean postType) {
        this.postType = postType;
    }

    public String getGiveBtmCategory() {
        return giveBtmCategory;
    }

    public void setGiveBtmCategory(String giveBtmCategory) {
        this.giveBtmCategory = giveBtmCategory;
    }

    public String getTakeBtmCategory() {
        return takeBtmCategory;
    }

    public void setTakeBtmCategory(String takeBtmCategory) {
        this.takeBtmCategory = takeBtmCategory;
    }
}
