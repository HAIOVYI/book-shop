package mate.academy.mybookshop.security;

import java.io.IOException;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends HttpFilter {
    public static final String AUTHORIZATION_SCHEMA_BEARER = "Bearer";
    public static final int BEGIN_TOKEN_INDEX = 7;

    private final TokenUtil tokenUtil;

    @Override
    protected void doFilter(HttpServletRequest request,
                            HttpServletResponse response,
                            FilterChain chain
    ) throws IOException, ServletException {

        String token = extractToken(request);
        if (token != null && tokenUtil.isValidToken(token)) {
            String username = tokenUtil.extractUsername(token);
            var usernamePasswordAuthenticationToken
                    = new UsernamePasswordAuthenticationToken(username, null);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        chain.doFilter(request, response);
    }

    //TODO 1. add method generateToken (String usernanme)
    //TODO 2. send this token to the client?? when and how??
    //TODO 3. receive token from client
    //TODO 4. validate token and identify client

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken == null) {
            return null;
        }

        bearerToken = bearerToken.trim();
        if (!StringUtils.startsWithIgnoreCase(bearerToken, AUTHORIZATION_SCHEMA_BEARER)) {
            return null;
        }

        if (bearerToken.equalsIgnoreCase(AUTHORIZATION_SCHEMA_BEARER)) {
            throw new BadCredentialsException("Bad credentials");
        }

        return bearerToken.substring(BEGIN_TOKEN_INDEX);
    }
}
