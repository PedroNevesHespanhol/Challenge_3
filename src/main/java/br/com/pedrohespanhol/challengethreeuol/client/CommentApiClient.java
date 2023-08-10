package br.com.pedrohespanhol.challengethreeuol.client;

import br.com.pedrohespanhol.challengethreeuol.model.Comment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static br.com.pedrohespanhol.challengethreeuol.utils.AppConstants.API_URL;

@FeignClient(name = "comment", url = API_URL)
public interface CommentApiClient {

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<List<Comment>> getComments(@PathVariable final Long postId);
}
