package murach.checkout.util;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public class FakeCatalog {
    // id -> [name, price]
    public static final Map<String, Item> ITEMS = new LinkedHashMap<>();
    static {
        ITEMS.put("b1", new Item("Lập trình Java cơ bản",  new BigDecimal("120000")));
        ITEMS.put("b2", new Item("Cấu trúc dữ liệu & GT", new BigDecimal("145000")));
        ITEMS.put("b3", new Item("Thiết kế Web với JSP",  new BigDecimal("99000")));
        ITEMS.put("b4", new Item("Cơ sở dữ liệu MySQL",   new BigDecimal("110000")));
    }
    public record Item(String name, BigDecimal price) {}
}
