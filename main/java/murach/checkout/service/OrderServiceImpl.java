package murach.checkout.service;

import murach.checkout.dao.AddressDao;
import murach.checkout.dao.AddressDaoImpl;
import murach.checkout.dao.OrderDao;
import murach.checkout.dao.OrderDaoImpl;
import murach.checkout.model.Address;
import murach.checkout.model.Order;
import murach.checkout.model.OrderItem;
import murach.checkout.util.Ids;

import java.math.BigDecimal;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final AddressDao addressDao = new AddressDaoImpl();
    private final OrderDao orderDao = new OrderDaoImpl();

    @Override
    public Order createOrderWithAddress(String userId, Address addr, List<OrderItem> items, String method) throws Exception {
        long addressId = addressDao.insert(addr);
        BigDecimal total = items.stream()
                .map(i -> i.getUnitPrice().multiply(new BigDecimal(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order o = new Order();
        o.setId(Ids.oid());
        o.setUserId(userId);
        o.setAddressId(addressId);
        o.setTotalAmount(total);
        o.setStatus("BANKING".equals(method) ? "PENDING_PAYMENT" : "CREATED");

        orderDao.insertOrder(o);
        orderDao.insertItems(o.getId(), items);
        return o;
    }
}
