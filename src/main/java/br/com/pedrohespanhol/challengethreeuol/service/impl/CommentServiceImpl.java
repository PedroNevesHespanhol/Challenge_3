package br.com.pedrohespanhol.challengethreeuol.service.impl;

import br.com.pedrohespanhol.challengethreeuol.client.CommentApiClient;
import br.com.pedrohespanhol.challengethreeuol.dto.CommentDTO;
import br.com.pedrohespanhol.challengethreeuol.model.Comment;
import br.com.pedrohespanhol.challengethreeuol.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentApiClient commentApiClient;

    public CommentServiceImpl(CommentApiClient commentApiClient) {
        this.commentApiClient = commentApiClient;
    }

    @Override
    @Async
    public List<Comment> getComments(Long postId) {

        return commentApiClient.getComments(postId).getBody();
    }

}
