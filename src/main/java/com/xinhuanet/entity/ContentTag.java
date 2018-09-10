package com.xinhuanet.entity;

public class ContentTag {
    private String id;
    private String contentid;
    private String tagtype;
    private String tagname;
    private Double tag_score;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }

    public String getTagtype() {
        return tagtype;
    }

    public void setTagtype(String tagtype) {
        this.tagtype = tagtype;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    public Double getTag_score() {
        return tag_score;
    }

    public void setTag_score(Double tag_score) {
        this.tag_score = tag_score;
    }
}
