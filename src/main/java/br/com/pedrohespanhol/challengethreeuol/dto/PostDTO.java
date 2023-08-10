package br.com.pedrohespanhol.challengethreeuol.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {

    @Min(1)
    @Max(100)
    private Long id;

    private String title;
    private String body;

}