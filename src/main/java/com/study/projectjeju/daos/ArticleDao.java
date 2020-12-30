package com.study.projectjeju.daos;

import com.study.projectjeju.enums.ArticleResult;
import com.study.projectjeju.vos.InsertArticleVo;
import com.study.projectjeju.vos.ModifyArticleVo;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ArticleDao {
    public int getArticleCount(Connection connection) throws SQLException {
        int count = -1;
        String query = "SELECT COUNT(`article_index`) AS `count` FROM `project_jeju`.`articles`";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public int getUserCount(Connection connection) throws SQLException {
        int count = -1;
        String query = "SELECT COUNT(`user_index`) AS `count` FROM `project_jeju`.`users`";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public ArticleResult insertArticle(Connection connection, InsertArticleVo insertArticleVo) throws SQLException {
        String query = "insert into `project_jeju`.`articles` " +
                "(`user_index`, `article_title`, `article_image`, `board_classification`, `article_location`, `article_content`, `article_hashtag`) " +
                "values (?, ?, ?, ?, ?, ? ,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, insertArticleVo.getWriterIndex());
            preparedStatement.setString(2, insertArticleVo.getTitle());
            preparedStatement.setString(3, insertArticleVo.getImage());
            preparedStatement.setString(4, insertArticleVo.getClassification());
            preparedStatement.setString(5, insertArticleVo.getLocation());
            preparedStatement.setString(6, insertArticleVo.getContent());
            preparedStatement.setString(7, insertArticleVo.getHashtags());
            if (preparedStatement.executeUpdate() == 1) {
                return ArticleResult.SUCCESS;
            } else {
                return ArticleResult.FAILURE;
            }
        }
    }

    public ArticleResult deleteArticle(Connection connection, int articleIndex) throws SQLException {
        String query = "DELETE FROM `project_jeju`.`articles` WHERE `article_index` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, articleIndex);
            if (preparedStatement.executeUpdate() == 1) {
                return ArticleResult.SUCCESS;
            } else {
                return ArticleResult.FAILURE;
            }
        }
    }

    public ArticleResult modifyArticleAndImage(Connection connection, ModifyArticleVo modifyArticleVo) throws SQLException {
        String query = "UPDATE `project_jeju`.`articles` " +
                "SET `article_title`=?, `article_image`=?, `article_location`=?, " +
                "`article_content`=?, `article_hashtag`=?, `board_classification`=? " +
                "WHERE `article_index`=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, modifyArticleVo.getTitle());
            preparedStatement.setString(2, modifyArticleVo.getImage());
            preparedStatement.setString(3, modifyArticleVo.getLocation());
            preparedStatement.setString(4, modifyArticleVo.getContent());
            preparedStatement.setString(5, modifyArticleVo.getHashtags());
            preparedStatement.setString(6, modifyArticleVo.getClassification());
            preparedStatement.setInt(7, modifyArticleVo.getArticleIndex());
            if (preparedStatement.executeUpdate() == 1) {
                return ArticleResult.SUCCESS;
            } else {
                return ArticleResult.FAILURE;
            }
        }
    }

    public ArticleResult modifyArticleNoneImage(Connection connection, ModifyArticleVo modifyArticleVo) throws SQLException {
        String query = "UPDATE `project_jeju`.`articles` " +
                "SET `article_title`=?, `article_location`=?, " +
                "`article_content`=?, `article_hashtag`=?, `board_classification`=? " +
                "WHERE `article_index`=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, modifyArticleVo.getTitle());
            preparedStatement.setString(2, modifyArticleVo.getLocation());
            preparedStatement.setString(3, modifyArticleVo.getContent());
            preparedStatement.setString(4, modifyArticleVo.getHashtags());
            preparedStatement.setString(5, modifyArticleVo.getClassification());
            preparedStatement.setInt(6, modifyArticleVo.getArticleIndex());
            if (preparedStatement.executeUpdate() == 1) {
                return ArticleResult.SUCCESS;
            } else {
                return ArticleResult.FAILURE;
            }
        }
    }

    public ModifyArticleVo modifyGetArticle(Connection connection, int index) throws SQLException {
        ModifyArticleVo modifyArticleVo = null;
        String query = "SELECT `article_index` AS `articleIndex`, " +
                                "`article_title` AS `title`, " +
                                "`user_nickname` AS `nickname`, " +
                                "`board_classification` AS `classification`, " +
                                "`article_location` AS `location`, " +
                                "`article_content` AS `content`, " +
                                "`article_hashtag` AS `hashtags` " +
                                "FROM `project_jeju`.`articles` " +
                                "INNER JOIN `project_jeju`.`users` " +
                                "WHERE `users`.`user_index` = `articles`.`user_index` " +
                                "AND `article_index`=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, index);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    modifyArticleVo = new ModifyArticleVo(
                            resultSet.getInt("articleIndex"),
                            resultSet.getString("writer"),
                            resultSet.getString("title"),
                            null,
                            resultSet.getString("classification"),
                            resultSet.getString("location"),
                            resultSet.getString("content"),
                            resultSet.getString("hashtags")
                    );
                }
            }
        }
        return modifyArticleVo;
    }
}
