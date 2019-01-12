package in.twister.blood_donate.Bean;

import com.google.gson.annotations.SerializedName;


public class ArticleBean {
    @SerializedName("error") private Boolean error;
    @SerializedName("message") private String message;
    @SerializedName("title") private String title;
    @SerializedName("article") private String article;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}
