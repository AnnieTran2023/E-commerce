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
			//Create categories
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

		};
	}
}