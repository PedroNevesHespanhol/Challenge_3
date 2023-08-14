package br.com.pedrohespanhol.challengethreeuol.controller;

import br.com.pedrohespanhol.challengethreeuol.dto.PostResponse;
import br.com.pedrohespanhol.challengethreeuol.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Void> processPost(@PathVariable(name = "postId") Long postId) throws JsonProcessingException {
        postService.processPost(postId);
        return ResponseEntity.ok(null);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> reprocessPost(@PathVariable(name = "postId") Long postId) throws JsonProcessingException {
        postService.reprocessPost(postId);
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> disablePost(@PathVariable(name = "postId") Long postId) throws JsonProcessingException {
        postService.disablePost(postId);
        return ResponseEntity.ok(null);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> queryPosts() {

        List<PostResponse> response = postService.queryPosts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
