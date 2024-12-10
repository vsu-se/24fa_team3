package com.example.demo.utils;

import java.time.Duration;
import java.time.LocalDateTime;


public class TimeManager {

    public static final String getInstance = null;
    private static TimeManager instance = new TimeManager();
    private long offsetSeconds = 0; 

    private TimeManager() {
    }

    public static TimeManager getInstance() {
        return instance;
    }

    // Returns the "current time" with the offset applied
    public LocalDateTime now() {
        return LocalDateTime.now().plusSeconds(offsetSeconds);
    }

    // Adjust the offset by comparing system time with the new time
    public void adjustTime(LocalDateTime newTime) {
        offsetSeconds = Duration.between(LocalDateTime.now(), newTime).getSeconds();
    }

    public String getFormattedTimeElapsed(long seconds) {
        Object[][] timeUnits = {
            {"Y", 365L * 24 * 60 * 60},
            {"M", (365L / 12) * 24 * 60 * 60},
            {"W", 7L * 24 * 60 * 60},
            {"D", 24L * 60 * 60},
            {"H", 60L * 60},
            {"M", 60L},
            {"S", 1L}
        };

        StringBuilder formattedTime = new StringBuilder();
        
        for (Object[] unit : timeUnits) {
            String unitName = (String) unit[0];
            long unitSeconds = (long) unit[1];

            if (seconds >= unitSeconds) {
                long value = seconds / unitSeconds;
                formattedTime.append(value).append(unitName);
                
                seconds %= unitSeconds; 
                
                if (seconds > 0) {
                    formattedTime.append(", ");
                }
            }

            if (seconds == 0) {
                break;
            }
        }
        
        return formattedTime.toString();
    }
}