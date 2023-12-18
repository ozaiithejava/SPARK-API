import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * Bu sınıf, JSON Web Token (JWT) oluşturma, doğrulama ve işlemleri yönetir.
 */
public class JwtManager {

    private static final String SECRET_KEY = "your_secret_key";

    /**
     * Kullanıcı adına dayalı olarak bir JWT oluşturur.
     *
     * @param username JWT içinde yer alacak kullanıcı adı
     * @return Oluşturulan JWT
     */
    public static String generateJwt(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 gün geçerli
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Verilen JWT'yi doğrular ve içindeki talepleri döndürür.
     *
     * @param jwt Doğrulanacak JWT
     * @return JWT içindeki talepler
     */
    public static Claims decodeJwt(String jwt) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwt).getBody();
    }

    /**
     * HTTP isteğinden alınan JWT'yi kullanarak kullanıcı adını döndürür.
     *
     * @param authorizationHeader HTTP isteğinin "Authorization" başlığından alınan JWT
     * @return JWT içindeki kullanıcı adı
     */
    public static String getUsernameFromRequest(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwt = authorizationHeader.substring(7); // "Bearer " sonrası JWT alınır
            Claims claims = decodeJwt(jwt);
            return claims.getSubject();
        }
        return null;
    }
}
