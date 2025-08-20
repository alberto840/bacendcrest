package com.project.pet_veteriana.config;

import com.project.pet_veteriana.dto.UsersDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {

    // Llave secreta configurada desde application.yml
    @Value("${jwt.secret}")
    private String secretKeyString;

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    // Generar el token con los datos de usuario
    public String generateToken(UsersDto usuarioDto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userid", usuarioDto.getUserId());
        claims.put("rolId", usuarioDto.getRolId());
        claims.put("correo", usuarioDto.getEmail());
        claims.put("nombre", usuarioDto.getName());
        claims.put("idioma", usuarioDto.getPreferredLanguage());

        // Agregar providerId si el usuario es un vendedor
        if (usuarioDto.getProviderId() != null) {
            claims.put("providerId", usuarioDto.getProviderId());
        }

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuarioDto.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 73))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extraer el nombre de usuario (correo) del token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Extraer cualquier claim del token
    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Extraer todos los claims del token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(token).getBody();
    }

    // Extraer el ID del usuario (userid) del token
    public Integer extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userid", Integer.class));
    }

    // Extraer el ID del proveedor (providerId) del token, si el usuario es un vendedor
    public Integer extractProviderId(String token) {
        return extractClaim(token, claims -> claims.get("providerId", Integer.class));
    }

    // Verificar si el token ha expirado
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Validar el token correctamente
    public Boolean validateToken(String token, String expectedUsername) {
        try {
            String extractedUsername = extractUsername(token);
            return extractedUsername.equals(expectedUsername) && !isTokenExpired(token);
        } catch (Exception e) {
            return false; // Si hay un error, el token no es válido
        }
    }
}
