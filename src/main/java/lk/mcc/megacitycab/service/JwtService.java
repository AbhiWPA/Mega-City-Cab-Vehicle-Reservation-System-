package lk.mcc.megacitycab.service;

import io.jsonwebtoken.Claims;
import lk.mcc.megacitycab.util.num.Role;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Title: Orange-Backend
 * Description: JwtService Class
 * Created by Ashan Sandeep on 11/6/2024
 * Java Version: 17
 */
public interface JwtService {
    Claims extractAllClaims(String jwtToken);
    Key getSignInKey();
    <T> T extractClaim(String jwtToken, Function<Claims, T> claimsResolver);
    String extractUsername(String jwtToken);
    String generateJwtAccessToken(String username,  String role);
    String generateJwtAccessToken(Map<String, Object> extraClaims, String username);
    boolean isTokenValid(String token, UserDetails userDetails, String userId);
    boolean isTokenExpired(String token);
    Date extractExpiration(String token);
    String extractUserId(String token);
    Role extractRole(String token);
}
