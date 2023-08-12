package br.com.pedrohespanhol.challengethreeuol.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Null;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class PostDTO {

    @JsonProperty("id")
    @Min(1)
    @Max(100)
    private Long id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("body")
    private String body;

}