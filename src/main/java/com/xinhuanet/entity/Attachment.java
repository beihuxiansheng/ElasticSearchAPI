package com.xinhuanet.entity;

public class Attachment {

    private String id;

    //稿件来源类型    0稿源库,1签发库
    private Integer libid;

    //附件标题
    private String title;

    //附件描述
    private String description;

    //附件路径
    private String attpath;

    //附件类型  2：标题图片   1：正文中的图片   0：普通附件（CEB、PDF、WORD、多媒体文件等
    private Integer type;

    //附件文件扩展名   用于区分文件类型
    private String extraname;

    //文件类型   titlepic,pic,bigpic,smallpic,video,flash,document,htm
    private String atttype;

    //标题图显示顺序
    private String titlepicsort;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttpath() {
        return attpath;
    }

    public void setAttpath(String attpath) {
        this.attpath = attpath;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getExtraname() {
        return extraname;
    }

    public void setExtraname(String extraname) {
        this.extraname = extraname;
    }

    public String getAtttype() {
        return atttype;
    }

    public void setAtttype(String atttype) {
        this.atttype = atttype;
    }

    public String getTitlepicsort() {
        return titlepicsort;
    }

    public void setTitlepicsort(String titlepicsort) {
        this.titlepicsort = titlepicsort;
    }

    public String getContentid() {
        return contentid;
    }

    public void setContentid(String contentid) {
        this.contentid = contentid;
    }
}
