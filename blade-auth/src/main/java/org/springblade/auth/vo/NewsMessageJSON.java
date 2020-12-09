package org.springblade.auth.vo;

public class NewsMessageJSON {

    private String touser;

    private String msgtype = "news";

    private NewsJSONN news;

    public NewsJSONN getNews() {
        return news;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public String getTouser() {
        return touser;
    }

    public void setNews(NewsJSONN news) {
        this.news = news;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }
}
