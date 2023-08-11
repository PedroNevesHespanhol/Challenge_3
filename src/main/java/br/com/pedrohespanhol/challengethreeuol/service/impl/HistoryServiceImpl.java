package br.com.pedrohespanhol.challengethreeuol.service.impl;

import br.com.pedrohespanhol.challengethreeuol.model.History;
import br.com.pedrohespanhol.challengethreeuol.repository.HistoryRepository;
import br.com.pedrohespanhol.challengethreeuol.service.HistoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService{

    private final HistoryRepository repository;

    public HistoryServiceImpl(HistoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<History> getAllHistory(Long postId) {
        return repository.findByPostId(postId);
    }
}
