package cz.kb.featureflags.service.checksum;

import cz.kb.featureflags.dto.configuration.ConfigurationDTO;

public interface ChecksumService {

    byte[] getChecksum(final ConfigurationDTO configuration);

}
