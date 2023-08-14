package br.com.pedrohespanhol.challengethreeuol.service;

import br.com.pedrohespanhol.challengethreeuol.dto.CommentDTO;
import br.com.pedrohespanhol.challengethreeuol.dto.PostDTO;
import br.com.pedrohespanhol.challengethreeuol.dto.PostResponse;
import br.com.pedrohespanhol.challengethreeuol.model.Comment;
import br.com.pedrohespanhol.challengethreeuol.model.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.scheduling.annotation.Async;

import java.util.List;


public interface PostService {

    void processPost(Long postId) throws JsonProcessingException;

    void reprocessPost(Long postId) throws JsonProcessingException;

    void disablePost(Long postId) throws JsonProcessingException;

    List<PostResponse> queryPosts();

}
