package br.com.pedrohespanhol.challengethreeuol.model;

import br.com.pedrohespanhol.challengethreeuol.enums.State;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.beans.ConstructorProperties;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    private State state;

    @Column(name = "post_id")
    private Long postId;

    @ConstructorProperties({"state", "post"})
    public History(State state, Long postId) {
        this.state = state;
        this.postId = postId;
    }

}
