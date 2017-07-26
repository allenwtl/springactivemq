package com.allen.learn.activemq.consumer;


import com.allen.learn.activemq.config.ActiveMqRootConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ActiveMqRootConfig.class})
public class QueueReceiver {

    @Resource
    private JmsTemplate jmsTemplate ;

    @Test
    public void receiverMqMessage(){
        Destination destination = jmsTemplate.getDefaultDestination() ;
        receive(destination);
    }


    public void receive(Destination destination){
        TextMessage tm = (TextMessage) jmsTemplate.receive(destination);
        try {
            System.out.println("从队列" + destination.toString() + "收到了消息：\t" + tm.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }




}
