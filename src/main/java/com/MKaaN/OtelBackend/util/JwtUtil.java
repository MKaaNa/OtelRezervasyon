package com.MKaaN.OtelBackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    // Token oluşturma metodu
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        // Kullanıcıdan rol bilgisini al
        String role = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse("ROLE_USER");  // Varsayılan olarak ROLE_USER

        // Token'ı oluştur ve role bilgisini claim olarak ekle
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())  // Kullanıcı adı
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Token oluşturulma zamanı
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))  // Token geçerlilik süresi (1 gün)
                .signWith(SignatureAlgorithm.HS256, getSigningKey())  // İmzalama algoritması
                .claim("role", role)  // Role bilgisini claim olarak ekle
                .compact();  // Token'ı döndür
    }

    // Token'dan kullanıcı adı almak için
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);  // Subject (kullanıcı adı) bilgisi
    }

    // Token geçerliliğini kontrol etme
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);  // Token'dan kullanıcı adını al
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);  // Token geçerli mi kontrol et
    }

    // Token'ın tüm claim'lerini almak için
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSigningKey())
                .parseClaimsJws(token)  // Token'ı çözümle
                .getBody();
    }

    // Claim verilerini almak için
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);  // Token'dan claim'leri çıkar
        return claimsResolver.apply(claims);  // Claim verisini döndür
    }

    // Token'ın geçerlilik süresini almak için
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);  // Token'ın bitiş zamanı
    }

    // Token süresi dolmuş mu?
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());  // Eğer bitiş zamanı geçmişse true döndür
    }

    // İmzalama anahtarını elde etme metodu
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
        return Keys.hmacShaKeyFor(keyBytes);  // İmzalama anahtarını döndür
    }
}