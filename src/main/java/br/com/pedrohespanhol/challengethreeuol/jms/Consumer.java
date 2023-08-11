package br.com.pedrohespanhol.challengethreeuol.jms;

import br.com.pedrohespanhol.challengethreeuol.dto.CommentDTO;
import br.com.pedrohespanhol.challengethreeuol.dto.PostDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @JmsListener(destination = "${activemq.name}")
    public void receiveMessage(String message) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(message);

        if (jsonNode.has("postId")) {
            CommentDTO commentDTO = mapper.readValue(message, CommentDTO.class);
        } else {
            PostDTO postDTO = mapper.readValue(message, PostDTO.class);
        }
    }
}