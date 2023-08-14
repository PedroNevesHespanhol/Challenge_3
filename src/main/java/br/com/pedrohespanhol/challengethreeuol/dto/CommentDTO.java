package br.com.pedrohespanhol.challengethreeuol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("body")
    @Column(length = 1000)
    private String body;

    @JsonProperty("postId")
    private Long postId;
}
