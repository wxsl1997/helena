package com.wxsl.helena.mq.util;

import lombok.experimental.UtilityClass;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author wxsl1997
 */


@UtilityClass
public class TimeUtils {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * 日期 1970-01-01 00:00:00
     */
    public static final LocalDateTime EPOCH_SECOND = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC);

    public static final int ZERO_NANO_OF_SECOND = 0;

    /**
     * 返回当前时间, nano = 0
     */
    public static LocalDateTime now() {
        return LocalDateTime.now().withNano(ZERO_NANO_OF_SECOND);
    }

    public static LocalDateTime startOfDay(LocalDate day) {
        return Optional.ofNullable(day).map(LocalDate::atStartOfDay).orElse(null);
    }

    public static LocalDateTime endOfDay(LocalDate day) {
        return Optional.ofNullable(day).map(d -> d.atTime(23, 59, 59)).orElse(null);
    }

    public static LocalDateTime toLocalDateTime(String time) {
        return Optional.ofNullable(time).map(s -> LocalDateTime.parse(s, DATE_TIME_FORMATTER)).orElse(null);
    }

    public static LocalDate toLocalDate(String date) {
        return Optional.ofNullable(date).map(s -> LocalDate.parse(s, DATE_FORMATTER)).orElse(null);
    }

    public static LocalTime toLocalTime(String date) {
        return Optional.ofNullable(date).map(s -> LocalTime.parse(s, TIME_FORMATTER)).orElse(null);
    }

    public static String format(LocalDateTime time) {
        return Optional.ofNullable(time).map(d -> d.format(DATE_TIME_FORMATTER)).orElse(null);
    }

    public static String format(LocalDate date) {
        return Optional.ofNullable(date).map(d -> d.format(DATE_FORMATTER)).orElse(null);
    }

    public static String format(LocalTime time) {
        return Optional.ofNullable(time).map(d -> d.format(TIME_FORMATTER)).orElse(null);
    }

    /**
     * 时间戳转时间
     *
     * @param timestamp 时间戳
     * @return 时间
     */
    public static LocalDateTime toLocalDateTime(Long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 时间转时间戳
     *
     * @param time 时间
     * @return 时间戳
     */
    public static Long timestamp(LocalDateTime time) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = time.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    public static List<LocalDate> listDays(LocalDate startInclusive, LocalDate endInclusive) {
        // 两时间天数差
        int duration = (int) startInclusive.until(endInclusive, ChronoUnit.DAYS);
        // 时间列表
        return IntStream.rangeClosed(0, duration).mapToObj(startInclusive::plusDays).collect(Collectors.toList());
    }
}
