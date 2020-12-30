package com.study.projectjeju.daos;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class OverlapCheckDao {
    public int registerEmailCheck(Connection connection, String email) throws SQLException {
        int count = 0;
        String query = "select count(`user_index`) as `count` from `project_jeju`.`users` where `user_email`=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.executeQuery();
            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                while (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public int registerNicknameCheck(Connection connection, String nickname) throws SQLException {
        int count = 0;
        String query = "select count(`user_index`) as `count` from `project_jeju`.`users` where `user_nickname`=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, nickname);
            preparedStatement.executeQuery();
            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                while (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public int registerContactCheck(Connection connection, String contact) throws SQLException {
        int count = 0;
        String query = "select count(`user_index`) as `count` from `project_jeju`.`users` where `user_contact`=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, contact);
            preparedStatement.executeQuery();
            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                while (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }
}
