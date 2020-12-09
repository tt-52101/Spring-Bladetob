package cn.rzedu.sf.activity.vo;

import java.io.Serializable;

public class ArticleJSON implements Serializable {

    private String title;

    private String description;

    private String url;

    private String picurl;

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    public String getPicurl() {
        return picurl;
    }
}
