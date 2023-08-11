package br.com.pedrohespanhol.challengethreeuol.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment {

    @Id
    private Long id;

    @Column(length = 1000)
    private String body;

    @Column(name = "post_id")
    private Long postId;

}
