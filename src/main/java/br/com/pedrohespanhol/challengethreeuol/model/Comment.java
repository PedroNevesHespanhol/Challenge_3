package br.com.pedrohespanhol.challengethreeuol.model;

import jakarta.persistence.*;
import lombok.*;

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
