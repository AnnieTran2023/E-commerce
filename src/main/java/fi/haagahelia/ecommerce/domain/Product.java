package fi.haagahelia.ecommerce.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;

@Document(collection = "product")
public class Product {

    @Id
    private ObjectId id;
    private String name;
    private String description;
    private double price;
    private String categoryName;
    
    public Product() {
    }

    public Product(String name, String description, double price, String categoryName) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.categoryName = categoryName;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
}
