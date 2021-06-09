package com.ukg.testbed.service.impl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.rabbitmq.client.impl.AMQChannel;
import com.ukg.testbed.dto.EmpDTO;

@Service
public class RabbitMQSender {
	
	@Autowired
	private RabbitTemplate kRabbitTemplate;
	
	@Autowired
	private RetryTemplate kRetryRabbitTemplate;
	
	@Value("${rmq.test.exchange}")
	private String exchange;
	
	@Value("${rmq.test.routingkey}")
	private String routingkey;	
	
	public void send(EmpDTO empDTO) {
		kRabbitTemplate.setChannelTransacted(true);
		kRabbitTemplate.convertAndSend(exchange, routingkey, empDTO);
	}
	
	public void sendWithRetryTemplate(EmpDTO empDTO) throws Exception {
		
		kRetryRabbitTemplate.execute(new RetryCallback<Object, Exception>() {

			@Override
			public Object doWithRetry(RetryContext context) throws Exception {
				context.setAttribute("message", empDTO);
				kRabbitTemplate.setChannelTransacted(true);
				AMQChannel.doNotSendMessage = true;
				kRabbitTemplate.convertAndSend(exchange, routingkey, empDTO);
				return null;
			}
		}, new RecoveryCallback<Object>() {
			@Override
			public Object recover(RetryContext context) throws Exception {
				Object message = context.getAttribute("message");
				Throwable t = context.getLastThrowable();
				AMQChannel.doNotSendMessage = false;
				kRabbitTemplate.setChannelTransacted(true);
				kRabbitTemplate.convertAndSend(exchange, routingkey, empDTO);
				return null;
			}
		});

	}
}