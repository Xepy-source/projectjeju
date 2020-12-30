package com.project.projectjeju.controllers;

import com.project.utility.Converter;
import com.project.projectjeju.enums.ArticleResult;
import com.project.projectjeju.services.ArticleService;
import com.project.projectjeju.vos.InsertArticleVo;
import com.project.projectjeju.vos.ModifyArticleVo;
import com.project.projectjeju.vos.UserVo;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@Controller
@RequestMapping(value = "/")
public class ArticleController {
    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @RequestMapping(value = "get_count", method = RequestMethod.POST)
    public void getArticleCount(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int articleCount = this.articleService.getArticleCount();
        int userCount = this.articleService.getUserCount();

        JSONObject resultJSON = new JSONObject();
            resultJSON.put("ArticleCount", articleCount);
        resultJSON.put("UserCount", userCount);
        response.getWriter().print(resultJSON);
        response.getWriter().close();
    }

    @RequestMapping(value = "create_article", method = RequestMethod.POST)
    public void createArticle(HttpServletRequest request, HttpServletResponse response,
                            @RequestParam(name = "title", defaultValue = "") String title,
                            @RequestParam(name = "image") MultipartFile imageData,
                            @RequestParam(name = "classification", defaultValue = "") String classification,
                            @RequestParam(name = "location", defaultValue = "") String location,
                            @RequestParam(name = "content", defaultValue = "") String content,
                            @RequestParam(name = "hashtags", defaultValue = "") String hashtags) throws SQLException, IOException {
        UserVo userVo = Converter.getUserVo(request);
        ArticleResult articleResult;
        JSONObject resultJSON = new JSONObject();
//        String splitContent = content.substring(3, content.length()-4);

        if (userVo == null) {
            articleResult = ArticleResult.NOT_AUTHORIZED;
        } else {
            String image = Converter.imageToString(imageData);
            InsertArticleVo insertArticleVo = new InsertArticleVo(userVo.getIndex(), title, image, classification, location, content, hashtags);
            if (insertArticleVo.isNormalized()) {
                articleResult = this.articleService.insertArticle(insertArticleVo);
            } else {
                articleResult = ArticleResult.NORMALIZED_FAILURE;
            }
        }
        resultJSON.put("result", articleResult.name().toLowerCase());
        response.getWriter().print(resultJSON);
        response.getWriter().close();
    }

    @RequestMapping(value = "delete_article", method = RequestMethod.POST)
    public void deleteArticle(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(name = "user_id", defaultValue = "") String userId,
                              @RequestParam(name = "article_id", defaultValue = "") String articleId) throws SQLException, IOException{
        UserVo userVo = Converter.getUserVo(request);
        int userIndex = Converter.stringToInt(userId, -1);
        int articleIndex = Converter.stringToInt(articleId, -1);
        JSONObject resultJSON = new JSONObject();
        ArticleResult articleResult;
        if (userVo == null || userVo.getIndex() != userIndex) {
            articleResult = ArticleResult.NOT_AUTHORIZED;
        } else {
            articleResult = this.articleService.deleteArticle(articleIndex);
        }
        resultJSON.put("result", articleResult.name().toLowerCase());
        response.getWriter().print(resultJSON);
        response.getWriter().close();
    }

    @RequestMapping(value = "modify_article", method = RequestMethod.POST)
    public void modifyArticle(HttpServletRequest request, HttpServletResponse response,
                              @RequestParam(name = "user_id", defaultValue = "") String userId,
                              @RequestParam(name = "article_id", defaultValue = "") String articleId,
                              @RequestParam(name = "writer", defaultValue = "") String writer,
                              @RequestParam(name = "title", defaultValue = "") String title,
                              @RequestParam(name = "image", defaultValue = "") MultipartFile imageData,
                              @RequestParam(name = "classification", defaultValue = "") String classification,
                              @RequestParam(name = "location", defaultValue = "") String location,
                              @RequestParam(name = "content", defaultValue = "") String content,
                              @RequestParam(name = "hashtags", defaultValue = "") String hashtags) throws SQLException, IOException {
        UserVo userVo = Converter.getUserVo(request);
        int userIndex = Converter.stringToInt(userId, -1);
        int articleIndex = Converter.stringToInt(articleId, -1);
        String image = imageData.isEmpty() ? null : Converter.imageToString(imageData);
        JSONObject resultJSON = new JSONObject();
        ArticleResult articleResult;
        if (userVo == null || userVo.getIndex() != userIndex) {
            articleResult = ArticleResult.NOT_AUTHORIZED;
        } else {
            ModifyArticleVo modifyArticleVo = new ModifyArticleVo(articleIndex, writer, title, image, classification, location, content, hashtags);
            if (modifyArticleVo.isNormalized() && modifyArticleVo.getImage() != null) {
                articleResult = this.articleService.modifyArticleAndImage(modifyArticleVo);
            } else if (modifyArticleVo.isNormalized() && modifyArticleVo.getImage() == null) {
                articleResult = this.articleService.modifyArticleNoneImage(modifyArticleVo);
            } else {
                articleResult = ArticleResult.NORMALIZED_FAILURE;
            }
        }
        resultJSON.put("result", articleResult.name().toLowerCase());
        response.getWriter().print(resultJSON);
        response.getWriter().close();
    }

    @RequestMapping(value = "modify_get_article", method = RequestMethod.POST)
    public void modifyGetArticle(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam(name = "user_id", defaultValue = "") String userId,
                                 @RequestParam(name = "article_id", defaultValue = "") String articleId) throws SQLException, IOException {
        UserVo userVo = Converter.getUserVo(request);
        int userIndex = Converter.stringToInt(userId, -1);
        int articleIndex = Converter.stringToInt(articleId, -1);
        JSONObject resultJSON = new JSONObject();

        if (userVo == null || userVo.getIndex() != userIndex) {
            resultJSON.put("result", "failure");
        } else {
            ModifyArticleVo modifyArticleVo = this.articleService.modifyGetArticle(articleIndex);
            resultJSON.put("result", "success");
            resultJSON.put("article_index", modifyArticleVo.getArticleIndex());
            resultJSON.put("writer", modifyArticleVo.getWriter());
            resultJSON.put("title", modifyArticleVo.getTitle());
            resultJSON.put("classification", modifyArticleVo.getClassification());
            resultJSON.put("location", modifyArticleVo.getLocation());
            resultJSON.put("content", modifyArticleVo.getContent());
            resultJSON.put("hashtag", modifyArticleVo.getHashtags());
        }
        response.getWriter().print(resultJSON);
        response.getWriter().close();
    }
}