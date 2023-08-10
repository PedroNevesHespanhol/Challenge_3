package br.com.pedrohespanhol.challengethreeuol.service;

import br.com.pedrohespanhol.challengethreeuol.dto.PostDTO;
import br.com.pedrohespanhol.challengethreeuol.model.Post;

import java.util.List;


public interface PostService {

    List<Post> getPosts();

}
