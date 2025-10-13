package murach.checkout.model;

import java.math.BigDecimal;
import java.util.List;

public class Order {
    private String id;
    private String userId;
    private long addressId;
    private BigDecimal totalAmount;
    private String status; // CREATED | PENDING_PAYMENT | PAID | CANCELLED
    private List<OrderItem> items;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public long getAddressId() { return addressId; }
    public void setAddressId(long addressId) { this.addressId = addressId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
