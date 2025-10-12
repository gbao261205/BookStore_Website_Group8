package murach.business;

import java.io.Serializable;
import java.util.ArrayList;

public class Cart implements Serializable {
    private final ArrayList<LineItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public ArrayList<LineItem> getItems() {
        return items;
    }

    public int getCount() {
        return items.size();
    }

    /** Thêm sản phẩm:
     *  - Nếu đã có trong giỏ: CỘNG thêm đúng bằng quantity của item truyền vào
     *  - Nếu chưa có: thêm mới với quantity hiện có trong item
     */
    public void addItem(LineItem item) {
        String code = item.getProduct().getCode();
        int quantity = item.getQuantity();
        for (int i = 0; i < items.size(); i++) {
            LineItem lineItem = items.get(i);
            if (lineItem.getProduct().getCode().equals(code)) {
                lineItem.setQuantity(lineItem.getQuantity() + quantity);
                return;
            }
        }
        items.add(item);
    }

    /** Giảm đúng 1; nếu về 0 thì xoá dòng. */
    public void decreItem(LineItem item) {
        String code = item.getProduct().getCode();
        for (int i = 0; i < items.size(); i++) {
            LineItem lineItem = items.get(i);
            if (lineItem.getProduct().getCode().equals(code)) {
                int currentQty = lineItem.getQuantity();
                if (currentQty > 1) {
                    lineItem.setQuantity(currentQty - 1);
                } else {
                    items.remove(i);
                }
                return;
            }
        }
    }

    /** ĐẶT số lượng tuyệt đối theo mã; <=0 thì xoá. (KHÔNG đổi thứ tự phần tử) */
    public void setQuantity(String code, int quantity) {
        for (int i = 0; i < items.size(); i++) {
            LineItem li = items.get(i);
            if (li.getProduct().getCode().equals(code)) {
                if (quantity <= 0) {
                    items.remove(i);
                } else {
                    li.setQuantity(quantity);
                }
                return;
            }
        }
        // Trường hợp đặt số lượng cho sản phẩm chưa có từ trang cart (hiếm khi xảy ra) → bỏ qua.
    }

    /** Xoá dòng theo item (dùng code). */
    public void removeItem(LineItem item) {
        String code = item.getProduct().getCode();
        for (int i = 0; i < items.size(); i++) {
            LineItem lineItem = items.get(i);
            if (lineItem.getProduct().getCode().equals(code)) {
                items.remove(i);
                return;
            }
        }
    }
}
