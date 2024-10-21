package fi.haagahelia.ecommerce.web;

import fi.haagahelia.ecommerce.domain.Product;
import fi.haagahelia.ecommerce.domain.User;
import fi.haagahelia.ecommerce.domain.UserRepository;
import fi.haagahelia.ecommerce.domain.UserRegistrationDto;
import fi.haagahelia.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderService orderService;

    @PostMapping("/add")
    public User registerUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        // Check if the username already exists
        if (userRepository.findByUsername(userRegistrationDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // Create a new user
        User user = new User();
        user.setUsername(userRegistrationDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegistrationDto.getPassword()));
        user.setRole(userRegistrationDto.getRole());

        // Save the user to the database
        return userRepository.save(user);
    }

    @GetMapping("/products")
    public List<Product> getProductsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return orderService.getOrdersByUserId(user.getId()).stream()
                .flatMap(order -> order.getProducts().stream())
                .collect(Collectors.toList());
    }
}