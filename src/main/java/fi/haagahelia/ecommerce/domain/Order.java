package fi.haagahelia.ecommerce.domain;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import jakarta.persistence.Id;

@Document(collection = "order")
public class Order {

    @Id
    private ObjectId id;
    private String orderDate;
    private double totalPrice;
    private String status;

    @DBRef
    private List<OrderProduct> orderProducts;

    @DBRef
    private User user;

    // Default constructor
    public Order() {
    }

    // Constructor with parameters
    public Order(String orderDate, double totalPrice, String status, List<OrderProduct> orderProducts, User user) {
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderProducts = orderProducts;
        this.user = user;
    }

    // Getters and setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}