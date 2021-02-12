package cz.kb.featureflags.dto.configuration;

import cz.kb.featureflags.enums.ConditionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConditionDTO {

    private ConditionType type;
    private String value;

}
