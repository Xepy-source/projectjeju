package com.study.projectjeju.vos;

public class SearchVo {
    private final String id;
    private final int page;
    private final String what;
    private final String keyword;

    public SearchVo(String id, String page, String what, String keyword) {
        this.id = id;
        int pageNumber;
        try {
            pageNumber = Integer.parseInt(page);
        } catch (NumberFormatException ignored) {
            pageNumber = 1;
        }
        this.page = pageNumber;
        this.what = what;
        this.keyword = keyword.replace(" ", "");
    }

    public int getPage() {
        return page;
    }

    public String getId() {
        return id;
    }

    public String getWhat() {
        return what;
    }

    public String getKeyword() {
        return keyword;
    }
}
