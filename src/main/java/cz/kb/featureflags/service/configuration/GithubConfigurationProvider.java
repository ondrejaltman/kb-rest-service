package cz.kb.featureflags.service.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kb.featureflags.config.GithubProperties;
import cz.kb.featureflags.dto.configuration.ConfigurationDTO;
import cz.kb.featureflags.dto.github.GithubFileDTO;
import cz.kb.featureflags.dto.github.GithubFilesListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@ConditionalOnProperty(name = "configuration.implementation", havingValue = "github")
@Slf4j
@RequiredArgsConstructor
public class GithubConfigurationProvider implements ConfigurationProvider {

    private final ObjectMapper objectMapper;
    private final GithubProperties githubProperties;
    private final RestTemplate restTemplate;

    @Override
    public Optional<ConfigurationDTO> getConfiguration(final String name) {
        for (GithubFileDTO githubFile : loadFileInfos()) {
            try {
                Optional<String> fileContent = loadFileContent(githubFile.getPath());
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

    private List<GithubFileDTO> loadFileInfos() {
        GithubFilesListDTO filesList;
        try {
            filesList = restTemplate.getForObject(githubProperties.getAllFilesUrl(), GithubFilesListDTO.class);
        } catch (RestClientException e) {
            log.warn("exception occurred during loading of list of files", e);
            return Collections.emptyList();
        }

        return Optional.ofNullable(filesList)
                .map(GithubFilesListDTO::getTree)
                .orElse(Collections.emptyList());
    }

    private Optional<String> loadFileContent(final String path) {
        try {
            String fileContent = restTemplate.getForObject(githubProperties.getSingleFileUrl() + path, String.class);
            return Optional.ofNullable(fileContent);
        } catch (RestClientException e) {
            log.warn("exception occurred during loading of file '{}' content", path, e);
            return Optional.empty();
        }
    }

}
