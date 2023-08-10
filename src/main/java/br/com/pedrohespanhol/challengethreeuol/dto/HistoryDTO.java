package br.com.pedrohespanhol.challengethreeuol.dto;

import br.com.pedrohespanhol.challengethreeuol.enums.HistoryEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryDTO {

    private Long id;
    private LocalDateTime date;
    private HistoryEnum state;
}
