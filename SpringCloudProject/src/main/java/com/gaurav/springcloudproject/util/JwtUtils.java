package com.gaurav.springcloudproject.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import com.gaurav.springcloudproject.databases.blogdb.entity.BlogUsers;
import com.gaurav.springcloudproject.databases.blogdb.repo.UserRepository;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtUtils {

    @Value("${blogUser.jwtSecret}")
    private String jwtSecret;

    @Value("${blogUser.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired
    private UserRepository userRepository;

    public String generateJwtToken(UserDetails userDetails) {

        Map<String, Object> claims = new HashMap<>();
        BlogUsers users = userRepository.findUserByEmail(userDetails.getUsername());
        claims.put("userId", users.getId());
//        claims.put("enabled", user.getEnabled());
        claims.put("role", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));//        claims.put("name", user.getName());
        return createToken(claims, userDetails.getUsername());
    }
    private String createToken(Map<String, Object> claims, String subject) {
        try {
            return Jwts.builder()
                    .setClaims(claims).setSubject(subject).setIssuedAt(Date.from(Instant.now()))

                    .setExpiration(DateUtil.generateExpiryDateForToken())
                    .signWith(SignatureAlgorithm.HS256, jwtSecret).compact();
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}