package cz.kb.featureflags.config.security;

import cz.kb.featureflags.service.authorization.AuthorizationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityConfig {

    @Bean
    public ApiKeyFilter apiKeyFilter(final AuthorizationService authorizationService) {
        return new ApiKeyFilter(authorizationService);
    }

}
