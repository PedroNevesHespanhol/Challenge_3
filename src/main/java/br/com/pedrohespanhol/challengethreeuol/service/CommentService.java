package br.com.pedrohespanhol.challengethreeuol.service;

import br.com.pedrohespanhol.challengethreeuol.model.Comment;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

public interface CommentService {

    @Async
    List<Comment> getAllComments(Long postId);
}