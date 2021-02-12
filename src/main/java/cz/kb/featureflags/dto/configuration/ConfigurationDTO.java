package cz.kb.featureflags.dto.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigurationDTO {

    private String name;
    private Collection<ConditionDTO> conditions;

}
