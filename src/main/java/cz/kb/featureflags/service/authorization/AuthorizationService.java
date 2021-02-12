package cz.kb.featureflags.service.authorization;

import cz.kb.featureflags.exception.UnauthorizedException;

public interface AuthorizationService {

    void validateApiKey(final String apiKey) throws UnauthorizedException;

}
