/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appsdeveloperblog.estore.OrdersService.command.rest;

import com.appsdeveloperblog.estore.OrdersService.core.model.OrderStatus;
import com.appsdeveloperblog.estore.OrdersService.core.model.OrderSummary;
import com.appsdeveloperblog.estore.OrdersService.query.FindOrderQuery;
import com.appsdeveloperblog.estore.OrdersService.command.commands.CreateOrderCommand;
import java.util.UUID;
import javax.validation.Valid;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrdersCommandController {

	private final CommandGateway commandGateway;
	private final QueryGateway queryGateway;

	@Autowired
	public OrdersCommandController(CommandGateway commandGateway, QueryGateway queryGateway) {
		this.commandGateway = commandGateway;
		this.queryGateway = queryGateway;
	}

	@PostMapping
	public OrderSummary createOrder(@Valid @RequestBody OrderCreateRest order) {

		String userId = "27b95829-4f3f-4ddf-8983-151ba010e35b";
		String orderId = UUID.randomUUID().toString();

		CreateOrderCommand createOrderCommand = CreateOrderCommand.builder().addressId(order.getAddressId())
				.productId(order.getProductId()).userId(userId).quantity(order.getQuantity()).orderId(orderId)
				.orderStatus(OrderStatus.CREATED).build();

		SubscriptionQueryResult<OrderSummary, OrderSummary> queryResult = queryGateway.subscriptionQuery(
				new FindOrderQuery(orderId), ResponseTypes.instanceOf(OrderSummary.class),
				ResponseTypes.instanceOf(OrderSummary.class));

		try {
			commandGateway.sendAndWait(createOrderCommand);
			return queryResult.updates().blockFirst();
		} finally {
			queryResult.close();
		}

	}

}
