package cz.kb.featureflags.dto.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChecksumDTO {

    private byte[] checksum;

}
