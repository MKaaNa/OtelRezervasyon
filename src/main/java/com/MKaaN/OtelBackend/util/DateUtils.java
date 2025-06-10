package com.MKaaN.OtelBackend.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public final class DateUtils {
    private DateUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static Date toDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static long calculateDaysBetween(Date startDate, Date endDate) {
        LocalDate start = toLocalDate(startDate);
        LocalDate end = toLocalDate(endDate);
        return ChronoUnit.DAYS.between(start, end);
    }

    public static long calculateDaysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    public static boolean isDateRangeValid(LocalDate startDate, LocalDate endDate) {
        return startDate != null && endDate != null && !startDate.isAfter(endDate);
    }

    public static boolean isDateInFuture(LocalDate date) {
        return date != null && date.isAfter(LocalDate.now());
    }

    /**
     * Kalış süresini hesaplar
     */
    public static long calculateStayDuration(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        long diffDays = ChronoUnit.DAYS.between(startDate, endDate);
        return diffDays <= 0 ? 1 : diffDays;
    }

    /**
     * Toplam fiyatı hesaplar
     */
    public static BigDecimal calculateTotalPrice(LocalDate startDate, LocalDate endDate, BigDecimal dailyPrice) {
        if (!isDateRangeValid(startDate, endDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }
        
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        return dailyPrice.multiply(BigDecimal.valueOf(days));
    }

    /**
     * Tarih aralık kontrolü
     */
    public static boolean isDateInRange(LocalDate date, LocalDate startDate, LocalDate endDate) {
        return date != null && startDate != null && endDate != null &&
               !date.isBefore(startDate) && !date.isAfter(endDate);
    }
}
