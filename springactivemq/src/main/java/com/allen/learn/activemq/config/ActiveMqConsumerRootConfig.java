package com.allen.learn.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

//@Configuration
public class ActiveMqConsumerRootConfig {

    @Bean
    ActiveMQConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("failover:(tcp://192.168.66.98:61616)");
        return connectionFactory ;
    }


    @Bean(name = "queueDestination")
    Destination createDestination(){
        //Destination destination = new ActiveMQQueue("allen-queue");
        Destination destination = new ActiveMQTopic("allen-topic");
        return destination ;
    }



    @Bean
    MessageListener consumerMessageListener(){
        return new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage tm = (TextMessage) message;
                try {
                    System.out.println("msg:"+tm.getText());
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        } ;
    }

    @Bean
    DefaultMessageListenerContainer jmsContainer(){
        DefaultMessageListenerContainer jmsContainer = new DefaultMessageListenerContainer();
        jmsContainer.setConnectionFactory(connectionFactory());
        jmsContainer.setDestination(createDestination());
        jmsContainer.setMessageListener(consumerMessageListener());
        return jmsContainer ;
    }

}
