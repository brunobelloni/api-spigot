package me.brunobelloni.api.commands.logging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Richmond Steele
 * @since 12/18/13 All rights Reserved Please read included LICENSE file
 */
public class Logger<T> {

    /**
     * Singleton instance
     */
    private static volatile Logger<Object> instance;

    /**
     * SimpleDateFormat to format time for logging
     */
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    /**
     * Calendar instance to get time for logging
     */
    private final Calendar calendar = Calendar.getInstance();

    /**
     * ConcurrentHashMap to hold everything that has been logged
     */
    private final Map<String, String> organizedLogs = new ConcurrentHashMap<>();

    /**
     * Returns the singleton instance
     *
     * @return instance
     */
    public static Logger<Object> getInstance() {
        if (instance == null) {
            instance = getNewInstance();
        }
        return instance;
    }

    /**
     * Creates a new instance of this class
     *
     * @return instance of this class
     */
    public static Logger<Object> getNewInstance() {
        return new Logger<>();
    }

    /**
     * Logs data to the console
     *
     * @param type Prefix for the println
     * @param data data to log
     */
    public void log(final T type, final T data) {
        System.out.println(String.format("[%s %s]: %s", getCurrentTime(), type, data.toString()));
        saveLog(type, getCurrentTime(), data);
    }

    public void logTimeless(final T type, final T data) {
        System.out.println(String.format("[%s]: %s", type, data.toString()));
        saveLog(type, getCurrentTime(), data);
    }

    /**
     * Returns the current time in string and properly formatted
     *
     * @return time string
     */
    private String getCurrentTime() {
        return this.simpleDateFormat.format(this.calendar.getTime());
    }

    /**
     * Saves the logged data to a Map for usage in a possible console or something
     *
     * @param type type of log
     * @param time timestamp of logged data
     * @param data logged data
     */
    private void saveLog(final T type, final String time, final T data) {
        this.organizedLogs.put(type.toString(), String.format("[%s]: %s", time, data.toString()));
    }
}
