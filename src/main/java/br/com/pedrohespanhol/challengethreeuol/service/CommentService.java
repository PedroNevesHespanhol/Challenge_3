package br.com.pedrohespanhol.challengethreeuol.service;

import br.com.pedrohespanhol.challengethreeuol.dto.CommentDTO;
import br.com.pedrohespanhol.challengethreeuol.model.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getComments(Long postId);

}
