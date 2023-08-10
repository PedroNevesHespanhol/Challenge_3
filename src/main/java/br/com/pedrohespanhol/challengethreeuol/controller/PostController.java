package br.com.pedrohespanhol.challengethreeuol.controller;

import br.com.pedrohespanhol.challengethreeuol.dto.PostHistoryDTO;
import br.com.pedrohespanhol.challengethreeuol.model.Comment;
import br.com.pedrohespanhol.challengethreeuol.model.Post;
import br.com.pedrohespanhol.challengethreeuol.repository.PostHistoryRepository;
import br.com.pedrohespanhol.challengethreeuol.service.impl.CommentServiceImpl;
import br.com.pedrohespanhol.challengethreeuol.service.impl.HistoryServiceImpl;
import br.com.pedrohespanhol.challengethreeuol.service.impl.PostHistoryServiceImpl;
import br.com.pedrohespanhol.challengethreeuol.service.impl.PostServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostHistoryRepository repository;
    private final CommentServiceImpl commentService;
    private final PostServiceImpl postService;

    private final PostHistoryServiceImpl postHistoryService;
    private final HistoryServiceImpl historyService;
    private final ObjectMapper mapper;

    public PostController(PostHistoryRepository repository,
                          CommentServiceImpl commentService,
                          PostServiceImpl postService,
                          PostHistoryServiceImpl postHistoryService, HistoryServiceImpl historyService, ObjectMapper mapper) {
        this.repository = repository;
        this.commentService = commentService;
        this.postService = postService;
        this.postHistoryService = postHistoryService;
        this.historyService = historyService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<PostHistoryDTO>> getAllPosts() {

        final long startTime = System.currentTimeMillis();

        final List<Post> posts = postService.getPosts();

        PostHistoryDTO postHistory = new PostHistoryDTO();
        List<PostHistoryDTO> listPosts = new ArrayList<>();
        for (Post post : posts) {
            final List<Comment> comments = commentService.getComments(post.getId());
            postHistory = PostHistoryDTO.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .body(post.getBody())
                    .comments(comments)
                    .build();
            listPosts.add(postHistory);
        }

        postHistoryService.createPostHistory(postHistory);

        final long endTime = System.currentTimeMillis();
        final float secFinal = (endTime - startTime) / 1000F;
        System.out.println("Total: " + secFinal);

        return new ResponseEntity<>(listPosts, HttpStatus.OK);
    }

}
