package murach.checkout.service;

import murach.checkout.model.Address;
import murach.checkout.model.Order;
import murach.checkout.model.OrderItem;
import java.util.List;

public interface OrderService {
    Order createOrderWithAddress(String userId, Address addr, List<OrderItem> items, String method) throws Exception;
}
