package murach.checkout.dao;
import murach.checkout.model.Order;
import murach.checkout.model.OrderItem;

public interface OrderDao {
    void insertOrder(Order o) throws Exception;
    void insertItems(String orderId, java.util.List<OrderItem> items) throws Exception;
    void updateStatus(String orderId, String status) throws Exception;
}
