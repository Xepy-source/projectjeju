package com.project.projectjeju.controllers;

import com.project.projectjeju.enums.WeatherResult;
import com.project.projectjeju.services.WeatherService;
import com.project.projectjeju.vos.SearchWeatherVo;
import com.project.projectjeju.vos.WeatherVo;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@RequestMapping(value = "/apis")
public class ApiController {
    private final WeatherService weatherService;

    @Autowired
    public ApiController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @RequestMapping(value = "weather-info", method = {RequestMethod.GET, RequestMethod.POST})
    public void WeatherProcess(HttpServletRequest request, HttpServletResponse response,
                               @RequestParam(name = "city", defaultValue = "") String city)
            throws IOException, SQLException, NullPointerException, MalformedURLException {
        WeatherResult weatherResult;
        JSONObject weatherJSON = null;
        WeatherVo SearchResultWeatherVo = null;
        LocalDate date = LocalDate.now();
        int hour = LocalTime.now().getHour();

        SearchWeatherVo searchWeatherVo = new SearchWeatherVo(city, date, hour);
        if (searchWeatherVo.isNormalization()) {
            if (this.weatherService.getWrittenTime(searchWeatherVo) != 1) {
                // insert
                URL url;
                if (city.equals("jeju")) {
                    url = new URL("http://api.openweathermap.org/data/2.5/weather?lat=33.5080179&lon=126.5185343&appid=5a20fbfc93d25091d4fb38f5454db389");
                } else if (city.equals("seogwipo")) {
                    url = new URL("http://api.openweathermap.org/data/2.5/weather?lat=33.2502176&lon=126.5587452&appid=5a20fbfc93d25091d4fb38f5454db389");
                } else {
                    url = null;
                }
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                BufferedReader buffered = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder responseBody = new StringBuilder();
                while ((inputLine = buffered.readLine()) != null) {
                    responseBody.append(inputLine);
                }
                buffered.close();

                String JsonString = responseBody.toString();
                JSONObject jsonObject = new JSONObject(JsonString);
                JSONObject jsonMainObject = jsonObject.getJSONObject("main");
                double temp = jsonMainObject.getDouble("temp") - 273.15;
                JSONArray jsonWeatherArray = jsonObject.getJSONArray("weather");
                JSONObject jsonWeatherObject = jsonWeatherArray.getJSONObject(0);
                String alt = jsonWeatherObject.getString("description");
                String icon = jsonWeatherObject.getString("icon");
                int tempInt = Integer.parseInt(String.valueOf(Math.round(temp)));

                WeatherVo InsertWeatherVo = new WeatherVo(city, tempInt, date, hour, alt, icon);
                this.weatherService.setWeatherData(InsertWeatherVo);

                if (this.weatherService.getWrittenTime(searchWeatherVo) == 1) {
                    SearchResultWeatherVo = this.weatherService.getWeatherData(searchWeatherVo);
                    weatherJSON = this.createJSON(SearchResultWeatherVo);
                    weatherResult = WeatherResult.SUCCESS;
                } else {
                    weatherResult = WeatherResult.FAILURE;
                }
            } else {
                SearchResultWeatherVo = this.weatherService.getWeatherData(searchWeatherVo);
                weatherJSON = this.createJSON(SearchResultWeatherVo);
                weatherResult = WeatherResult.SUCCESS;
            }
        } else {
            weatherResult = WeatherResult.NORMALIZATION_FAILURE;
        }

        if (weatherResult == WeatherResult.SUCCESS) {
            response.getWriter().print(weatherJSON);
        } else {
            response.getWriter().print(weatherResult);
        }
    }

    public JSONObject createJSON(WeatherVo weatherVo) {
        JSONObject weatherJSON = new JSONObject();
        weatherJSON.put("city", weatherVo.getCity());
        weatherJSON.put("temp", weatherVo.getTemperature());
        weatherJSON.put("date", weatherVo.getDate());
        weatherJSON.put("hour", weatherVo.getHour());
        weatherJSON.put("alt", weatherVo.getAlt());
        weatherJSON.put("icon", weatherVo.getIcon());

        return weatherJSON;
    }
}
