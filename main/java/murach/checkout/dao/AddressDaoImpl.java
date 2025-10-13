package murach.checkout.dao;
import murach.checkout.model.Address;
import murach.checkout.util.DB;
import java.sql.*;

public class AddressDaoImpl implements AddressDao {
    @Override
    public long insert(Address a) throws Exception {
        String sql = "INSERT INTO addresses(user_id,nation,province,district,village,detail) VALUES(?,?,?,?,?,?)";
        try (Connection c = DB.getDataSource().getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getUserId());
            ps.setString(2, a.getNation());
            ps.setString(3, a.getProvince());
            ps.setString(4, a.getDistrict());
            ps.setString(5, a.getVillage());
            ps.setString(6, a.getDetail());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getLong(1);
            }
        }
        throw new SQLException("Cannot insert address");
    }
}
