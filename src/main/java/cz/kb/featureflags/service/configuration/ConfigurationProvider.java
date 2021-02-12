package cz.kb.featureflags.service.configuration;

import cz.kb.featureflags.dto.configuration.ConfigurationDTO;

import java.util.Optional;

public interface ConfigurationProvider {

    Optional<ConfigurationDTO> getConfiguration(final String name);

}
