package cz.kb.featureflags.service.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kb.featureflags.dto.configuration.ConfigurationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Service
@ConditionalOnProperty(name = "configuration.implementation", havingValue = "local")
@RequiredArgsConstructor
@Slf4j
public class LocalConfigurationProvider implements ConfigurationProvider {

    private static final String RESOURCES_PATTERN = "classpath:feature-flags/*.json";

    private final ResourcePatternResolver resourcePatternResolver;
    private final ObjectMapper objectMapper;

    @Override
    public Optional<ConfigurationDTO> getConfiguration(final String name) {
        for (Resource resource : loadResources()) {
            ConfigurationDTO configuration;
            try {
                configuration = objectMapper.readValue(resource.getFile(), ConfigurationDTO.class);
            } catch (IOException e) {
                log.warn("exception occurred during loading of one of resources '{}'", RESOURCES_PATTERN, e);
                continue;
            }

            if (Objects.equals(configuration.getName(), name)) {
                return Optional.of(configuration);
            }
        }

        return Optional.empty();
    }

    private Resource[] loadResources() {
        try {
            return resourcePatternResolver.getResources(RESOURCES_PATTERN);
        } catch (IOException e) {
            log.warn("exception occurred during loading of resources '{}'", RESOURCES_PATTERN, e);
            return new Resource[0];
        }
    }

}
