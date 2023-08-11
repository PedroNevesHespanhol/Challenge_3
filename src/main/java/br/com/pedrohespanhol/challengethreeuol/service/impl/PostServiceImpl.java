package br.com.pedrohespanhol.challengethreeuol.service.impl;

import br.com.pedrohespanhol.challengethreeuol.client.PostApiClient;
import br.com.pedrohespanhol.challengethreeuol.dto.CommentDTO;
import br.com.pedrohespanhol.challengethreeuol.dto.PostDTO;
import br.com.pedrohespanhol.challengethreeuol.enums.State;
import br.com.pedrohespanhol.challengethreeuol.jms.Producer;
import br.com.pedrohespanhol.challengethreeuol.model.Comment;
import br.com.pedrohespanhol.challengethreeuol.model.History;
import br.com.pedrohespanhol.challengethreeuol.model.Post;
import br.com.pedrohespanhol.challengethreeuol.repository.CommentRepository;
import br.com.pedrohespanhol.challengethreeuol.repository.HistoryRepository;
import br.com.pedrohespanhol.challengethreeuol.repository.PostRepository;
import br.com.pedrohespanhol.challengethreeuol.service.PostService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService{

    private final PostApiClient postApiClient;
    private final PostRepository postRepository;
    private final HistoryRepository historyRepository;
    private final CommentRepository commentRepository;
    private final Producer producer;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;

    @Value("${activemq.name}")
    private String destinationQueue;

    public PostServiceImpl(PostApiClient postApiClient,
                           PostRepository postRepository,
                           HistoryRepository historyRepository,
                           CommentRepository commentRepository,
                           Producer producer, ModelMapper mapper, ObjectMapper objectMapper) {
        this.postApiClient = postApiClient;
        this.postRepository = postRepository;
        this.historyRepository = historyRepository;
        this.commentRepository = commentRepository;
        this.producer = producer;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

//    private History createHistory(State state, Long postId) {
//        History status = historyRepository.save(new History(state, postId));
//
//        return status;
//    }

    @Override
    @Async
    public void processPost(Long postId) throws JsonProcessingException {
        if (postId < 1 || postId > 100) throw new RuntimeException("Id must be minimum 1 and maximum 100!");

        Post post = new Post();
        post.setId(postId);

        if (postRepository.findById(postId).isPresent())
            throw new RuntimeException(String.format("Id %d is already in use", postId));

        postRepository.save(post);

        historyRepository.save(new History(State.CREATED, postId));
    }

    @Override
    @Async
    public void postFind(Long postId) throws JsonProcessingException {

        try {
            PostDTO postDTO = postApiClient.getPostById(postId).getBody();

            historyRepository.save(new History(State.POST_FIND, postId));

            postOk(postId, postDTO);

            String message = objectMapper.writeValueAsString(postDTO);
            producer.sendMessage(destinationQueue, message);
        } catch (Exception e) {

            historyRepository.save(new History(State.FAILED, postId));
            e.printStackTrace();
        }


    }

    @Override
    @Async
    public void postOk(Long postId, PostDTO postDTO) throws JsonProcessingException {

        Post post = postToEntity(postDTO);

        try {
            postRepository.save(post);
            historyRepository.save(new History(State.POST_OK, postId));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    @Async
    public void commentsFind(Long postId) throws JsonProcessingException {
        try {
            List<CommentDTO> commentDTOs = postApiClient.getComments(postId).getBody();

            for (CommentDTO commentDTO: commentDTOs
            ) {
                String message = objectMapper.writeValueAsString(commentDTO);
                producer.sendMessage(destinationQueue, message);
            }

            historyRepository.save(new History(State. COMMENTS_FIND, postId));

        } catch (Exception e) {

            historyRepository.save(new History(State.FAILED, postId));
            e.printStackTrace();
        }
    }

    @Override
    @Async
    public void commentsOk(Long postId) throws JsonProcessingException {

        List<CommentDTO> commentDTOs = postApiClient.getComments(postId).getBody();

        try {
            for (CommentDTO commentDTO: commentDTOs) {
                Comment newComment = commentToEntity(commentDTO);
                commentRepository.save(newComment);
            }

            historyRepository.save(new History(State.COMMENTS_OK, postId));
            historyRepository.save(new History(State.ENABLED, postId));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    @Async
    public void reprocessPost(Long postId) throws JsonProcessingException {
        if (postId < 1 || postId > 100) throw new RuntimeException("Id must be minimum 1 and maximum 100!");
    }

    @Override
    @Async
    public void disablePost(Long postId) throws JsonProcessingException {

    }

    @Override
    @Async
    public List<Post> queryPosts() {
        return postRepository.findAll();
    }

    private Post postToEntity(PostDTO postDTO) {
        return mapper.map(postDTO, Post.class);
    }

    private Comment commentToEntity(CommentDTO commentDTO) {
        return mapper.map(commentDTO, Comment.class);
    }
}
