package com.study.projectjeju.services;

import com.study.projectjeju.daos.BoardDao;
import com.study.projectjeju.vos.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@Service
public class BoardService {
    private final DataSource dataSource;
    private final BoardDao boardDao;

    @Autowired
    public BoardService(DataSource dataSource, BoardDao boardDao) {
        this.dataSource = dataSource;
        this.boardDao = boardDao;
    }

    public byte[] getWriterImage(int userId) throws SQLException, IOException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.boardDao.getWriterImage(connection, userId);
        }
    }

    public byte[] getArticleImage(int articleId) throws SQLException, IOException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.boardDao.getArticleImage(connection, articleId);
        }
    }

    public int getTotalArticles() throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.boardDao.getTotalArticles(connection);
        }
    }

    public ArrayList<ViewArticleVo> getAllArticles (BoardVo boardVo) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.boardDao.getAllArticles(connection, boardVo);
        }
    }

    public int getClassArticles(String classification) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.boardDao.getTotalClassArticles(connection, classification);
        }
    }

    public ArrayList<ViewArticleVo> getClassArticles(BoardVo boardVo) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.boardDao.getClassArticles(connection, boardVo);
        }
    }
}
