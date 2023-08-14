package br.com.pedrohespanhol.challengethreeuol.service;

import br.com.pedrohespanhol.challengethreeuol.dto.PostResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.scheduling.annotation.Async;

import java.util.List;


public interface PostService {

    @Async
    void processPost(Long postId) throws JsonProcessingException;

    @Async
    void reprocessPost(Long postId) throws JsonProcessingException;

    @Async
    void disablePost(Long postId) throws JsonProcessingException;

    @Async
    List<PostResponse> queryPosts();

}
