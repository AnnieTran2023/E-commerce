package fi.haagahelia.ecommerce.web;

import java.util.List;
import java.util.Optional;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import fi.haagahelia.ecommerce.domain.Order;
import fi.haagahelia.ecommerce.domain.OrderRepository;
import fi.haagahelia.ecommerce.domain.Product;
import fi.haagahelia.ecommerce.domain.ProductRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

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
    public Order addOrder(@RequestBody List<ObjectId> productIds) {
        List<Product> products = productRepository.findAllById(productIds);
        double totalPrice = calculateTotalPrice(products);
        Order order = new Order(new Date().toString(), totalPrice, "Complete", products);
        Order savedOrder = orderRepository.save(order);

        // Update products with the new order reference
        for (Product product : products) {
            List<Order> orders = product.getOrders();
            orders.add(savedOrder);
            product.setOrders(orders);
            productRepository.save(product);
        }

        return savedOrder;
    }

    // Get products in an order
    @GetMapping("/{id}/products")
    public List<Product> getProductsInOrder(@PathVariable ObjectId id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
            return order.get().getProducts();
        } else {
            return null;
        }
    }

    // Calculate total price of the order
    private double calculateTotalPrice(List<Product> products) {
        return products.stream().mapToDouble(Product::getPrice).sum();
    }
}