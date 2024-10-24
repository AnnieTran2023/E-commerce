package fi.haagahelia.ecommerce.service;

import fi.haagahelia.ecommerce.domain.OrderProduct;
import fi.haagahelia.ecommerce.domain.OrderProductRepository;
import fi.haagahelia.ecommerce.domain.Product;
import fi.haagahelia.ecommerce.domain.ProductRepository;
import fi.haagahelia.ecommerce.domain.User;
import fi.haagahelia.ecommerce.domain.UserRepository;
import fi.haagahelia.ecommerce.dto.OrderProductDTO;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderProductService {

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public OrderProductDTO addToCart(OrderProductDTO orderProductDTO) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with username: " + userDetails.getUsername()));

        Product product = productRepository.findById(new ObjectId(orderProductDTO.getProductId()))
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Product not found with id: " + orderProductDTO.getProductId()));

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setQuantity(orderProductDTO.getQuantity());
        orderProductRepository.save(orderProduct);

        return convertToDTO(orderProduct);
    }

    public List<OrderProductDTO> getCartItems() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User not found with username: " + userDetails.getUsername()));

        List<OrderProduct> orderProducts = orderProductRepository.findByUserId(user.getId());
        return orderProducts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private OrderProductDTO convertToDTO(OrderProduct orderProduct) {
        OrderProductDTO dto = new OrderProductDTO();
        dto.setId(orderProduct.getId().toString());
        dto.setProductId(orderProduct.getProduct().getId().toString());
        dto.setProductName(orderProduct.getProduct().getName());
        dto.setQuantity(orderProduct.getQuantity());
        return dto;
    }
}