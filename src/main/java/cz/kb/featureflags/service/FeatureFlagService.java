package cz.kb.featureflags.service;

import cz.kb.featureflags.dto.configuration.ChecksumDTO;
import cz.kb.featureflags.dto.configuration.ConfigurationDTO;
import cz.kb.featureflags.exception.ConfigurationNotFoundException;
import cz.kb.featureflags.service.checksum.ChecksumService;
import cz.kb.featureflags.service.configuration.ConfigurationProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FeatureFlagService {

    private final ConfigurationProvider configurationProvider;
    private final ChecksumService checksumService;

    public ConfigurationDTO getConfiguration(String name) throws ConfigurationNotFoundException {
        log.debug("getting configuration by name = {}", name);

        return configurationProvider.getConfiguration(name)
                .orElseThrow(() -> {
                    log.debug("configuration '{}' not found", name);
                    return new ConfigurationNotFoundException(name);
                });
    }

    public ChecksumDTO getConfigurationChecksum(String name) throws ConfigurationNotFoundException {
        log.debug("getting configuration checksum by name = {}", name);

        return configurationProvider.getConfiguration(name)
                .map(c -> new ChecksumDTO(checksumService.getChecksum(c)))
                .orElseThrow(() -> {
                    log.debug("configuration '{}' not found", name);
                    return new ConfigurationNotFoundException(name);
                });
    }

}
