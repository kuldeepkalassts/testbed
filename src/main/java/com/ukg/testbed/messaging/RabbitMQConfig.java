package com.ukg.testbed.messaging;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RabbitMQConfig {

	@Value("${rmq.test.queue}")
	String queueName;

	@Value("${rmq.test.exchange}")
	String exchange;

	@Value("${rmq.test.routingkey}")
	private String routingkey;

	@Bean
	Queue kQueue() {
		return new Queue(queueName, false);
	}

	@Bean
	DirectExchange kExchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding kBinding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(routingkey);
	}

	@Bean
	public MessageConverter kJsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate kRabbitTemplate(CachingConnectionFactory connectionFactory) {
		final RabbitTemplate kRabbitTemplate = new RabbitTemplate(connectionFactory);
		kRabbitTemplate.setChannelTransacted(true);
		kRabbitTemplate.setMessageConverter(kJsonMessageConverter());
		return kRabbitTemplate;
	}

	@Bean
	public RetryTemplate kRabbitRetryTemplate() {
		
		RetryTemplate retryTemplate = new RetryTemplate();
	    ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
	    backOffPolicy.setInitialInterval(5);
	    backOffPolicy.setMultiplier(2.0);
	    backOffPolicy.setMaxInterval(50);
	    retryTemplate.setBackOffPolicy(backOffPolicy);
	    
	    return retryTemplate;
	}

}