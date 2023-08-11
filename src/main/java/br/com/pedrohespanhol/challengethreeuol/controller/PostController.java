package br.com.pedrohespanhol.challengethreeuol.controller;

import br.com.pedrohespanhol.challengethreeuol.dto.PostResponse;
import br.com.pedrohespanhol.challengethreeuol.model.Comment;
import br.com.pedrohespanhol.challengethreeuol.model.History;
import br.com.pedrohespanhol.challengethreeuol.model.Post;
import br.com.pedrohespanhol.challengethreeuol.service.CommentService;
import br.com.pedrohespanhol.challengethreeuol.service.HistoryService;
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
    private final CommentService commentService;
    private final HistoryService historyService;

    public PostController(PostService postService, CommentService commentService, HistoryService historyService) {
        this.postService = postService;
        this.commentService = commentService;
        this.historyService = historyService;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> postFind(@PathVariable Long postId) throws JsonProcessingException {

        postService.postFind(postId);
        postService.postOk(postId);
        postService.commentsFind(postId);
        postService.commentsOk(postId);

        List<Post> posts = postService.getAllPosts();

        PostResponse response = posts.stream().map(post -> {
            List<Comment> comments = commentService.getAllComments(post.getId());
            List<History> histories = historyService.getAllHistory(post.getId());
            return PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .body(post.getBody())
                    .comments(comments)
                    .history(histories)
                    .build();
        }).toList().get(0);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
