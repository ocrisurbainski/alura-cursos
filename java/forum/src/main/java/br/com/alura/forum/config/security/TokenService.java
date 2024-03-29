package br.com.alura.forum.config.security;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class TokenService {

    @Value("${forum.jwt.expiration}")
    private Integer expiration;

    @Value("${forum.jwt.secret}")
    private String secret;

    public String gerarToken(Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        Date issueAt = new Date();
        Date dateExpiration = getExpirationDate(issueAt, expiration);
        return Jwts.builder()
                .setIssuer("API do forum da Alura")
                .setSubject(usuarioLogado.getEmail())
                .setIssuedAt(issueAt)
                .setExpiration(dateExpiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isTokenService(String token) {
        try {
            parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public String getEmailUsuario(String token) {
        Jws<Claims> claimsJws = parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }

    private Jws<Claims> parseClaimsJws(String token) {
        return Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
    }

    private Date getExpirationDate(Date issueAt, Integer expiration) {
        Calendar expirationCalendar = Calendar.getInstance();
        expirationCalendar.setTime(issueAt);
        expirationCalendar.add(Calendar.MILLISECOND, expiration);
        return expirationCalendar.getTime();
    }

}
