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

	@Bean
	public CommandLineRunner ecommerceDemo(ProductRepository productRepository, CategoryRepository categoryRepository,
			OrderRepository orderRepository, UserRepository userRepository,
			OrderProductRepository orderProductRepository, PasswordEncoder passwordEncoder) {
		return (args) -> {
			// Clear existing data
			productRepository.deleteAll();
			categoryRepository.deleteAll();
			orderRepository.deleteAll();
			userRepository.deleteAll();
			orderProductRepository.deleteAll();

			// Create users
			User user = new User("user1", passwordEncoder.encode("password"), "USER");
			userRepository.save(user);

			User admin = new User("admin1", passwordEncoder.encode("password"), "ADMIN");
			userRepository.save(admin);

			// Create categories
			Category electronics = new Category("Electronics", "Electronic items");
			Category books = new Category("Books", "Books and literature");
			Category clothing = new Category("Clothing", "Apparel and accessories");
			categoryRepository.saveAll(Arrays.asList(electronics, books, clothing));

			// Create products
			Product iphone15 = new Product("iPhone 15", "The latest iPhone",
					"https://www.apple.com/newsroom/images/product/iphone/standard/Apple_announce-iphone13pro_09142021_big.jpg.og.jpg",
					999.99, 10, electronics);
			Product samsungGalaxy = new Product("Samsung Galaxy", "The latest Samsung Galaxy",
					"https://www.samsung.com/us/smartphones/galaxy-s21-5g/_jcr_content/og-v2.jpg", 899.99, 15,
					electronics);
			Product iPad = new Product("iPad", "The latest iPad", "https://store.store", 499.99, 20, electronics);
			Product book1 = new Product("Book Title 1", "Interesting book 1", "https://example.com/book1.jpg", 19.99,
					100, books);
			Product book2 = new Product("Book Title 2", "Interesting book 2", "https://example.com/book2.jpg", 29.99,
					50, books);
			Product tshirt = new Product("T-Shirt", "Comfortable cotton t-shirt", "https://example.com/tshirt.jpg",
					15.99, 200, clothing);
			Product jeans = new Product("Jeans", "Stylish denim jeans", "https://example.com/jeans.jpg", 49.99, 100,
					clothing);
			// Save products
			productRepository.saveAll(Arrays.asList(iphone15, samsungGalaxy, iPad, book1, book2, tshirt, jeans));

			System.out.println("Sample products inserted into the database.");

			// Create order products
			OrderProduct orderProduct1 = new OrderProduct(iphone15, 2);
			OrderProduct orderProduct2 = new OrderProduct(samsungGalaxy, 1);
			OrderProduct orderProduct3 = new OrderProduct(iPad, 3);
			OrderProduct orderProduct4 = new OrderProduct(book1, 5);
			OrderProduct orderProduct5 = new OrderProduct(tshirt, 10);

			// Save order products
			orderProductRepository
					.saveAll(Arrays.asList(orderProduct1, orderProduct2, orderProduct3, orderProduct4, orderProduct5));

			// Create orders
			Order order1 = new Order("2023-10-01", 0, "Complete", Arrays.asList(orderProduct1, orderProduct2), user);
			order1.setTotalPrice(calculateTotalPrice(order1.getOrderProducts()));
			Order order2 = new Order("2023-10-02", 0, "Complete",
					Arrays.asList(orderProduct3, orderProduct4, orderProduct5), admin);
			order2.setTotalPrice(calculateTotalPrice(order2.getOrderProducts()));
			orderRepository.saveAll(Arrays.asList(order1, order2));

			System.out.println("Sample orders inserted into the database.");
		};
	}

	private double calculateTotalPrice(List<OrderProduct> orderProducts) {
		return orderProducts.stream().mapToDouble(op -> op.getProduct().getPrice() * op.getQuantity()).sum();
	}
}