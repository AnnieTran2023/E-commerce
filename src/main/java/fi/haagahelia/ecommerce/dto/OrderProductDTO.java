package fi.haagahelia.ecommerce.dto;

public class OrderProductDTO {

    private String id;
    private String productId;
    private String productName;
    private int quantity;

    public OrderProductDTO() {
    }

    public OrderProductDTO(String id, String productId, String productName, int quantity) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
