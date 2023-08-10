package br.com.pedrohespanhol.challengethreeuol.repository;

import br.com.pedrohespanhol.challengethreeuol.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
