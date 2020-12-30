package com.study.projectjeju.vos;

public class InsertArticleVo {
    private final int writerIndex;
    private final String title;
    private final String image;
    private final String classification;
    private final String location;
    private final String content;
    private final String hashtags;
    private final boolean isNormalized;

    public InsertArticleVo(int writerIndex, String title, String image, String classification, String location, String content, String hashtags) {
        if (writerIndex < 1 || title.equals("") || image.equals("") || classification.equals("") || location.equals("") || content.equals("") || hashtags.equals("")) {
            this.writerIndex = 0;
            this.title = null;
            this.image = null;
            this.classification = null;
            this.location = null;
            this.content = null;
            this.hashtags = null;
            this.isNormalized = false;
        } else {
            this.writerIndex = writerIndex;
            this.title = title;
            this.image = image;
            this.classification = classification;
            this.location = location;
            this.content = content;
            this.hashtags = hashtags;
            this.isNormalized = true;
        }
    }

    public boolean isNormalized() {
        return isNormalized;
    }

    public int getWriterIndex() {
        return writerIndex;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }

    public String getClassification() {
        return classification;
    }

    public String getLocation() {
        return location;
    }

    public String getHashtags() {
        return hashtags;
    }
}
