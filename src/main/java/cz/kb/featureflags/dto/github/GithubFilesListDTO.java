package cz.kb.featureflags.dto.github;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GithubFilesListDTO {

    private List<GithubFileDTO> tree;

}
