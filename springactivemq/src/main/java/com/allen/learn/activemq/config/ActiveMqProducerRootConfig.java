package com.allen.learn.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.Destination;

//@Configuration
public class ActiveMqProducerRootConfig {

    @Bean
    ActiveMQConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("failover:(tcp://192.168.66.98:61616)");
        return connectionFactory ;
    }


    @Bean(name = "queueDestination")
    Destination createDestination(){
        Destination destination = new ActiveMQTopic("allen-topic");
        return destination ;
    }


    @Bean(name = "jmsTemplate")
    JmsTemplate createJmsTemplate () {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setDefaultDestination(createDestination());
        jmsTemplate.setReceiveTimeout(10000);
        return jmsTemplate ;
    }
}
