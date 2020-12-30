package com.project.projectjeju.vos;

import java.sql.Date;
import java.time.LocalDate;

public class SearchWeatherVo {
    private final String city;
    private final Date date;
    private final int hour;
    private final boolean isNormalization;

    public SearchWeatherVo(String city, LocalDate date, int hour) {
        if (date != null && hour != -1 && city != null) {
            this.date = Date.valueOf(date);
            this.city = city;
            this.hour = hour;
            isNormalization = true;
        } else {
            this.date = null;
            this.city = null;
            this.hour = 0;
            isNormalization = false;
        }

    }

    public String getCity() {
        return city;
    }

    public Date getDate() {
        return date;
    }

    public int getHour() {
        return hour;
    }

    public boolean isNormalization() {
        return isNormalization;
    }
}
