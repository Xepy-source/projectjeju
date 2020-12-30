package com.study.projectjeju.daos;

import com.study.projectjeju.vos.SearchWeatherVo;
import com.study.projectjeju.vos.WeatherVo;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class WeatherDao {
    public void setWeatherData(Connection connection, WeatherVo weatherVo) throws SQLException {
        String query = "insert into `project_jeju`.`weather` " +
                "(`weather_city`, `weather_temperature`, `weather_written_date`, `weather_written_hour`, `weather_alt`, `weather_icon`) " +
                "values (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, weatherVo.getCity());
            preparedStatement.setInt(2, weatherVo.getTemperature());
            preparedStatement.setDate(3, weatherVo.getDate());
            preparedStatement.setInt(4, weatherVo.getHour());
            preparedStatement.setString(5, weatherVo.getAlt());
            preparedStatement.setString(6, weatherVo.getIcon());
            preparedStatement.execute();
        }
    }

    public int getWrittenTime(Connection connection, SearchWeatherVo searchWeatherVo) throws SQLException {
        int count = 0;
        String query = "select count(`weather_index`) as `count` " +
                "       from `project_jeju`.`weather` where `weather_city`=? and `weather_written_date`=? and `weather_written_hour`=? limit 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, searchWeatherVo.getCity());
            preparedStatement.setDate(2, searchWeatherVo.getDate());
            preparedStatement.setInt(3, searchWeatherVo.getHour());
            preparedStatement.executeQuery();
            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                while (resultSet.next()) {
                    count = resultSet.getInt("count");
                }
            }
        }
        return count;
    }

    public WeatherVo getWeatherData(Connection connection, SearchWeatherVo searchWeatherVo) throws SQLException {
        WeatherVo weatherVo = null;
        String query = "select `weather_city` as `city`, " +
                "               `weather_temperature` as `temp`, " +
                "               `weather_written_date` as `date`, " +
                "               `weather_written_hour` as `hour`, " +
                "               `weather_alt` as `alt`, " +
                "               `weather_icon` as `icon` " +
                "       from `project_jeju`.`weather` where `weather_city`=? and `weather_written_date`=? and `weather_written_hour`=? limit 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, searchWeatherVo.getCity());
            preparedStatement.setDate(2, searchWeatherVo.getDate());
            preparedStatement.setInt(3, searchWeatherVo.getHour());
            preparedStatement.executeQuery();
            try (ResultSet resultSet = preparedStatement.getResultSet()) {
                while (resultSet.next()) {
                    weatherVo = new WeatherVo(
                            resultSet.getString("city"),
                            resultSet.getInt("temp"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getInt("hour"),
                            resultSet.getString("alt"),
                            resultSet.getString("icon")
                    );
                }
            }
        }
        return weatherVo;
    }
}
