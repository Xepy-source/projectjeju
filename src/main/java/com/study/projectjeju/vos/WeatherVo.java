package com.study.projectjeju.vos;

import java.sql.Date;
import java.time.LocalDate;

public class WeatherVo {
    private final String city;
    private final int temperature;
    private final Date date;
    private final int hour;
    private final String alt;
    private final String icon;
    private final boolean isNormalization;

    public WeatherVo(String city, int temperature, LocalDate date, int hour, String alt, String icon) {
        if (city != null && temperature != -100 && date != null && hour != -1 && alt != null && icon != null) {
            this.city = city;
            this.temperature = temperature;
            this.date = Date.valueOf(date);
            this.hour = hour;
            this.alt = alt;
            this.icon = icon;
            this.isNormalization = true;
        } else {
            this.city = null;
            this.temperature = -100;
            this.date = null;
            this.hour = -1;
            this.alt = null;
            this.icon = null;
            this.isNormalization = false;
        }

    }

    public String getCity() {
        return city;
    }

    public int getTemperature() {
        return temperature;
    }

    public Date getDate() {
        return date;
    }

    public int getHour() {
        return hour;
    }

    public String getAlt() {
        return alt;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isNormalization() {
        return isNormalization;
    }
}
