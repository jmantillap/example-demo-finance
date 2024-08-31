package work.javiermantilla.finance.utils;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import work.javiermantilla.finance.dto.UserContextSessionDTO;

@Component
public class JwtUtil {

	@SuppressWarnings("deprecation")
	public boolean validateJWT(String token) {
		try {
			Claims claims = Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(FinanceConstants.APIKEY))
					.parseClaimsJws(token)
					.getBody();
			
			Date now = new Date();
			return !now.after(claims.getExpiration());
		} catch (Exception e) {
			return false;
		}
	}
	
	@SuppressWarnings("deprecation")
	public UserContextSessionDTO extraerContextoJWT(String token) {

		UserContextSessionDTO user = new UserContextSessionDTO();
        SignatureAlgorithm algoritmoFirma = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(FinanceConstants.APIKEY);
        Key key = new SecretKeySpec(apiKeySecretBytes, algoritmoFirma.getJcaName());
        var parser = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        
        user.setIdUser(parser.get(FinanceConstants.TOKEN_IDUSER).toString());
        user.setName(parser.get(FinanceConstants.TOKEN_NAME).toString());        
        user.setExpirationDate(parser.getExpiration());
        return  user;
    }
}
