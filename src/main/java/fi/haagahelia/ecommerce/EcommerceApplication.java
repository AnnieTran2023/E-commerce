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

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
		}
	
		private double calculateTotalPrice(List<Product> products) {
			return products.stream().mapToDouble(Product::getPrice).sum();
		}
	
	@Bean
	public CommandLineRunner bookDemo(ProductRepository repository, CategoryRepository categoryRepository, OrderRepository orderRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return (args) -> {
			//Create user
			User user = new User("user1", passwordEncoder.encode("password"), "USER");
			userRepository.save(user);

			User admin = new User("admin1", passwordEncoder.encode("password"), "ADMIN");
			userRepository.save(admin);

			// Create categories
			Category phone = new Category("Phone", "mobile phones");
			Category tablet = new Category("Tablet", "all sorts of tablets");
			categoryRepository.save(phone);
			categoryRepository.save(tablet);

			// Create products
			Product iphone15 = new Product("iPhone 15", "The latest iPhone", "https://www.apple.com/newsroom/images/product/iphone/standard/Apple_announce-iphone13pro_09142021_big.jpg.og.jpg", 999.99, phone);
			Product samsungGalaxy = new Product("Samsung Galaxy", "The latest Samsung Galaxy", "https://www.samsung.com/us/smartphones/galaxy-s21-5g/_jcr_content/og-v2.jpg", 899.99, phone);
			Product iPad = new Product("iPad", "The latest iPad", "https://store.store", 499.99, tablet);
			// Save products
			repository.save(iphone15);
			repository.save(samsungGalaxy);
			repository.save(iPad);

			System.out.println("Sample products inserted into the database.");

			// Create orders
            Order order1 = new Order("2023-10-01", 0, "Complete", Arrays.asList(iphone15, samsungGalaxy));
            order1.setTotalPrice(calculateTotalPrice(order1.getProducts()));
            Order order2 = new Order("2023-10-02", 0, "Complete", Arrays.asList(iPad));
            order2.setTotalPrice(calculateTotalPrice(order2.getProducts()));
            orderRepository.save(order1);
            orderRepository.save(order2);

            // Update products with the new order references
            iphone15.setOrders(Arrays.asList(order1));
            samsungGalaxy.setOrders(Arrays.asList(order1));
            iPad.setOrders(Arrays.asList(order2));
            repository.save(iphone15);
            repository.save(samsungGalaxy);
            repository.save(iPad);

            System.out.println("Sample orders inserted into the database.");

		};
	}
}