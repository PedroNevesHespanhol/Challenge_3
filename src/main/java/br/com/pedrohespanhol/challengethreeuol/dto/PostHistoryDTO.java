package br.com.pedrohespanhol.challengethreeuol.dto;

import br.com.pedrohespanhol.challengethreeuol.model.Comment;
import br.com.pedrohespanhol.challengethreeuol.model.History;
import br.com.pedrohespanhol.challengethreeuol.model.Post;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostHistoryDTO {

    @JsonProperty("id")
    private Long id;

    @NotEmpty
    @JsonProperty("title")
    private String title;

    @NotEmpty
    @JsonProperty("body")
    private String body;

    @NotEmpty
    @JsonProperty("comments")
    private List<Comment> comments;

    @JsonProperty("history")
    private Set<History> history;

}
