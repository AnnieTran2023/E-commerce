package fi.haagahelia.ecommerce.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.haagahelia.ecommerce.domain.CategoryRepository;
import fi.haagahelia.ecommerce.domain.Product;
import fi.haagahelia.ecommerce.domain.Category;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // Rest service to get all categories
    @RequestMapping(value="/categories",method=RequestMethod.GET)

    public @ResponseBody List<Category> categoryRest() {
        return (List<Category>) categoryRepository.findAll();
    }

    // Rest service to delete all categories
    @RequestMapping(value = "/deleteAllCategories", method = RequestMethod.GET)
    public @ResponseBody String deleteAllProducts() {
        categoryRepository.deleteAll();
        return "All categories deleted successfully!";
    }

    // Rest service to add a new category
    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public @ResponseBody Category addCategory(@RequestBody Category newCategory) {
        return categoryRepository.save(newCategory);
    }

}
