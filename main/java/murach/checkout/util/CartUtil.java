package murach.checkout.util;

import jakarta.servlet.http.HttpSession;
import murach.checkout.model.OrderItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartUtil {
    public static final String CART_KEY = "CART_ITEMS";

    @SuppressWarnings("unchecked")
    public static List<OrderItem> getCart(HttpSession session) {
        Object o = session.getAttribute(CART_KEY);
        if (o == null) {
            List<OrderItem> cart = new ArrayList<>();
            session.setAttribute(CART_KEY, cart);
            return cart;
        }
        return (List<OrderItem>) o;
    }

    public static void add(HttpSession session, String pid, String name, BigDecimal price, int qty) {
        List<OrderItem> cart = getCart(session);
        for (OrderItem it : cart) {
            if (it.getProductId().equals(pid)) {
                it.setQuantity(it.getQuantity() + qty);
                return;
            }
        }
        OrderItem it = new OrderItem();
        it.setProductId(pid);
        it.setProductName(name);
        it.setUnitPrice(price);
        it.setQuantity(qty);
        cart.add(it);
    }

    public static void remove(HttpSession session, String pid) {
        getCart(session).removeIf(it -> it.getProductId().equals(pid));
    }

    public static void clear(HttpSession session) {
        getCart(session).clear();
    }

    public static java.math.BigDecimal total(HttpSession session) {
        return getCart(session).stream()
                .map(it -> it.getUnitPrice().multiply(new BigDecimal(it.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public static boolean isEmpty(HttpSession session) {
        return getCart(session).isEmpty();
    }
}
