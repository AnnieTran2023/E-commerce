package fi.haagahelia.ecommerce.service;

import fi.haagahelia.ecommerce.domain.Order;
import fi.haagahelia.ecommerce.domain.OrderProduct;
import fi.haagahelia.ecommerce.domain.OrderProductRepository;
import fi.haagahelia.ecommerce.domain.OrderRepository;
import fi.haagahelia.ecommerce.domain.Product;
import fi.haagahelia.ecommerce.domain.ProductRepository;
import fi.haagahelia.ecommerce.domain.User;
import fi.haagahelia.ecommerce.domain.UserRepository;
import fi.haagahelia.ecommerce.dto.OrderDTO;
import fi.haagahelia.ecommerce.dto.OrderProductDTO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public OrderDTO createOrder(OrderDTO orderDTO) {
        // Get the authenticated user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with username: " + userDetails.getUsername()));

        List<OrderProduct> orderProducts = orderDTO.getOrderProducts().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList());

        Order order = new Order();
        order.setOrderProducts(orderProducts);
        order.setTotalPrice(calculateTotalPrice(orderProducts));
        order.setStatus(orderDTO.getStatus());
        order.setUser(user);
        order.setOrderDate(orderDTO.getOrderDate()); // Set the order date as String

        // Save the order
        orderRepository.save(order);

        // Save order products and update products with the new order products
        for (OrderProduct orderProduct : orderProducts) {
            orderProductRepository.save(orderProduct);
            Product product = orderProduct.getProduct();
            product.getOrderProducts().add(orderProduct);
            productRepository.save(product);
        }

        return convertToDTO(order);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getAllOrdersForUser() {
        // Get the authenticated user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with username: " + userDetails.getUsername()));

        return orderRepository.findByUserId(user.getId()).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderByIdForUser(String id) {
        // Get the authenticated user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with username: " + userDetails.getUsername()));

        Order order = orderRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Order not found for the current user with id: " + id);
        }

        return convertToDTO(order);
    }

    public void deleteOrderForUser(String id) {
        // Get the authenticated user
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with username: " + userDetails.getUsername()));

        Order order = orderRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException("Order not found for the current user with id: " + id);
        }

        orderRepository.delete(order);
    }

    private double calculateTotalPrice(List<OrderProduct> orderProducts) {
        return orderProducts.stream().mapToDouble(op -> op.getProduct().getPrice() * op.getQuantity()).sum();
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId().toString());
        dto.setOrderDate(order.getOrderDate()); // Set the order date as String
        dto.setOrderProducts(order.getOrderProducts().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()));
        dto.setTotalPrice(order.getTotalPrice());
        dto.setStatus(order.getStatus());
        dto.setUserId(order.getUser().getId().toString());
        return dto;
    }

    private OrderProductDTO convertToDTO(OrderProduct orderProduct) {
        OrderProductDTO dto = new OrderProductDTO();
        dto.setId(orderProduct.getId().toString());
        dto.setProductId(orderProduct.getProduct().getId().toString());
        dto.setProductName(orderProduct.getProduct().getName()); // Set the product name
        dto.setQuantity(orderProduct.getQuantity());
        return dto;
    }

    private OrderProduct convertToEntity(OrderProductDTO orderProductDTO) {
        Product product = productRepository.findById(new ObjectId(orderProductDTO.getProductId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + orderProductDTO.getProductId()));

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setQuantity(orderProductDTO.getQuantity());
        return orderProduct;
    }
}