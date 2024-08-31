package work.javiermantilla.finance.security;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import work.javiermantilla.finance.utils.FinanceConstants;
import work.javiermantilla.finance.utils.JwtUtil;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String MESSAGE_REQUIRED_HEADER = "Required headers not specified in the request";
	
	private final JwtUtil jWTUtils;
	
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
            if (cookie.getName().equals("access_token")){
                jwt = cookie.getValue();
                break;
            }
        }
        if(jwt!=null && jWTUtils.validateJWT(jwt)) {
            Thread.currentThread().setName(jWTUtils.extraerContextoJWT(jwt).toString());
            filterChain.doFilter(request, response);
            //return;
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
