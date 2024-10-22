package fi.haagahelia.ecommerce;

import java.util.Arrays;
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
import fi.haagahelia.ecommerce.domain.Order;
import fi.haagahelia.ecommerce.domain.OrderRepository;
import fi.haagahelia.ecommerce.domain.Product;
import fi.haagahelia.ecommerce.domain.OrderProduct;
import fi.haagahelia.ecommerce.domain.OrderProductRepository;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	private double calculateTotalPrice(List<OrderProduct> orderProducts) {
		return orderProducts.stream().mapToDouble(op -> op.getProduct().getPrice() * op.getQuantity()).sum();
	}

	@Bean
	public CommandLineRunner bookDemo(ProductRepository productRepository, CategoryRepository categoryRepository,
			OrderRepository orderRepository, UserRepository userRepository,
			OrderProductRepository orderProductRepository, PasswordEncoder passwordEncoder) {
		return (args) -> {
			// // Create users
			// User user = new User("user1", passwordEncoder.encode("password"), "USER");
			// userRepository.save(user);

			// User admin = new User("admin1", passwordEncoder.encode("password"), "ADMIN");
			// userRepository.save(admin);

			// // Create categories
			// Category phone = new Category("Phone", "mobile phones");
			// Category tablet = new Category("Tablet", "all sorts of tablets");
			// categoryRepository.save(phone);
			// categoryRepository.save(tablet);

			// // Create products
			// Product iphone15 = new Product("iPhone 15", "The latest iPhone",
			// 		"https://www.apple.com/newsroom/images/product/iphone/standard/Apple_announce-iphone13pro_09142021_big.jpg.og.jpg",
			// 		999.99, 10, phone);
			// Product samsungGalaxy = new Product("Samsung Galaxy", "The latest Samsung Galaxy",
			// 		"https://www.samsung.com/us/smartphones/galaxy-s21-5g/_jcr_content/og-v2.jpg", 899.99, 15, phone);
			// Product iPad = new Product("iPad", "The latest iPad", "https://store.store", 499.99, 20, tablet);
			// // Save products
			// productRepository.save(iphone15);
			// productRepository.save(samsungGalaxy);
			// productRepository.save(iPad);

			// System.out.println("Sample products inserted into the database.");

			// // Create order products
			// OrderProduct orderProduct1 = new OrderProduct(iphone15, 2);
			// OrderProduct orderProduct2 = new OrderProduct(samsungGalaxy, 1);
			// OrderProduct orderProduct3 = new OrderProduct(iPad, 3);

			// // Save order products
			// orderProductRepository.save(orderProduct1);
			// orderProductRepository.save(orderProduct2);
			// orderProductRepository.save(orderProduct3);

			// // Create orders
			// Order order1 = new Order("2023-10-01", 0, "Complete", Arrays.asList(orderProduct1, orderProduct2), user);
			// order1.setTotalPrice(calculateTotalPrice(order1.getOrderProducts()));
			// Order order2 = new Order("2023-10-02", 0, "Complete", Arrays.asList(orderProduct3), admin);
			// order2.setTotalPrice(calculateTotalPrice(order2.getOrderProducts()));
			// orderRepository.save(order1);
			// orderRepository.save(order2);

			// // Update products with the new order references
			// iphone15.setOrderProducts(Arrays.asList(orderProduct1));
			// samsungGalaxy.setOrderProducts(Arrays.asList(orderProduct2));
			// iPad.setOrderProducts(Arrays.asList(orderProduct3));
			// productRepository.save(iphone15);
			// productRepository.save(samsungGalaxy);
			// productRepository.save(iPad);

			// System.out.println("Sample orders inserted into the database.");
		};
	}
}