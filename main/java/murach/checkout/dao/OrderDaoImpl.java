package murach.checkout.dao;

import murach.checkout.model.Order;
import murach.checkout.model.OrderItem;
import murach.checkout.util.DB;

import java.sql.*;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void insertOrder(Order o) throws Exception {
        String sql = "INSERT INTO orders(id,user_id,address_id,total_amount,status) VALUES(?,?,?,?,?)";
        try (Connection c = DB.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, o.getId());
            ps.setString(2, o.getUserId());
            ps.setLong(3, o.getAddressId());
            ps.setBigDecimal(4, o.getTotalAmount());
            ps.setString(5, o.getStatus());
            ps.executeUpdate();
        }
    }

    @Override
    public void insertItems(String orderId, List<OrderItem> items) throws Exception {
        String sql = "INSERT INTO order_items(order_id,product_id,product_name,unit_price,quantity) VALUES(?,?,?,?,?)";
        try (Connection c = DB.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            for (OrderItem it : items) {
                ps.setString(1, orderId);
                ps.setString(2, it.getProductId());
                ps.setString(3, it.getProductName());
                ps.setBigDecimal(4, it.getUnitPrice());
                ps.setInt(5, it.getQuantity());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    @Override
    public void updateStatus(String orderId, String status) throws Exception {
        try (Connection c = DB.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement("UPDATE orders SET status=? WHERE id=?")) {
            ps.setString(1, status);
            ps.setString(2, orderId);
            ps.executeUpdate();
        }
    }
}
