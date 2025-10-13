package murach.checkout.dao;

import murach.checkout.model.Address;

public interface AddressDao {
    long insert(Address a) throws Exception;
}
