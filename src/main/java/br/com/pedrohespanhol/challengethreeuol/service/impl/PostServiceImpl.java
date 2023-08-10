package br.com.pedrohespanhol.challengethreeuol.service.impl;

import br.com.pedrohespanhol.challengethreeuol.client.PostApiClient;
import br.com.pedrohespanhol.challengethreeuol.dto.HistoryDTO;
import br.com.pedrohespanhol.challengethreeuol.dto.PostDTO;
import br.com.pedrohespanhol.challengethreeuol.model.History;
import br.com.pedrohespanhol.challengethreeuol.model.Post;
import br.com.pedrohespanhol.challengethreeuol.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostApiClient postApiClient;

    public PostServiceImpl(PostApiClient postApiClient) {
        this.postApiClient = postApiClient;
    }

    @Override
    @Async
    public List<Post> getPosts() {

        return postApiClient.getPosts().getBody();
    }

}
