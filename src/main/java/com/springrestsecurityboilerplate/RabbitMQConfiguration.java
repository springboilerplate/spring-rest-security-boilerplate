package com.springrestsecurityboilerplate;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class RabbitMQConfiguration {

	@Bean
	public DirectExchange direct() {
		return new DirectExchange("email-direct");
	}

//	 @Profile("guest")
	private static class ReceiverConfig {

		@Bean
		public Queue registrationTokenMailQueue() {
			return new Queue("registrationTokenMailQueue"); //Just queue name in the rabbitmq, it can be different naming from other variables.
		}

		@Bean
		public Queue resendTokenMailQueue() {
			return new Queue("resendTokenMailQueue");
		}

		@Bean
		public Binding bindingRegistrationToken(DirectExchange direct, Queue registrationTokenMailQueue) {
			return BindingBuilder.bind(registrationTokenMailQueue).to(direct).with("registration");
		}

		@Bean
		public Binding bindingResendToken(DirectExchange direct, Queue resendTokenMailQueue) {
			return BindingBuilder.bind(resendTokenMailQueue).to(direct).with("resend-token");
		}

		@Bean
		public Mailer mailer() {
			return new Mailer();
		}
	}

//	 @Profile("sender")
//	 @Bean
//	 public RegistrationListener sender() {
//	 return new RegistrationListener();
//	 }

}
