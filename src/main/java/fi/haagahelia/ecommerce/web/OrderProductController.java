package fi.haagahelia.ecommerce.web;

import fi.haagahelia.ecommerce.dto.OrderProductDTO;
import fi.haagahelia.ecommerce.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class OrderProductController {

    @Autowired
    private OrderProductService orderProductService;

    @PostMapping
    public OrderProductDTO addToCart(@RequestBody OrderProductDTO orderProductDTO) {
        return orderProductService.addToCart(orderProductDTO);
    }

    @GetMapping
    public List<OrderProductDTO> getCartItems() {
        return orderProductService.getCartItems();
    }
}