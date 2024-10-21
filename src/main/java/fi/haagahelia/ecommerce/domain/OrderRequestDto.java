package fi.haagahelia.ecommerce.domain;

import java.util.List;

public class OrderRequestDto {
    private List<OrderProductDto> products;

    // Getters and setters
    public List<OrderProductDto> getProducts() {
        return products;
    }

    public void setProducts(List<OrderProductDto> products) {
        this.products = products;
    }
}