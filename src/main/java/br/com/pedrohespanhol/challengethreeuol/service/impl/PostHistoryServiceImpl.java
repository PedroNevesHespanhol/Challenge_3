package br.com.pedrohespanhol.challengethreeuol.service.impl;

import br.com.pedrohespanhol.challengethreeuol.dto.CommentDTO;
import br.com.pedrohespanhol.challengethreeuol.dto.PostDTO;
import br.com.pedrohespanhol.challengethreeuol.dto.PostHistoryDTO;
import br.com.pedrohespanhol.challengethreeuol.model.Comment;
import br.com.pedrohespanhol.challengethreeuol.model.Post;
import br.com.pedrohespanhol.challengethreeuol.model.PostHistory;
import br.com.pedrohespanhol.challengethreeuol.repository.PostHistoryRepository;
import br.com.pedrohespanhol.challengethreeuol.service.PostHistoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class PostHistoryServiceImpl implements PostHistoryService {

    private final PostHistoryRepository repository;

    private final CommentServiceImpl commentService;
    private final PostServiceImpl postService;
    private final HistoryServiceImpl historyService;
    private final ModelMapper mapper;

    public PostHistoryServiceImpl(PostHistoryRepository repository,
                                  CommentServiceImpl commentService,
                                  PostServiceImpl postService,
                                  HistoryServiceImpl historyService,
                                  ModelMapper mapper) {
        this.repository = repository;
        this.commentService = commentService;
        this.postService = postService;
        this.historyService = historyService;
        this.mapper = mapper;
    }

    @Override
    public PostHistoryDTO createPostHistory(PostHistoryDTO postHistoryDTO) {

        final List<Post> posts = postService.getPosts();

        PostHistory newPost = mapToEntity(postHistoryDTO);
        for (Post post : posts) {
            final List<Comment> comments = commentService.getComments(post.getId());
            newPost = PostHistory.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .body(post.getBody())
                    .comments(comments)
                    .build();
            repository.save(newPost);
        }

        return mapToDTO(newPost);
    }

    private PostHistoryDTO mapToDTO(PostHistory postHistory) {
        return mapper.map(postHistory, PostHistoryDTO.class);
    }

    // convert DTO to Entity
    private PostHistory mapToEntity(PostHistoryDTO postHistoryDTO) {
        return mapper.map(postHistoryDTO, PostHistory.class);
    }

}
