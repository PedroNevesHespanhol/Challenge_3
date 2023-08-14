package br.com.pedrohespanhol.challengethreeuol.jms;

import br.com.pedrohespanhol.challengethreeuol.client.PostApiClient;
import br.com.pedrohespanhol.challengethreeuol.dto.CommentDTO;
import br.com.pedrohespanhol.challengethreeuol.dto.PostDTO;
import br.com.pedrohespanhol.challengethreeuol.enums.State;
import br.com.pedrohespanhol.challengethreeuol.model.Comment;
import br.com.pedrohespanhol.challengethreeuol.model.History;
import br.com.pedrohespanhol.challengethreeuol.model.Post;
import br.com.pedrohespanhol.challengethreeuol.repository.CommentRepository;
import br.com.pedrohespanhol.challengethreeuol.repository.HistoryRepository;
import br.com.pedrohespanhol.challengethreeuol.repository.PostRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Consumer {

    private final PostApiClient postApiClient;
    private final PostRepository postRepository;
    private final HistoryRepository historyRepository;
    private final CommentRepository commentRepository;
    private final ModelMapper mapper;

    public Consumer(PostApiClient postApiClient,
                    PostRepository postRepository,
                    HistoryRepository historyRepository,
                    CommentRepository commentRepository,
                    ModelMapper mapper) {
        this.postApiClient = postApiClient;
        this.postRepository = postRepository;
        this.historyRepository = historyRepository;
        this.commentRepository = commentRepository;
        this.mapper = mapper;
    }

    @JmsListener(destination = "posts.queue")
    public void receivePost(String message) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(message);

        if (jsonNode.has("postId")) {
            History history = objectMapper.readValue(message, History.class);
            if (history.getState().equals(State.ENABLED)) {
                historyRepository.save(new History(State.UPDATING, history.getPostId()));
                historyRepository.save(new History(State.ENABLED, history.getPostId()));
            }

            if (history.getState().equals(State.FAILED)) {
                historyRepository.save(new History(State.DISABLED, history.getPostId()));
            }
        } else {

            Post post = objectMapper.readValue(message, Post.class);

            if (post != null) historyRepository.save(new History(State.POST_FIND, post.getId()));

            postRepository.save(post);
            if (postRepository.save(post).getId().equals(post.getId())) {
                historyRepository.save(new History(State.POST_OK, post.getId()));
            }

            // Fetch post comments from external API
            List<CommentDTO> commentDTOs = postApiClient.getComments(post.getId());

            if (commentDTOs.isEmpty()) {
                historyRepository.save(new History(State.FAILED, post.getId()));
            }
            historyRepository.save(new History(State.COMMENTS_FIND, post.getId()));

            for (CommentDTO commentDTO : commentDTOs) {
                Comment newComment = commentToEntity(commentDTO);
                commentRepository.save(newComment);
            }
            historyRepository.save(new History(State.COMMENTS_OK, post.getId()));
            historyRepository.save(new History(State.ENABLED, post.getId()));
        }

    }

    private Comment commentToEntity(CommentDTO commentDTO) {
        return mapper.map(commentDTO, Comment.class);
    }
}