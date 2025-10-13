package murach.checkout.service;

import murach.checkout.model.Order;
import murach.checkout.model.Payment;

public interface PaymentService {
    Payment createPaymentAndMaybeSendEmail(Order order, String method, String userEmail, String baseUrl) throws Exception;
    void markPaid(String token) throws Exception;
}
