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
}