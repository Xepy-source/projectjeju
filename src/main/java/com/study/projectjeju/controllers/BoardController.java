package com.study.projectjeju.controllers;

import com.study.projectjeju.vos.UserVo;
import com.study.utility.Converter;
import com.study.projectjeju.services.BoardService;
import com.study.projectjeju.vos.BoardVo;
import com.study.projectjeju.vos.ViewArticleVo;
import com.study.projectjeju.vos.ViewBoardVo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@Controller
@RequestMapping(value = "/")
public class BoardController {
    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @RequestMapping(value = "main", method = RequestMethod.GET)
    public String getMain(HttpServletRequest request, HttpServletResponse response) {
        return "main";
    }


    @RequestMapping(value = "get_user_image", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getUserImage(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(name = "user_id", defaultValue = "") String userId) throws SQLException, IOException {
        int index = Converter.stringToInt(userId, 0);
        if (index > 0) {
            return this.boardService.getWriterImage(index);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "get_article_image", method = RequestMethod.GET, produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getArticleImage(HttpServletRequest request, HttpServletResponse response,
                                  @RequestParam(name = "article_id", defaultValue = "") String articleId) throws SQLException, IOException {
        int index = Converter.stringToInt(articleId, 0);
        if (index > 0) {
            return this.boardService.getArticleImage(index);
        } else {
            return null;
        }
    }

    @RequestMapping(value = "board", method = RequestMethod.POST)
    public void getAllArticles(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(name = "classification", defaultValue = "all") String classification,
                                 @RequestParam(name = "page", defaultValue = "1") String page) throws SQLException, IOException {
        UserVo userVo = Converter.getUserVo(request) != null ? Converter.getUserVo(request) : new UserVo(0, "-", "-", "-", "-", "-", 0);
        int requestPage = Converter.stringToInt(page, 0);
        int totalArticles = classification.equals("all") ? this.boardService.getTotalArticles() : this.boardService.getClassArticles(classification);
        int maxPage = totalArticles % 10 == 0 ? totalArticles / 10 : (int) (Math.floor((double) totalArticles / 10) + 1);
        int startPage = (requestPage > 5) ? (requestPage - 4) : 1;
        int endPage = (maxPage > 9) ? (requestPage + 4) : maxPage;
        BoardVo boardVo = new BoardVo(classification, requestPage);
        ArrayList<ViewArticleVo> articles = classification.equals("all") ? this.boardService.getAllArticles(boardVo) : this.boardService.getClassArticles(boardVo);
        JSONObject resultJSON = new JSONObject();

        if(articles != null) {
            resultJSON.put("result", "success");
            JSONArray articlesJSON = new JSONArray();
            for (ViewArticleVo article : articles) {
                JSONObject articleJSON = new JSONObject();
                if (article.getUserId() == userVo.getIndex()) {
                    articleJSON.put("article_host", "true");
                } else {
                    articleJSON.put("article_host", "false");
                }
                articleJSON.put("article_id", article.getArticleId());
                articleJSON.put("writer_id", article.getUserId());
                articleJSON.put("writer", article.getWriter());
                articleJSON.put("writer_image", "/get_user_image?user_id=" + article.getUserId());
                articleJSON.put("title", article.getArticleTitle());
                articleJSON.put("image", "/get_article_image?article_id=" + article.getArticleId());
                articleJSON.put("content", article.getArticleContent());
                articleJSON.put("hashtag", article.getHashtags());
                articlesJSON.put(articleJSON);
            }
            resultJSON.put("articles", articlesJSON);
            resultJSON.put("requestPage", requestPage);
            resultJSON.put("maxPage", maxPage);
            resultJSON.put("startPage", startPage);
            resultJSON.put("endPage", endPage);
        }
        response.getWriter().print(resultJSON);
        response.getWriter().close();
    }
}