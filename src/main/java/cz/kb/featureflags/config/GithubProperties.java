package cz.kb.featureflags.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("github")
public class GithubProperties {

    private String user;
    private String token;
    private String repository;
    private String branch;

    public String getAllFilesUrl() {
        return String.format("https://api.github.com/repos/%s/%s/git/trees/%s",
                this.user, this.repository, this.branch);
    }

    public String getSingleFileUrl() {
        return String.format("https://raw.githubusercontent.com/%s/%s/%s/",
                this.user, this.repository, this.branch);
    }

}
