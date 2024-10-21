package fi.haagahelia.ecommerce.web;

import fi.haagahelia.ecommerce.domain.Order;
import fi.haagahelia.ecommerce.domain.User;
import fi.haagahelia.ecommerce.domain.UserRepository;
import fi.haagahelia.ecommerce.service.OrderService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user")
    public List<Order> getOrdersByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return orderService.getOrdersByUserId(user.getId());
    }
}