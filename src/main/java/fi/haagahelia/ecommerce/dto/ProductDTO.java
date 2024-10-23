package fi.haagahelia.ecommerce.dto;

import java.util.List;

public class ProductDTO {

    private String id;
    private String name;
    private String description;
    private String imageUrl;
    private double price;
    private int quantity;
    private CategoryDTO category;
    private List <OrderProductDTO> orderProducts;

    public ProductDTO() {
    }

    public ProductDTO(String name, String description, String imageUrl, double price, int quantity,
            CategoryDTO category, List<OrderProductDTO> orderProducts) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        this.orderProducts = orderProducts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public List<OrderProductDTO> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProductDTO> orderProducts) {
        this.orderProducts = orderProducts;
    }

    

}
