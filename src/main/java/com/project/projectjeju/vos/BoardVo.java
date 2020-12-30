package com.project.projectjeju.vos;

public class BoardVo {
    private final String classification;
    private final int page;

    public BoardVo(String classification, int page) {
        this.classification = classification;
        this.page = page;
    }

    public String getClassification() {
        return classification;
    }

    public int getPage() {
        return page;
    }
}
