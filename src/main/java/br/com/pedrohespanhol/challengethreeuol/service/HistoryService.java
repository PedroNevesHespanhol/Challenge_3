package br.com.pedrohespanhol.challengethreeuol.service;

import br.com.pedrohespanhol.challengethreeuol.model.History;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface HistoryService {

    @Async
    List<History> getAllHistory(Long postId);
}
