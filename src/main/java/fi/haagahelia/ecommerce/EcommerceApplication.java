package fi.haagahelia.ecommerce;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import fi.haagahelia.ecommerce.domain.ProductRepository;
import fi.haagahelia.ecommerce.domain.User;
import fi.haagahelia.ecommerce.domain.UserRepository;
import fi.haagahelia.ecommerce.domain.Category;
import fi.haagahelia.ecommerce.domain.CategoryRepository;
import fi.haagahelia.ecommerce.domain.OrderRepository;
import fi.haagahelia.ecommerce.domain.Product;
import fi.haagahelia.ecommerce.domain.OrderProduct;
import fi.haagahelia.ecommerce.domain.OrderProductRepository;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Bean
	public CommandLineRunner ecommerceDemo(ProductRepository productRepository, CategoryRepository categoryRepository,
			OrderRepository orderRepository, UserRepository userRepository,
			OrderProductRepository orderProductRepository, PasswordEncoder passwordEncoder) {
		return (args) -> {
			// Clear existing data	

		};
	}

	private double calculateTotalPrice(List<OrderProduct> orderProducts) {
		return orderProducts.stream().mapToDouble(op -> op.getProduct().getPrice() * op.getQuantity()).sum();
	}
}