package fi.haagahelia.ecommerce.web;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import fi.haagahelia.ecommerce.domain.Category;
import fi.haagahelia.ecommerce.domain.Product;
import fi.haagahelia.ecommerce.domain.ProductRepository;
import fi.haagahelia.ecommerce.domain.CategoryRepository;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    // Rest service to get all products
    // Rest service to get all products
    @RequestMapping(value = "/products", method = RequestMethod.GET)
    public @ResponseBody List<Product> productRest() {
        return (List<Product>) productRepository.findAll();
    }

    // Rest service to get product by name
    @RequestMapping(value = "/search/{name}", method = RequestMethod.GET)
    public @ResponseBody List<Product> findProductByName(@PathVariable("name") String name) {
        return productRepository.findByName(name);
    }

    // Rest service for adding a new product
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public @ResponseBody Product addProduct(@RequestBody Product newProduct) {
        return productRepository.save(newProduct);
    }

    // Rest service to delete product by id
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public @ResponseBody String deleteProduct(@PathVariable("id") ObjectId id) {
        productRepository.deleteById(id);
        return "Product deleted successfully " + id;
    }

    // Rest service to delete all products
    @RequestMapping(value = "/deleteAll", method = RequestMethod.GET)
    public @ResponseBody String deleteAllProducts() {
        productRepository.deleteAll();
        return "All products deleted successfully!";
    }

    @GetMapping("/category/{categoryName}")
    public @ResponseBody List<Product> findProductByCategory(@PathVariable("categoryName") String categoryName) {
        Category category = categoryRepository.findByName(categoryName);
        if (category != null) {
            return productRepository.findByCategory(category.getId());
        } else {
            return List.of();
        }
    }
}