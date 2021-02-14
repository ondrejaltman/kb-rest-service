package cz.kb.featureflags.service;

import cz.kb.featureflags.config.GithubProperties;
import cz.kb.featureflags.dto.github.GithubFileDTO;
import cz.kb.featureflags.dto.github.GithubFilesListDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GithubApiService implements InitializingBean {

    private final GithubProperties githubProperties;
    private final RestTemplate restTemplate;

    private final HttpHeaders httpHeaders = new HttpHeaders();

    @Override
    public void afterPropertiesSet() {
        this.httpHeaders.setBasicAuth(githubProperties.getUser(), githubProperties.getToken());
    }

    public List<GithubFileDTO> loadFileInfos() {
        return get(githubProperties.getAllFilesUrl(), GithubFilesListDTO.class)
                .map(GithubFilesListDTO::getTree)
                .orElse(Collections.emptyList());
    }

    public Optional<String> loadFileContent(final String path) {
        return get(githubProperties.getSingleFileUrl() + path, String.class);
    }

    private <T> Optional<T> get(final String url, Class<T> resultClass) {
        ResponseEntity<T> responseEntity;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(httpHeaders), resultClass);
        } catch (RestClientException e) {
            log.warn("exception occurred during loading get {}", url, e);
            return Optional.empty();
        }

        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            log.warn("github API responded with HTTP {}", responseEntity.getStatusCode());
            return Optional.empty();
        }

        return Optional.ofNullable(responseEntity.getBody());
    }

}
