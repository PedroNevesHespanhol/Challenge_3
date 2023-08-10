package br.com.pedrohespanhol.challengethreeuol.client;

import br.com.pedrohespanhol.challengethreeuol.model.Post;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static br.com.pedrohespanhol.challengethreeuol.utils.AppConstants.API_URL;

@FeignClient(name = "post", url = API_URL)
public interface PostApiClient {

    @GetMapping("/posts")
    ResponseEntity<List<Post>> getPosts();

}
