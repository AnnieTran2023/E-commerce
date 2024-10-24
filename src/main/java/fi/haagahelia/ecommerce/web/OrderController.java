package fi.haagahelia.ecommerce.web;

import fi.haagahelia.ecommerce.dto.OrderDTO;
import fi.haagahelia.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public OrderDTO createOrder(@RequestBody OrderDTO orderDTO) {
        return orderService.createOrder(orderDTO);
    }

    @GetMapping
    public List<OrderDTO> getAllOrdersForUser() {
        return orderService.getAllOrdersForUser();
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderByIdForUser(@PathVariable String id) {
        return orderService.getOrderByIdForUser(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrderForUser(@PathVariable String id) {
        orderService.deleteOrderForUser(id);
    }
}