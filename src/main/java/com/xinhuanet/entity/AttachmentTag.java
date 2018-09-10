package com.xinhuanet.entity;

public class AttachmentTag {

    private String id;
    private String attid;
    private String tagtype;
    private String tagname;
    private Double tag_score;
    private String contentid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttid() {
        return attid;
    }

    public void setAttid(String attid) {
        this.attid = attid;
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

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }
}
