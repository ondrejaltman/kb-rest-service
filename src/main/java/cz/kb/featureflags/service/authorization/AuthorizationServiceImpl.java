package cz.kb.featureflags.service.authorization;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kb.featureflags.dto.authorization.ApiKeysDTO;
import cz.kb.featureflags.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorizationServiceImpl implements AuthorizationService, InitializingBean {

    private static final String ALLOWED_API_KEYS_FILE = "/config/allowed-api-keys.json";

    private final ObjectMapper objectMapper;

    private Collection<String> allowedApiKeys = Collections.emptySet();

    @Override
    public void afterPropertiesSet() throws Exception {
        this.allowedApiKeys = loadAllowedApiKeys();
    }

    private Collection<String> loadAllowedApiKeys() throws IOException {
        log.info("loading allowed API keys");

        ApiKeysDTO apiKeys;
        try (InputStream is = this.getClass().getResourceAsStream(ALLOWED_API_KEYS_FILE)) {
            apiKeys = objectMapper.readValue(is, ApiKeysDTO.class);
        }

        Collection<String> keys = apiKeys.getKeys();
        log.info("{} allowed API keys successfully loaded", keys.size());

        return keys;
    }

    @Override
    public void validateApiKey(final String apiKey) throws UnauthorizedException {
        if (isApiKeyInvalid(apiKey)) {
            throw new UnauthorizedException(apiKey);
        }
    }

    private boolean isApiKeyInvalid(final String apiKey) {
        return apiKey == null
                || !allowedApiKeys.contains(apiKey);
    }

}
