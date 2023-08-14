package br.com.pedrohespanhol.challengethreeuol.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    @Autowired
    JmsTemplate jmsTemplate;
    public void sendMessage(String queue, final String message) {

        jmsTemplate.send(queue, new MessageCreator() {
            @Override
            public @NotNull Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(message);
            }
        });
    }

}
