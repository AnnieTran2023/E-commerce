package fi.haagahelia.ecommerce;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.haagahelia.ecommerce.domain.ProductRepository;
import fi.haagahelia.ecommerce.domain.Product;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Bean
	public CommandLineRunner bookDemo(ProductRepository repository) {
		return (args) -> {

			// if (repository.count() == 0) {

			// 	// Create products
			// 	Product iphone15 = new Product("iPhone 15", "The latest iPhone", 999.99);
			// 	Product samsungGalaxy = new Product("Samsung Galaxy", "The latest Samsung Galaxy", 899.99);
			// 	Product huaweiP40 = new Product("Huawei P40", "The latest Huawei P40", 799.99);

			// 	// Save products
			// 	repository.save(iphone15);
			// 	repository.save(samsungGalaxy);
			// 	repository.save(huaweiP40);

			// 	System.out.println("Sample products inserted into the database.");
			// } else {
			// 	System.out.println("Sample products already exist in the database.");
			// }
	}; 
}}