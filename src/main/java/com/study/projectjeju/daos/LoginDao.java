package com.study.projectjeju.daos;

import com.study.utility.Sha512;
import com.study.projectjeju.vos.LoginVo;
import com.study.projectjeju.vos.UserVo;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class LoginDao {
    public int Login(Connection connection, LoginVo loginVo) throws SQLException {
        int count = 0;
        String query = "select count(`user_index`) as `count` from `project_jeju`.`users` where `user_email`=? and `user_password`=? limit 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, loginVo.getEmail());
            preparedStatement.setString(2, Sha512.hash(loginVo.getPassword()));
            preparedStatement.executeQuery();
            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                while (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public UserVo getUserVo(Connection connection, LoginVo loginVo) throws SQLException {
        UserVo userVo = null;
        String query = "select `user_index`, `user_email`, `user_name`, `user_nickname`, `user_contact`, `user_birth`, `user_level` from `project_jeju`.`users` where `user_email`=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, loginVo.getEmail());
            preparedStatement.executeQuery();
            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                while (resultSet.next()) {
                    userVo = new UserVo(
                            resultSet.getInt("user_index"),
                            resultSet.getString("user_email"),
                            resultSet.getString("user_name"),
                            resultSet.getString("user_nickname"),
                            resultSet.getString("user_contact"),
                            resultSet.getString("user_birth"),
                            resultSet.getInt("user_level")
                    );
                }
            }
        }
        return userVo;
    }
}
