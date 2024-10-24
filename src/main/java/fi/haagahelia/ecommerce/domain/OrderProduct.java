package fi.haagahelia.ecommerce.domain;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Id;

@Document(collection = "order_product")
public class OrderProduct {

    @Id
    private ObjectId id;

    @DBRef
    private User user;

    @DBRef
    @JsonBackReference
    private Product product;

    private int quantity;

    public OrderProduct() {
    }

    public OrderProduct(ObjectId id, User user, Product product, int quantity) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.quantity = quantity;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
}