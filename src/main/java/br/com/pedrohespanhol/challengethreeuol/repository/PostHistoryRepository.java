package br.com.pedrohespanhol.challengethreeuol.repository;

import br.com.pedrohespanhol.challengethreeuol.model.Post;
import br.com.pedrohespanhol.challengethreeuol.model.PostHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHistoryRepository extends JpaRepository<PostHistory, Long> {
}
