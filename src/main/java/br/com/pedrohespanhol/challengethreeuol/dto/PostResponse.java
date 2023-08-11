package br.com.pedrohespanhol.challengethreeuol.dto;

import br.com.pedrohespanhol.challengethreeuol.model.Comment;
import br.com.pedrohespanhol.challengethreeuol.model.History;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponse {

    private Long id;

    @Column(length = 500)
    private String title;

    @Column(length = 500)
    private String body;

    private List<Comment> comments;

    private List<History> history;

}
