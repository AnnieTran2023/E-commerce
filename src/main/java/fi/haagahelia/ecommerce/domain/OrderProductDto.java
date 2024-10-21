package fi.haagahelia.ecommerce.domain;

import org.bson.types.ObjectId;

public class OrderProductDto {
    private ObjectId productId;
    private int quantity;

    // Getters and setters
    public ObjectId getProductId() {
        return productId;
    }

    public void setProductId(ObjectId productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}