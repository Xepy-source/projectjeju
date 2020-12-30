package com.project.projectjeju.vos;

public class ViewArticleVo {
    private final int articleId;
    private final int userId;
    private final String writer;
    private final String articleTitle;
    private final String articleContent;
    private final String hashtags;

    public ViewArticleVo(int articleId, int userId, String writer, String articleTitle, String articleContent, String hashtags) {
        this.articleId = articleId;
        this.userId = userId;
        this.writer = writer;
        this.articleTitle = articleTitle;
        this.articleContent = articleContent;
        this.hashtags = hashtags;
    }

    public int getArticleId() {
        return articleId;
    }

    public int getUserId() {
        return userId;
    }

    public String getWriter() {
        return writer;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getArticleContent() {
        return articleContent;
    }

    public String getHashtags() {
        return hashtags;
    }
}
