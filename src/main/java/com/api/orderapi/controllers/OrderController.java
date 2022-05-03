package com.api.orderapi.controllers;

import com.api.orderapi.models.Order;
import com.api.orderapi.models.OrderStatus;
import com.api.orderapi.models.dtos.OrderDTO;
import com.api.orderapi.services.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.api.orderapi.models.OrderStatus.PENDING;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping
    public ResponseEntity<Object> saveOrder(@RequestBody @Valid OrderDTO orderDTO) {
        var order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        order.setStatus(PENDING);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(order));
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders(){
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findAll());
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<Object> getOneOrder(@PathVariable(value = "orderNumber") Long orderNumber){
        Optional<Order> orderOptional = orderService.findByOrderNumber(orderNumber);
        if (!orderOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderOptional.get());
    }

    @GetMapping("/find/{status}")
    public ResponseEntity<Object> filtrarPedidoPorStatus(@PathVariable(value = "status") OrderStatus status){
        List<Order> orderOptional = orderService.findByOrderStatus(status);
        if (orderOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderOptional);
    }

    @DeleteMapping("/{orderNumber}")
    public ResponseEntity<Object> deleteOrder(@PathVariable(value = "orderNumber") Long orderNumber){
        Optional<Order> orderOptional = orderService.findByOrderNumber(orderNumber);
        if (!orderOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }
        var order = new Order();
        order.setStatus(orderOptional.get().getStatus());
        if (order.getStatus() == OrderStatus.DELIVERED) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict : You cannot delete a delivered order.");
        }
        if (order.getStatus() == OrderStatus.PAID) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict : You cannot delete a paid order.");
        }
        orderService.delete(orderOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Order deleted successfully.");
    }

    @PutMapping("/{orderNumber}")
    public ResponseEntity<Object> updateOrder(@PathVariable(value = "orderNumber") Long orderNumber,
                                                    @RequestBody @Valid OrderDTO orderDTO){
        Optional<Order> orderOptional = orderService.findByOrderNumber(orderNumber);
        if (!orderOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }
        var order = new Order();
        BeanUtils.copyProperties(orderDTO, order);
        order.setOrderNumber(orderOptional.get().getOrderNumber());
        order.setStatus(orderOptional.get().getStatus());
        if (order.getStatus() == OrderStatus.DELIVERED) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict : You cannot change a delivered order.");
        }
        if (order.getStatus() == OrderStatus.PAID) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict : You cannot change a paid order.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(orderService.save(order));
    }

    @PutMapping("/{orderNumber}/delivered")
    public ResponseEntity<Object> setDelivered(@PathVariable(value = "orderNumber") Long orderNumber){
        Optional<Order> orderOptional = orderService.findByOrderNumber(orderNumber);
        if (!orderOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }
        var order = new Order();
        order.setOrderNumber(orderOptional.get().getOrderNumber());
        order.setStatus(OrderStatus.DELIVERED);
        order.setAmount(orderOptional.get().getAmount());
        order.setDescriptionItem(orderOptional.get().getDescriptionItem());
        order.setValueItem(orderOptional.get().getValueItem());
        order.setQuantityItem(orderOptional.get().getQuantityItem());
        return ResponseEntity.status(HttpStatus.OK).body(orderService.save(order));
    }

    @PutMapping("/{orderNumber}/paid")
    public ResponseEntity<Object> setpaid(@PathVariable(value = "orderNumber") Long orderNumber){
        Optional<Order> orderOptional = orderService.findByOrderNumber(orderNumber);
        if (!orderOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.");
        }
        var order = new Order();
        order.setOrderNumber(orderOptional.get().getOrderNumber());
        order.setStatus(OrderStatus.PAID);
        order.setAmount(orderOptional.get().getAmount());
        order.setDescriptionItem(orderOptional.get().getDescriptionItem());
        order.setValueItem(orderOptional.get().getValueItem());
        order.setQuantityItem(orderOptional.get().getQuantityItem());
        return ResponseEntity.status(HttpStatus.OK).body(orderService.save(order));
    }

}
