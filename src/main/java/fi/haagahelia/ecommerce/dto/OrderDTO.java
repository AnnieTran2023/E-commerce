package fi.haagahelia.ecommerce.dto;
import java.util.List;

public class OrderDTO {

    private String id;
    private String orderDate;
    private double totalPrice;
    private String status;
    private List < OrderProductDTO > orderProducts;
    private String userId;

    public OrderDTO() {
    }

    public OrderDTO(String id, String orderDate, double totalPrice, String status, List<OrderProductDTO> orderProducts,
            String userId) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderProducts = orderProducts;
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public List<OrderProductDTO> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProductDTO> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    


}
