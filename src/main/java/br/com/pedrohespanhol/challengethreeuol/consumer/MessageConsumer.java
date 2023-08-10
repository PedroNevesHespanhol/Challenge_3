//package br.com.pedrohespanhol.challengethreeuol.consumer;
//
//import br.com.pedrohespanhol.challengethreeuol.model.History;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.jms.annotation.EnableJms;
//import org.springframework.jms.annotation.JmsListener;
//import org.springframework.stereotype.Component;
//
//@Component
//@EnableJms
//public class MessageConsumer {
//
//    private final Logger logger = LoggerFactory.getLogger(MessageConsumer.class);
//
//    @JmsListener(destination = "test-queue")
//    public void listener(String message) {
//
//        History history = parseMessageToHistory(message);
//
//        logger.info("Message received {} ", message);
//    }
//
//    private History parseMessageToHistory(String state) {
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            History history = objectMapper.readValue(state, History.class);
//            return history;
//        } catch (JsonProcessingException e) {
//            // Trate o erro, se necessário
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//}
