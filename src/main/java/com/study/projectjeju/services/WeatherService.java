package com.study.projectjeju.services;

import com.study.projectjeju.daos.WeatherDao;
import com.study.projectjeju.vos.SearchWeatherVo;
import com.study.projectjeju.vos.WeatherVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Service
public class WeatherService {
    private final WeatherDao weatherDao;
    private final DataSource dataSource;

    @Autowired
    public WeatherService(WeatherDao weatherDao, DataSource dataSource) {
        this.weatherDao = weatherDao;
        this.dataSource = dataSource;
    }

    public void setWeatherData(WeatherVo weatherVo) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            this.weatherDao.setWeatherData(connection, weatherVo);
        }
    }

    public int getWrittenTime(SearchWeatherVo searchWeatherVo) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.weatherDao.getWrittenTime(connection, searchWeatherVo);
        }
    }

    public WeatherVo getWeatherData(SearchWeatherVo searchWeatherVo) throws SQLException {
        try (Connection connection = this.dataSource.getConnection()) {
            return this.weatherDao.getWeatherData(connection, searchWeatherVo);
        }
    }
}
