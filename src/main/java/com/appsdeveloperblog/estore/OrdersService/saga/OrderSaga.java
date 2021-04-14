package com.appsdeveloperblog.estore.OrdersService.saga;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import com.appsdeveloperblog.estore.OrdersService.core.events.OrderCreatedEvent;

@Saga
public class OrderSaga {
	
	@Autowired
	private transient CommandGateway commandGateway;
	
	@StartSaga
	@SagaEventHandler(associationProperty="orderId")
	public void handle(OrderCreatedEvent orderCreatedEvent) {
		
	}

}
