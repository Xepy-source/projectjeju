package com.project.projectjeju.services;

import com.project.projectjeju.daos.OverlapCheckDao;
import com.project.projectjeju.daos.RegisterDao;
import com.project.projectjeju.enums.RegisterResult;
import com.project.projectjeju.vos.RegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class RegisterService {
    private final DataSource dataSource;
    private final RegisterDao registerDao;
    private final OverlapCheckDao overlapCheckDao;

    @Autowired
    public RegisterService(DataSource dataSource, RegisterDao registerDao, OverlapCheckDao overlapCheckDao) {
        this.dataSource = dataSource;
        this.registerDao = registerDao;
        this.overlapCheckDao = overlapCheckDao;
    }

    public RegisterResult register(RegisterVo registerVo) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            if (this.registerDao.registerEmailCheck(connection, registerVo) == 1) {    // 이메일 중복검사
                return RegisterResult.EMAIL_DUPLICATE;
            } else if (this.registerDao.registerNicknameCheck(connection, registerVo) == 1) {  // 닉네임 중복검사
                return RegisterResult.NICKNAME_DUPLICATE;
            } else if (this.registerDao.registerContactCheck(connection, registerVo) == 1) {   // 연락처 중복검사
                return RegisterResult.CONTACT_DUPLICATE;
            } else if (this.registerDao.register(connection, registerVo) == 1) {   // 실제 회원가입
                return RegisterResult.SUCCESS;
            } else {
                return RegisterResult.FAILURE;
            }
        }
    }

    public RegisterResult emailOverlapCheck(String email) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            if (this.overlapCheckDao.registerEmailCheck(connection, email) == 1) {    // 이메일 중복검사
                return RegisterResult.EMAIL_DUPLICATE;
            } else {
                return RegisterResult.USE_POSSIBLE;
            }
        }
    }

    public RegisterResult nicknameOverlapCheck(String nickname) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            if (this.overlapCheckDao.registerNicknameCheck(connection, nickname) == 1) {    // 이메일 중복검사
                return RegisterResult.NICKNAME_DUPLICATE;
            } else {
                return RegisterResult.USE_POSSIBLE;
            }
        }
    }

    public RegisterResult contactOverlapCheck(String contact) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            if (this.overlapCheckDao.registerContactCheck(connection, contact) == 1) {    // 이메일 중복검사
                return RegisterResult.CONTACT_DUPLICATE;
            } else {
                return RegisterResult.USE_POSSIBLE;
            }
        }
    }
}
