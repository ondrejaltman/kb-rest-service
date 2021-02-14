package cz.kb.featureflags.service.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kb.featureflags.dto.configuration.ConfigurationDTO;
import cz.kb.featureflags.dto.github.GithubFileDTO;
import cz.kb.featureflags.service.GithubApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@ConditionalOnProperty(name = "configuration.implementation", havingValue = "github")
@Slf4j
@RequiredArgsConstructor
public class GithubConfigurationProvider implements ConfigurationProvider {

    private final ObjectMapper objectMapper;
    private final GithubApiService githubApiService;

    @Override
    public Optional<ConfigurationDTO> getConfiguration(final String name) {
        for (GithubFileDTO githubFile : githubApiService.loadFileInfos()) {
            try {
                Optional<String> fileContent = githubApiService.loadFileContent(githubFile.getPath());
                if (fileContent.isEmpty()) {
                    continue;
                }

                ConfigurationDTO configuration = objectMapper.readValue(fileContent.get(), ConfigurationDTO.class);
                if (Objects.equals(configuration.getName(), name)) {
                    return Optional.of(configuration);
                }
            } catch (JsonProcessingException e) {
                log.warn("exception occurred during loading of one of files", e);
            }
        }

        return Optional.empty();
    }

}
