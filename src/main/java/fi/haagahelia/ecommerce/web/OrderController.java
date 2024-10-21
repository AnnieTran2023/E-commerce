package fi.haagahelia.ecommerce.web;

import java.util.List;
import java.util.Optional;
import java.util.Date;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import fi.haagahelia.ecommerce.domain.Order;
import fi.haagahelia.ecommerce.domain.OrderRepository;
import fi.haagahelia.ecommerce.domain.Product;
import fi.haagahelia.ecommerce.domain.ProductRepository;
import fi.haagahelia.ecommerce.domain.User;
import fi.haagahelia.ecommerce.domain.UserRepository;
import fi.haagahelia.ecommerce.domain.OrderProduct;
import fi.haagahelia.ecommerce.domain.OrderRequestDto;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // Get all orders
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get order by ID
    @GetMapping("/{id}")
    public Optional<Order> getOrderById(@PathVariable ObjectId id) {
        return orderRepository.findById(id);
    }

    // Create a new order
    @PostMapping("/add")
    public Order addOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));

        List<OrderProduct> orderProducts = orderRequestDto.getProducts().stream()
                .map(orderProductDto -> {
                    Product product = productRepository.findById(orderProductDto.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));
                    return new OrderProduct(product, orderProductDto.getQuantity());
                })
                .collect(Collectors.toList());

        double totalPrice = calculateTotalPrice(orderProducts);
        Order order = new Order(new Date().toString(), totalPrice, "Complete", orderProducts, user);
        Order savedOrder = orderRepository.save(order);

        // Update products with the new order reference
        for (OrderProduct orderProduct : orderProducts) {
            Product product = orderProduct.getProduct();
            List<OrderProduct> orderProductList = product.getOrderProducts();
            orderProductList.add(orderProduct);
            product.setOrderProducts(orderProductList);
            productRepository.save(product);
        }

        return savedOrder;
    }

    // Get products in an order
    @GetMapping("/{id}/products")
    public List<Product> getProductsInOrder(@PathVariable ObjectId id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get().getOrderProducts().stream()
                    .map(OrderProduct::getProduct)
                    .collect(Collectors.toList());
        } else {
            return null;
        }
    }

    // Calculate total price of the order
    private double calculateTotalPrice(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .mapToDouble(orderProduct -> orderProduct.getProduct().getPrice() * orderProduct.getQuantity())
                .sum();
    }
}