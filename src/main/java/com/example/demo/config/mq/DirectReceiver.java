package com.example.demo.config.mq;

import com.example.demo.entity.EmailEntity;
import com.example.demo.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "TestDirectQueue")//监听的队列名称 TestDirectQueue
@Slf4j
public class DirectReceiver {
    @Autowired
    private EmailService emailService;

    @RabbitHandler
    public void process(EmailEntity emailEntity) {
        try {
            log.info("收到消息并发送邮件  : " + emailEntity.toString());
            emailService.send(emailEntity);
        } catch (Exception e) {
            log.info("发送邮件失败！" + e);
        }

    }
}
