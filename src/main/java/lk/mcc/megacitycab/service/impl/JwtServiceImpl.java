package lk.mcc.megacitycab.service.impl;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lk.mcc.megacitycab.service.JwtService;
import lk.mcc.megacitycab.util.num.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Title: Orange-Backend
 * Description: JwtServiceImpl Class
 * Created by Ashan Sandeep on 11/6/2024
 * Email: ashan_m@epiclanka.net
 * Company: Epic Lanka (Pvt) Ltd.
 * Java Version: 17
 */

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value("${security.jwt.secret-key}")
    private String secretKey;
    @Value("${security.jwt.expiration-time}")
    private long tokenExpirationTime;

    @Override
    public Claims extractAllClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey().getEncoded())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    @Override
    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims allClaims = extractAllClaims(jwtToken);
        return claimsResolver.apply(allClaims);
    }

    @Override
    public String extractUsername(String jwtToken) {
        return extractClaim(jwtToken, Claims::getSubject);
    }

    @Override
    public String generateJwtAccessToken(String username, String role) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("role", role);

        String jwtAccessToken = generateJwtAccessToken(extraClaims, username);
        return validateToken(jwtAccessToken) ? jwtAccessToken : null;
    }

    @Override
    public String generateJwtAccessToken(Map<String, Object> extraClaims, String username) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(tokenExpirationTime)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails, String userId) {
        final String username = extractUsername(token);
        if (!isValidUserId(token, userId)) return false;
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    @Override
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isValidUserId(String jwtToken, String userId) {
        Claims claims = extractAllClaims(jwtToken);
        String userIdFromToken = claims.get("userId", String.class);
        return (userIdFromToken.equals(userId));
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSignInKey().getEncoded())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Extract userId claim from token
    public String extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", String.class));
    }

    @Override
    public Role extractRole(String token) {
        Claims claims = extractAllClaims(token);
        return Role.valueOf(claims.get("role", String.class)); // Ensure "role" is a valid claim in the JWT

    }
}
