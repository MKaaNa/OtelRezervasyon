package com.MKaaN.OtelBackend.constant;

public final class Constants {
    
    private Constants() {
        // Utility sınıfı, örnek oluşturmayı engelle
    }
    
    public static final class Security {
        private Security() {}
        
        public static final String TOKEN_PREFIX = "Bearer ";
        public static final String HEADER_STRING = "Authorization";
        public static final String SIGN_UP_URL = "/api/auth/signup";
        public static final String LOGIN_URL = "/api/auth/login";
        public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 gün
    }
    
    public static final class Validation {
        private Validation() {}
        
        public static final int MIN_PASSWORD_LENGTH = 8;
        public static final String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        public static final String PHONE_PATTERN = "^\\+?[1-9][0-9]{7,14}$";
    }
    
    public static final class Messages {
        private Messages() {}
        
        public static final String USER_NOT_FOUND = "Kullanıcı bulunamadı";
        public static final String ROOM_NOT_FOUND = "Oda bulunamadı";
        public static final String RESERVATION_NOT_FOUND = "Rezervasyon bulunamadı";
        public static final String HOTEL_NOT_FOUND = "Otel bulunamadı";
        public static final String INVALID_CREDENTIALS = "Geçersiz kullanıcı adı veya şifre";
        public static final String ACCESS_DENIED = "Bu işlem için yetkiniz bulunmuyor";
        public static final String EMAIL_EXISTS = "Bu email adresi zaten kullanımda";
        public static final String INVALID_DATE_RANGE = "Geçersiz tarih aralığı";
        public static final String ROOM_ALREADY_RESERVED = "Oda seçilen tarihler için zaten rezerve edilmiş";
    }
    
    public static final class Pagination {
        private Pagination() {}
        
        public static final int DEFAULT_PAGE_SIZE = 10;
        public static final int MAX_PAGE_SIZE = 100;
        public static final String DEFAULT_SORT_BY = "id";
        public static final String DEFAULT_SORT_DIRECTION = "asc";
    }
} 