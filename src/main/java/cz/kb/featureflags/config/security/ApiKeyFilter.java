package cz.kb.featureflags.config.security;

import cz.kb.featureflags.exception.UnauthorizedException;
import cz.kb.featureflags.service.authorization.AuthorizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class ApiKeyFilter extends GenericFilterBean {

    private static final String API_KEY_HEADER = "X-Api-Key";

    private final AuthorizationService authorizationService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String apiKey = ((HttpServletRequest) request).getHeader(API_KEY_HEADER);

        try {
            authorizationService.validateApiKey(apiKey);
        } catch (UnauthorizedException e) {
            log.debug("API key '{}' unauthorized", apiKey);

            HttpServletResponse resp = (HttpServletResponse) response;
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        log.debug("API key '{}' authorized", apiKey);
        filterChain.doFilter(request, response);
    }

}
