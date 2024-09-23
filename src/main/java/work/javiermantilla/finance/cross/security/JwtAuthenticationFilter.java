package work.javiermantilla.finance.cross.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import work.javiermantilla.finance.cross.util.FinanceConstants;

@Component
@RequiredArgsConstructor
@Log4j2
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String MESSAGE_REQUIRED_HEADER = "Required headers not specified in the request";
	
	private final JwtUtil jWTUtils;
	
	@SuppressWarnings("null")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getMethod().equals("OPTIONS")) {
			return;
		}
		String path = request.getRequestURI();
		if (verifyPath(path)){
            filterChain.doFilter(request, response);
            return;
        }		
		String jwt = null;
        if(request.getCookies()==null) {
        	HttpServletResponse resp = response;
            resp.sendError(HttpServletResponse.SC_FORBIDDEN,MESSAGE_REQUIRED_HEADER);
            return;
        }
        
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("finance_token")){
                jwt = cookie.getValue();
                break;
            }
        }
        if(jwt!=null && jWTUtils.validateJWT(jwt)) {
        	String nameThread=jWTUtils.extraerContextoJWT(jwt).toString();
        	log.info("Se nombra el hilo del proceso: {}",nameThread);
            Thread.currentThread().setName(nameThread);            
            filterChain.doFilter(request, response);
            return;
        } else {
            HttpServletResponse resp = response;
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
        
	}
	
	private boolean verifyPath(String path) {
        Boolean response = false;
        for(String urlAdmitida : FinanceConstants.URLS_ADMITIDAS){            
            if(path.contains(urlAdmitida)){
                return true;
            }
        }
        return response;
    }

}
