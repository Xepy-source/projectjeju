package com.study.projectjeju.daos;

import com.study.projectjeju.vos.RegisterVo;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class RegisterDao {
    public int register(Connection connection, RegisterVo registerVo) throws SQLException {
        int count;
        String query = "" +
                "insert into `project_jeju`.`users` " +
                "            (`user_email`, `user_password`, `user_name`, `user_nickname`, `user_contact`, `user_birth`) " +
                "values (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, registerVo.getEmail());
            preparedStatement.setString(2, registerVo.getHashedPassword());
            preparedStatement.setString(3, registerVo.getName());
            preparedStatement.setString(4, registerVo.getNickname());
            preparedStatement.setString(5, registerVo.getContact());
            preparedStatement.setString(6, registerVo.getBirth());
            preparedStatement.execute();
        }
        count = registerCheck(connection, registerVo);
        return count;
    }

    public int registerEmailCheck(Connection connection, RegisterVo registerVo) throws SQLException {
        int count = 0;
        String query = "select count(`user_index`) as `count` from `project_jeju`.`users` where `user_email`=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, registerVo.getEmail());
            preparedStatement.executeQuery();
            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                while (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public int registerNicknameCheck(Connection connection, RegisterVo registerVo) throws SQLException {
        int count = 0;
        String query = "select count(`user_index`) as `count` from `project_jeju`.`users` where `user_nickname`=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, registerVo.getNickname());
            preparedStatement.executeQuery();
            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                while (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public int registerContactCheck(Connection connection, RegisterVo registerVo) throws SQLException {
        int count = 0;
        String query = "select count(`user_index`) as `count` from `project_jeju`.`users` where `user_contact`=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, registerVo.getContact());
            preparedStatement.executeQuery();
            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                while (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public int registerCheck(Connection connection, RegisterVo registerVo) throws SQLException {
        int count = 0;
        String query = "select count(`user_index`) as `count` from `project_jeju`.`users` where `user_email`=? and `user_password`=? limit 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, registerVo.getEmail());
            preparedStatement.setString(2, registerVo.getHashedPassword());
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
