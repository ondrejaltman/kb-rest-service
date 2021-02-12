package cz.kb.featureflags.dto.authorization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeysDTO {

    private Collection<String> keys;

}
