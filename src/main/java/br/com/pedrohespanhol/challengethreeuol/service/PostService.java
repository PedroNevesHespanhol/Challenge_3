package br.com.pedrohespanhol.challengethreeuol.service;

import br.com.pedrohespanhol.challengethreeuol.dto.PostDTO;
import br.com.pedrohespanhol.challengethreeuol.model.Post;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.scheduling.annotation.Async;

import java.util.List;


public interface PostService {

    @Async
    void processPost(Long postId) throws JsonProcessingException;
    @Async
    void postFind(Long postId) throws JsonProcessingException;

    @Async
    void postOk(Long postId, PostDTO postDTO) throws JsonProcessingException;

    @Async
    void commentsFind(Long postId) throws JsonProcessingException;

    @Async
    void commentsOk(Long postId) throws JsonProcessingException;

    @Async
    void reprocessPost(Long postId) throws JsonProcessingException;

    @Async
    void disablePost(Long postId) throws JsonProcessingException;

    @Async
    List<Post> queryPosts();

}
