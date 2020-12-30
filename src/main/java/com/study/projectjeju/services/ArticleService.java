package com.study.projectjeju.services;

import com.study.projectjeju.daos.ArticleDao;
import com.study.projectjeju.enums.ArticleResult;
import com.study.projectjeju.vos.InsertArticleVo;
import com.study.projectjeju.vos.ModifyArticleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class ArticleService {
    private final DataSource dataSource;
    private final ArticleDao articleDao;

    @Autowired
    public ArticleService(DataSource dataSource, ArticleDao articleDao) {
        this.dataSource = dataSource;
        this.articleDao = articleDao;
    }

    public int getArticleCount() throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.articleDao.getArticleCount(connection);
        }
    }

    public int getUserCount() throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.articleDao.getUserCount(connection);
        }
    }

    public ArticleResult insertArticle(InsertArticleVo insertArticleVo) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.articleDao.insertArticle(connection, insertArticleVo);
        }
    }

    public ArticleResult deleteArticle(int articleIndex) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.articleDao.deleteArticle(connection, articleIndex);
        }
    }

    public ArticleResult modifyArticleAndImage(ModifyArticleVo modifyArticleVo) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.articleDao.modifyArticleAndImage(connection, modifyArticleVo);
        }
    }

    public ArticleResult modifyArticleNoneImage(ModifyArticleVo modifyArticleVo) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.articleDao.modifyArticleNoneImage(connection, modifyArticleVo);
        }
    }

    public ModifyArticleVo modifyGetArticle(int index) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.articleDao.modifyGetArticle(connection, index);
        }
    }
}
