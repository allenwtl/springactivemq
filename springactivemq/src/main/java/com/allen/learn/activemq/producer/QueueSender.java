package com.allen.learn.activemq.producer;


import com.allen.learn.activemq.config.ActiveMqRootConfig;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.Calendar;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= {ActiveMqRootConfig.class})
public class QueueSender{

    @Resource
    private JmsTemplate jmsTemplate;

    @Test
    public void send(){
        sendMqMessage(null, "spring activemq queue type message"+ Calendar.getInstance().getTimeInMillis());
    }


    public void sendMqMessage(Destination destination, final String message){
        if(null == destination){
            destination = jmsTemplate.getDefaultDestination() ;
        }

        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });

        System.out.println("spring send message ....");
    }

}
