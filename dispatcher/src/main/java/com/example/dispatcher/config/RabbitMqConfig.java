package com.example.dispatcher.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.constraints.util.RabbitConstants.*;


@Configuration
public class RabbitMqConfig {
    @Bean
    public MessageConverter jsonMessageConverter(){
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public Queue textQueue(){
        return new Queue(TEXT_MESSAGE_UPDATE);
    }
    @Bean
    public Queue documentQueue(){
        return new Queue(DOC_MESSAGE_UPDATE);
    }
    @Bean
    public Queue photoQueue(){
        return new Queue(PHOTO_MESSAGE_UPDATE);
    }
    @Bean
    public Queue audioQueue(){
        return new Queue(AUDIO_MESSAGE_UPDATE);
    }
    @Bean
    public Queue answerQueue(){
        return new Queue(ANSWER_MESSAGE);
    }
}
