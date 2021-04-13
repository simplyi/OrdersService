package com.appsdeveloperblog.estore.OrdersService.command.rest;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderCreateRest {

    @NotBlank(message = "Order productId is a required field")
    private String productId;
    
    @Min(value = 1, message = "Quantity cannot be lower than 1")
    @Max(value = 5, message = "Quantity cannot be larger than 5")
    private int quantity;
    
    @NotBlank(message = "Order addressId is a required field")
    private String addressId;
    
}
