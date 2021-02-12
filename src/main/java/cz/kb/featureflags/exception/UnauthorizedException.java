package cz.kb.featureflags.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends FeatureFlagServiceException {

    private final String apiKey;

}
