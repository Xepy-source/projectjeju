package com.study.projectjeju.services;

import com.study.projectjeju.daos.LoginDao;
import com.study.projectjeju.enums.LoginResult;
import com.study.projectjeju.vos.LoginVo;
import com.study.projectjeju.vos.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class LoginService {
    private final DataSource dataSource;
    private final LoginDao loginDao;

    @Autowired
    public LoginService(DataSource dataSource, LoginDao loginDao) {
        this.dataSource = dataSource;
        this.loginDao = loginDao;
    }

    public LoginResult login(HttpSession session, LoginVo loginVo) throws SQLException {
        int count;
        UserVo userVo;
        try (Connection connection = this.dataSource.getConnection()) {
            count = this.loginDao.Login(connection, loginVo);
            userVo = this.loginDao.getUserVo(connection, loginVo);
        }
        if (count == 1) {
            session.setAttribute("UserVo", userVo);
            return LoginResult.SUCCESS;
        } else {
            return LoginResult.FAILURE;
        }
    }

}