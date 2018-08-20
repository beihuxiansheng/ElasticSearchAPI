package com.xinhuanet.entity;

import java.util.Date;

public class ContentInfo {

    // 主键ID
    private String id;

    // 稿件来源类型 default ''0'',0稿源库,1签发库
    private Integer libid;

    // 引题
    private String introtitle;

    // 标题
    private String title;

    // 副题
    private String subtitle;

    // 发布时间
    private Date pubtime;

    // 稿源库导入时间
    private Date importtime;

    // 稿源稿件原始ID 如果是从源稿库引用过来的稿件，则记录源稿库XML名，否则为空
    private String importid;

    // 作者
    private String author;

    // 编辑
    private String editor;

    // 责任编辑
    private String liability;

    // 签发人
    private String subscriber;

    // 稿件来源
    private String sourcename;

    // 稿件来源url
    private String sourceurl;

    // 多媒体链接
    private String multiattach;

    // 稿件url 稿件外网URL，多个URL存最新一个
    private String url;

    // 关键词
    private String keyword;

    // 知识分类
    private String category;

    // 人名，分词提取的值
    private String personname;

    // 地区
    private String area;

    // 组织机构
    private String organization;

    // 摘要
    private String sabstract;

    // 自动摘要
    private String autoabstract;

    // 版权 default ''0'',1表示原创
    private Integer copyright;

    // 删除标记 default ''0'',1表示已删除
    private Integer deleteflag;

    // 稿件入库日期
    private Date createtime;

    // 语言ID
    private Integer languageid;

    // 最后编辑日期
    private Date lastmodify;

    //稿件类型	0文字 1图片
    private Integer contenttype;

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

    public String getIntrotitle() {
        return introtitle;
    }

    public void setIntrotitle(String introtitle) {
        this.introtitle = introtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImportid() {
        return importid;
    }

    public void setImportid(String importid) {
        this.importid = importid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getLiability() {
        return liability;
    }

    public void setLiability(String liability) {
        this.liability = liability;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public String getSourcename() {
        return sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }

    public String getSourceurl() {
        return sourceurl;
    }

    public void setSourceurl(String sourceurl) {
        this.sourceurl = sourceurl;
    }

    public String getMultiattach() {
        return multiattach;
    }

    public void setMultiattach(String multiattach) {
        this.multiattach = multiattach;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPersonname() {
        return personname;
    }

    public void setPersonname(String personname) {
        this.personname = personname;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getSabstract() {
        return sabstract;
    }

    public void setSabstract(String sabstract) {
        this.sabstract = sabstract;
    }

    public String getAutoabstract() {
        return autoabstract;
    }

    public void setAutoabstract(String autoabstract) {
        this.autoabstract = autoabstract;
    }

    public Integer getCopyright() {
        return copyright;
    }

    public void setCopyright(Integer copyright) {
        this.copyright = copyright;
    }

    public Integer getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(Integer deleteflag) {
        this.deleteflag = deleteflag;
    }

    public Integer getLanguageid() {
        return languageid;
    }

    public void setLanguageid(Integer languageid) {
        this.languageid = languageid;
    }

    public Integer getContenttype() {
        return contenttype;
    }

    public void setContenttype(Integer contenttype) {
        this.contenttype = contenttype;
    }

    public Date getPubtime() {
        return pubtime;
    }

    public void setPubtime(Date pubtime) {
        this.pubtime = pubtime;
    }

    public Date getImporttime() {
        return importtime;
    }

    public void setImporttime(Date importtime) {
        this.importtime = importtime;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getLastmodify() {
        return lastmodify;
    }

    public void setLastmodify(Date lastmodify) {
        this.lastmodify = lastmodify;
    }
}
