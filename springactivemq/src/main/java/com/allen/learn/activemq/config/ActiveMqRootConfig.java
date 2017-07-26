package com.allen.learn.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Configuration
public class ActiveMqRootConfig {

    @Resource
    private Environment env;



    @Bean
    ActiveMQConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("failover:(tcp://192.168.66.98:61616,tcp://192.168.66.98:61626)");
        return connectionFactory ;
    }


//    @Bean
//    ActiveMQConnectionFactory connectionFactoryTwo(){
//        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
//        connectionFactory.setBrokerURL("failover:(tcp://192.168.66.98:61626)");
//        return connectionFactory ;
//    }


    @Bean(name = "queueDestination")
    Destination  createDestination(){
        //Destination destination = new ActiveMQQueue("allen-queue");
        Destination destination = new ActiveMQTopic("allen-topic");
        return destination ;
    }


    @Bean(name = "jmsTemplate")
    JmsTemplate createJmsTemplate () {
        JmsTemplate jmsTemplate = new JmsTemplate();
        jmsTemplate.setConnectionFactory(connectionFactory());
        jmsTemplate.setDefaultDestination(createDestination());
        jmsTemplate.setDeliveryPersistent(true);
        jmsTemplate.setReceiveTimeout(10000);
        return jmsTemplate ;
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
    MessageListener consumerMessageListener2(){
        return new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage tm = (TextMessage) message;
                try {
                    System.out.println("msg2:"+tm.getText());
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
        //topic和queue
        jmsContainer.setPubSubDomain(true);
        return jmsContainer ;
    }

    @Bean
    DefaultMessageListenerContainer jmsContainer2(){
        DefaultMessageListenerContainer jmsContainer = new DefaultMessageListenerContainer();
        jmsContainer.setConnectionFactory(connectionFactory());
        jmsContainer.setDestination(createDestination());
        jmsContainer.setMessageListener(consumerMessageListener2());
        //topic和queue
        jmsContainer.setPubSubDomain(true);
        return jmsContainer ;
    }


}
