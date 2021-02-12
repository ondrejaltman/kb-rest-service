package cz.kb.featureflags.service.checksum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.kb.featureflags.dto.configuration.ConfigurationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChecksumServiceImpl implements ChecksumService {

    private final ObjectMapper objectMapper;

    @Override
    public byte[] getChecksum(final ConfigurationDTO configuration) {
        log.debug("calculating checksum for configuration '{}'", configuration.getName());

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(objectMapper.writeValueAsBytes(configuration));
        } catch (NoSuchAlgorithmException | JsonProcessingException e) {
            log.warn("exception occurred during checksum calculation", e);
            throw new IllegalStateException(e.getMessage());
        }
    }

}
