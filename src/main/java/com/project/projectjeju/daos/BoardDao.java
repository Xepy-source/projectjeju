package com.project.projectjeju.daos;

import com.project.projectjeju.vos.BoardVo;
import com.project.projectjeju.vos.ViewArticleVo;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class BoardDao {
    public byte[] getWriterImage(Connection connection, int userId) throws SQLException, IOException {
        byte[] resultImage = null;
        String query = "SELECT `user_image` AS `userImage` FROM `project_jeju`.`users` WHERE `user_index` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String imageData = resultSet.getString("userImage").split(",")[1];
                    byte[] imageBytes = DatatypeConverter.parseBase64Binary(imageData);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(image, "jpg", byteArrayOutputStream);
                    resultImage = byteArrayOutputStream.toByteArray();
                }
            }
        }
        return resultImage;
    }

    public byte[] getArticleImage(Connection connection, int articleId) throws SQLException, IOException {
        byte[] resultImage = null;
        String query = "SELECT `article_image` AS `articleImage` FROM `project_jeju`.`articles` WHERE `article_index` = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, articleId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String imageData = resultSet.getString("articleImage").split(",")[1];
                    byte[] imageBytes = DatatypeConverter.parseBase64Binary(imageData);
                    BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    ImageIO.write(image, "jpg", byteArrayOutputStream);
                    resultImage = byteArrayOutputStream.toByteArray();
                }
            }
        }
        return resultImage;
    }

    public int getTotalArticles(Connection connection) throws SQLException {
        int count = 0;
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

    public ArrayList<ViewArticleVo> getAllArticles(Connection connection, BoardVo boardVo) throws SQLException {
        ArrayList<ViewArticleVo> articles = new ArrayList<>();
        String query = "SELECT `articles`.`article_index` AS `articleId`, " +
                                "`articles`.`user_index` AS `userId`, " +
                                "`users`.`user_nickname` AS `nickname`, " +
                                "`articles`.`article_title` AS `title`, " +
                                "`articles`.`article_content` AS `content`, " +
                                "`articles`.`article_hashtag` AS `hashtag` " +
                                "FROM `project_jeju`.`articles` " +
                                "INNER JOIN `project_jeju`.`users` AS `users` ON `users`.`user_index`=`articles`.`user_index` " +
                                "ORDER BY `article_index` DESC LIMIT ?, 10";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, (boardVo.getPage() - 1) * 10);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ViewArticleVo viewArticleVo = new ViewArticleVo(
                            resultSet.getInt("articleId"),
                            resultSet.getInt("userId"),
                            resultSet.getString("nickname"),
                            resultSet.getString("title"),
                            resultSet.getString("content"),
                            resultSet.getString("hashtag"));
                    articles.add(viewArticleVo);
                }
            }
        }
        return articles;
    }

    public int getTotalClassArticles(Connection connection, String classification) throws SQLException {
        int count = 0;
        String query = "SELECT COUNT(`article_index`) AS `count` FROM `project_jeju`.`articles` WHERE `board_classification` LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, classification);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public ArrayList<ViewArticleVo> getClassArticles(Connection connection, BoardVo boardVo) throws SQLException {
        ArrayList<ViewArticleVo> articles = new ArrayList<>();
        String query = "SELECT `articles`.`article_index` AS `articleId`, " +
                                "`articles`.`user_index` AS `userId`, " +
                                "`users`.`user_nickname` AS `nickname`, " +
                                "`articles`.`article_title` AS `title`, " +
                                "`articles`.`article_content` AS `content`, " +
                                "`articles`.`article_hashtag` AS `hashtag` " +
                                "FROM `project_jeju`.`articles` " +
                                "INNER JOIN `project_jeju`.`users` AS `users` ON `users`.`user_index`=`articles`.`user_index` " +
                                "WHERE `articles`.`board_classification` LIKE ? " +
                                "ORDER BY `article_index` DESC LIMIT ?, 10";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, boardVo.getClassification());
            preparedStatement.setInt(2, (boardVo.getPage() - 1) * 10);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ViewArticleVo viewArticleVo = new ViewArticleVo(
                            resultSet.getInt("articleId"),
                            resultSet.getInt("userId"),
                            resultSet.getString("nickname"),
                            resultSet.getString("title"),
                            resultSet.getString("content"),
                            resultSet.getString("hashtag"));
                    articles.add(viewArticleVo);
                }
            }
        }
        return articles;
    }
}
