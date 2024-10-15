package fi.haagahelia.ecommerce.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import fi.haagahelia.ecommerce.domain.Product;
import fi.haagahelia.ecommerce.domain.ProductRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

   @RequestMapping(value = "/products", method = RequestMethod.GET)
    public @ResponseBody List<Product> productRest() {
        return (List<Product>) productRepository.findAll();
    }

}
