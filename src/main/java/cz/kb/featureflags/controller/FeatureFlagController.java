package cz.kb.featureflags.controller;

import cz.kb.featureflags.dto.configuration.ChecksumDTO;
import cz.kb.featureflags.dto.configuration.ConfigurationDTO;
import cz.kb.featureflags.exception.FeatureFlagServiceException;
import cz.kb.featureflags.service.FeatureFlagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feature-flags")
@RequiredArgsConstructor
@Slf4j
public class FeatureFlagController {

    private final FeatureFlagService featureFlagService;

    @GetMapping("/{name}")
    public ConfigurationDTO getConfiguration(@PathVariable("name") final String name) throws FeatureFlagServiceException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ConfigurationDTO configuration = featureFlagService.getConfiguration(name);

        stopWatch.stop();
        log.debug("getConfiguration took {} ms", stopWatch.getLastTaskTimeMillis());

        return configuration;
    }

    @GetMapping("/{name}/checksum")
    public ChecksumDTO getConfigurationChecksum(@PathVariable("name") final String name) throws FeatureFlagServiceException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        ChecksumDTO checksum = featureFlagService.getConfigurationChecksum(name);

        stopWatch.stop();
        log.debug("getConfigurationChecksum took {} ms", stopWatch.getLastTaskTimeMillis());

        return checksum;
    }

}
