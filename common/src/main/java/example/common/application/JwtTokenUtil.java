package example.common.application;

import example.common.infrastructure.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    //Needs to be external (not done that yet as no application.properties in this project)
    private String secret = "$7T7w0JJLkTtt_eBJdgb0iOYmvoXhBTWDOBWg!";

    //generate token for user
    public String generateToken(AppUser userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AppUser.USERNAME,userDetails.getUserName());
        claims.put(AppUser.PASSWORD,userDetails.getPassword());
        claims.put(AppUser.ID,userDetails.getUserUUID());
        claims.put(AppUser.FIRST_NAME,userDetails.getFirstName());
        claims.put(AppUser.SURNAME,userDetails.getSurname());
        claims.put(AppUser.EMAIL,userDetails.getEmail());
        claims.put(AppUser.ROLE,userDetails.getRole()); //Requires a custom serializer for Role (see infrastructure)
        return tokenFactory(claims, userDetails.getUserName());
    }

    private String tokenFactory(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()
                        + JWT_TOKEN_VALIDITY * 1000))
                .signWith(getSigningKey())
                .compact();
    }


    //retrieve expiration date from jwt token - predefined claim so using function from getClaimFromToken
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //check if the token has expired
    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //retrieve role from jwt token
    public String getClaimFromToken(String token, String claim) {
        return getAllClaimsFromToken(token).get(claim).toString();
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}