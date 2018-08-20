package com.xinhuanet.entity;

public class ContentDetail {

    // 主键ID
    private String id;

    // 稿件来源类型 default ''0'',0稿源库,1签发库
    private Integer libid;

    //稿件内容
    private String content;

    //contentid
    private String contentid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getLibid() {
        return libid;
    }

    public void setLibid(Integer libid) {
        this.libid = libid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }
}
