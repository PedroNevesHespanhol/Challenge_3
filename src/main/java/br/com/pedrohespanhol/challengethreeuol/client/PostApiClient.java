package br.com.pedrohespanhol.challengethreeuol.client;

import br.com.pedrohespanhol.challengethreeuol.dto.CommentDTO;
import br.com.pedrohespanhol.challengethreeuol.dto.PostDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static br.com.pedrohespanhol.challengethreeuol.utils.AppConstants.API_URL;

@FeignClient(name = "postAPI", url = API_URL)
public interface PostApiClient {

    @GetMapping("/{postId}")
    PostDTO getPostById(@PathVariable(name = "postId") Long postId);

    @GetMapping("/{postId}/comments")
    List<CommentDTO> getComments(@PathVariable(name = "postId") Long postId);

}
