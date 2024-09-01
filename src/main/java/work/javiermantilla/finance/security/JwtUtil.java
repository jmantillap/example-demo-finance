package work.javiermantilla.finance.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import work.javiermantilla.finance.dto.UserContextSessionDTO;
import work.javiermantilla.finance.utils.FinanceConstants;

@Component
public class JwtUtil {

	@SuppressWarnings("deprecation")
	public boolean validateJWT(String token) {
		try {
			Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(FinanceConstants.APIKEY))
					.parseClaimsJws(token).getBody();

			Date now = new Date();
			return !now.after(claims.getExpiration());
		} catch (Exception e) {
			return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	public String createJWT(String issuer, String name) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(FinanceConstants.TOKEN_IDUSER, issuer);
		claims.put(FinanceConstants.TOKEN_NAME, name);
		long timeLifeJwt = System.currentTimeMillis() + FinanceConstants.TIME_LIFE_JWT;
		Date dateExpiration = new Date(timeLifeJwt);
		
		return Jwts.builder()
				.setClaims(claims)
				.setIssuer(issuer)
				.setSubject(name)
				.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(dateExpiration)
                .signWith(SignatureAlgorithm.HS256, FinanceConstants.APIKEY).compact();
		
		
	}

	@SuppressWarnings("deprecation")
	public UserContextSessionDTO extraerContextoJWT(String token) {
		UserContextSessionDTO user = new UserContextSessionDTO();
		SignatureAlgorithm algoritmoFirma = SignatureAlgorithm.HS256;
		byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(FinanceConstants.APIKEY);
		Key key = new SecretKeySpec(apiKeySecretBytes, algoritmoFirma.getJcaName());
		var parser = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();		
		user.setIdUser(parser.get(FinanceConstants.TOKEN_IDUSER).toString());
		user.setName(parser.get(FinanceConstants.TOKEN_NAME).toString());
		user.setExpirationDate(parser.getExpiration());
		return user;
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
	
	public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
	
	@SuppressWarnings("deprecation")
	private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(FinanceConstants.APIKEY).parseClaimsJws(token).getBody();
    }
	private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
	public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
