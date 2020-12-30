package com.project.projectjeju.vos;

public class ModifyArticleVo {
    private final int articleIndex;
    private final String writer;
    private final String title;
    private final String image;
    private final String classification;
    private final String location;
    private final String content;
    private final String hashtags;
    private final boolean isNormalized;

    public ModifyArticleVo(int articleIndex, String writer, String title, String image, String classification, String location, String content, String hashtags) {
        this.writer = writer;
        if (articleIndex < 1 || title.equals("") || classification.equals("") || location.equals("") || content.equals("") || hashtags.equals("")) {
            this.articleIndex = 0;
            this.title = null;
            this.image = null;
            this.classification = null;
            this.location = null;
            this.content = null;
            this.hashtags = null;
            this.isNormalized = false;
        } else {
            this.articleIndex = articleIndex;
            this.title = title;
            this.image = image;
            this.classification = classification;
            this.location = location;
            this.content = content;
            this.hashtags = hashtags;
            this.isNormalized = true;
        }
    }

    public String getWriter() {
        return writer;
    }

    public boolean isNormalized() {
        return isNormalized;
    }

    public int getArticleIndex() {
        return articleIndex;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getClassification() {
        return classification;
    }

    public String getLocation() {
        return location;
    }

    public String getContent() {
        return content;
    }

    public String getHashtags() {
        return hashtags;
    }
}
