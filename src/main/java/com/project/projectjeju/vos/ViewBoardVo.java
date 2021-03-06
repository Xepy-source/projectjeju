package com.project.projectjeju.vos;

import java.util.ArrayList;

public class ViewBoardVo {
    private final ArrayList<ViewArticleVo> articles;
    private final int requestPage;      // 요청 페이지
    private final int maxPage;          // 게시글 개수에 따른 최대 페이지
    private final int startPage;        // 화면에 표시할 첫 페이지 번호
    private final int endPage;          // 화면에 표시할 마지막 페이지 번호
    private final boolean isSearchResult;

    public ViewBoardVo(ArrayList<ViewArticleVo> articles, int requestPage, int maxPage, int startPage, int endPage, boolean isSearchResult) {
        this.articles = articles;
        this.requestPage = requestPage;
        this.maxPage = maxPage;
        this.startPage = startPage;
        this.endPage = endPage;
        this.isSearchResult = isSearchResult;
    }

    public ArrayList<ViewArticleVo> getArticles() {
        return articles;
    }

    public int getRequestPage() {
        return requestPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public int getStartPage() {
        return startPage;
    }

    public int getEndPage() {
        return endPage;
    }

    public boolean isSearchResult() {
        return isSearchResult;
    }
}
