package br.com.pedrohespanhol.challengethreeuol.repository;

import br.com.pedrohespanhol.challengethreeuol.model.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByPostId(Long postId);
}
