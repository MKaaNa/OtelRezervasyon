package com.MKaaN.OtelBackend.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Form validasyonları için yardımcı metotlar içeren sınıf
 */
public class ValidationUtils {

    private static final Pattern EMAIL_PATTERN = 
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private ValidationUtils() {
        // Utility sınıfı, örnek oluşturmayı engelle
    }

    /**
     * Bir e-posta adresinin geçerli olup olmadığını kontrol eder
     * 
     * @param email Kontrol edilecek e-posta adresi
     * @return E-posta geçerliyse true, değilse false
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Bir şifrenin güçlü olup olmadığını kontrol eder.
     * Şifre en az 8 karakter, en az bir büyük harf, bir küçük harf, bir rakam ve bir özel karakter içermelidir.
     * 
     * @param password Kontrol edilecek şifre
     * @return Şifre güçlüyse true, değilse false
     */
    public static boolean isStrongPassword(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else {
                hasSpecial = true;
            }
        }

        return hasUppercase && hasLowercase && hasDigit && hasSpecial;
    }

    /**
     * Fiyatın geçerli olup olmadığını kontrol eder.
     * Fiyat sıfırdan büyük olmalıdır.
     * 
     * @param price Kontrol edilecek fiyat
     * @return Fiyat geçerliyse true, değilse false
     */
    public static boolean isValidPrice(BigDecimal price) {
        return price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * Rezervasyon tarih aralığının geçerli olup olmadığını kontrol eder.
     * Başlangıç tarihi bitiş tarihinden önce ve bugünden sonra olmalıdır.
     * 
     * @param startDate Başlangıç tarihi
     * @param endDate Bitiş tarihi
     * @return Tarih aralığı geçerliyse true, değilse false
     */
    public static boolean isValidReservationDateRange(LocalDate startDate, LocalDate endDate) {
        return DateUtils.isDateInFuture(startDate) && 
               DateUtils.isDateInFuture(endDate) && 
               DateUtils.isDateRangeValid(startDate, endDate);
    }

    /**
     * Belirtilen değerin null olup olmadığını kontrol eder
     * 
     * @param value Kontrol edilecek değer
     * @param <T> Değer tipi
     * @return Değer null değilse true, null ise false
     */
    public static <T> boolean isNotNull(T value) {
        return value != null;
    }

    /**
     * Belirtilen string değerin boş olup olmadığını kontrol eder
     * 
     * @param value Kontrol edilecek string değer
     * @return Değer boş değilse true, boş ise false
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
