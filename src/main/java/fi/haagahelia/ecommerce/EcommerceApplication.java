package fi.haagahelia.ecommerce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.ecommerce.domain.ProductRepository;
import fi.haagahelia.ecommerce.domain.Category;
import fi.haagahelia.ecommerce.domain.CategoryRepository;
import fi.haagahelia.ecommerce.domain.Product;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Bean
	public CommandLineRunner bookDemo(ProductRepository repository, CategoryRepository categoryRepository) {
		return (args) -> {
			// Create categories
			Category phone = new Category("Phone");
			Category tablet = new Category("Tablet");
			categoryRepository.save(phone);
			categoryRepository.save(tablet);

			// Create products
			Product iphone15 = new Product("iPhone 15", "The latest iPhone", 999.99, "Phone");
			Product samsungGalaxy = new Product("Samsung Galaxy", "The latest Samsung Galaxy", 899.99, "Phone");
			Product iPad = new Product("iPad", "The latest iPad", 799.99, "Tablet");
			// Save products
			repository.save(iphone15);
			repository.save(samsungGalaxy);
			repository.save(iPad);

			System.out.println("Sample products inserted into the database.");

		};
	}
}