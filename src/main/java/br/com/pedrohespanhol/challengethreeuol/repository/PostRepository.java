package br.com.pedrohespanhol.challengethreeuol.repository;

import br.com.pedrohespanhol.challengethreeuol.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
