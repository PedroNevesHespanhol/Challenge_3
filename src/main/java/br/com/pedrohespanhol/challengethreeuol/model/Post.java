package br.com.pedrohespanhol.challengethreeuol.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Valid
@Entity
public class Post {

    @Id
    @Min(1)
    @Max(100)
    private Long id;

    @Column(length = 500)
    private String title;

    @Column(length = 500)
    private String body;

    private boolean deleted = false;

}
