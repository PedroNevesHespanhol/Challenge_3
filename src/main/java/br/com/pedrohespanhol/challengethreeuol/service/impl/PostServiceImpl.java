package br.com.pedrohespanhol.challengethreeuol.service.impl;

import br.com.pedrohespanhol.challengethreeuol.client.PostApiClient;
import br.com.pedrohespanhol.challengethreeuol.dto.PostDTO;
import br.com.pedrohespanhol.challengethreeuol.dto.PostResponse;
import br.com.pedrohespanhol.challengethreeuol.enums.State;
import br.com.pedrohespanhol.challengethreeuol.jms.Producer;
import br.com.pedrohespanhol.challengethreeuol.model.Comment;
import br.com.pedrohespanhol.challengethreeuol.model.History;
import br.com.pedrohespanhol.challengethreeuol.model.Post;
import br.com.pedrohespanhol.challengethreeuol.repository.HistoryRepository;
import br.com.pedrohespanhol.challengethreeuol.repository.PostRepository;
import br.com.pedrohespanhol.challengethreeuol.service.CommentService;
import br.com.pedrohespanhol.challengethreeuol.service.HistoryService;
import br.com.pedrohespanhol.challengethreeuol.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class PostServiceImpl implements PostService{

    private final PostApiClient postApiClient;
    private final PostRepository postRepository;
    private final HistoryRepository historyRepository;
    private final CommentService commentService;
    private final HistoryService historyService;
    private final Producer producer;
    private final ObjectMapper objectMapper;
    private final ExecutorService executorService = Executors.newFixedThreadPool(5);

    @Value("posts.queue")
    private String destinationQueue;

    public PostServiceImpl(PostApiClient postApiClient,
                           PostRepository postRepository,
                           HistoryRepository historyRepository,
                           CommentService commentService,
                           HistoryService historyService,
                           Producer producer,
                           ObjectMapper objectMapper) {
        this.postApiClient = postApiClient;
        this.postRepository = postRepository;
        this.historyRepository = historyRepository;
        this.commentService = commentService;
        this.historyService = historyService;
        this.producer = producer;
        this.objectMapper = objectMapper;
    }

    @Override
    @Async
    public void processPost(Long postId) {
        executorService.submit(() -> {
            PostDTO postDTO = postApiClient.getPostById(postId);

            if (postRepository.findById(postId).isEmpty()) historyRepository.save(new History(State.CREATED, postId));

            try {
                producer.sendMessage(destinationQueue, objectMapper.writeValueAsString(postDTO));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        });
    }

    @Override
    @Async
    public void disablePost(Long postId) {
        executorService.submit(() -> {
            List<History> histories = new ArrayList<>();
            if (postId != null) {
                histories = historyRepository.findByPostId(postId);
            }
            int lastIndex = 0;
            if (!histories.isEmpty()) {
                lastIndex = histories.size() - 1;
            }
            History history= histories.get(lastIndex);
            if (history.getState().equals(State.FAILED)){
                Map<String, Object> simplifiedHistory = new HashMap<>();
                simplifiedHistory.put("id", history.getId());
                simplifiedHistory.put("postId", history.getPostId());
                simplifiedHistory.put("state", history.getState());
                try {
                    producer.sendMessage(destinationQueue, objectMapper.writeValueAsString(simplifiedHistory));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
            Optional<Post> post = postRepository.findById(postId);

            if (post.isPresent()){
                Post deletedPost = post.get();
                deletedPost.setDeleted(true);
                postRepository.save(deletedPost);
                historyRepository.save(new History(State.DISABLED, postId));
            }

        });
    }

    @Override
    @Async
    public void reprocessPost(Long postId) {

        executorService.submit(() -> {
            List<History> histories = new ArrayList<>();
            if (postId != null) {
                histories = historyRepository.findByPostId(postId);
            }
            int lastIndex = 0;
            if (!histories.isEmpty()) {
                lastIndex = histories.size() - 1;
            }
            History history= histories.get(lastIndex);
            if (history.getState().equals(State.ENABLED)){
                Map<String, Object> simplifiedHistory = new HashMap<>();
                simplifiedHistory.put("id", history.getId());
                simplifiedHistory.put("postId", history.getPostId());
                simplifiedHistory.put("state", history.getState());
                try {
                    producer.sendMessage(destinationQueue, objectMapper.writeValueAsString(simplifiedHistory));
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            if (history.getState().equals(State.DISABLED)) {
                historyRepository.save(new History(State.UPDATING, postId));
                Optional<Post> post = postRepository.findById(postId);

                if (post.isPresent()) {
                    Post updatePost = post.get();
                    updatePost.setDeleted(false);
                    postRepository.save(updatePost);
                    historyRepository.save(new History(State.ENABLED, postId));
                }
            }

        });
    }

    @Override
    @Async
    public List<PostResponse> queryPosts() {

        List<Post> posts =  postRepository.findAll();

        return posts.stream().map(post -> {
            List<Comment> comments = commentService.getAllComments(post.getId());
            List<History> histories = historyService.getAllHistory(post.getId());
            return PostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .body(post.getBody())
                    .comments(comments)
                    .history(histories)
                    .build();
        }).toList();
    }

}
