package murach.checkout.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import murach.checkout.util.CartUtil;
import java.io.IOException;

@WebFilter({"/checkout", "/checkout_form"})
public class CheckoutGuardFilter implements Filter {
    @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        if (CartUtil.isEmpty(req.getSession())) {
            resp.sendRedirect(req.getContextPath() + "/cart?seed=true");
            return;
        }
        chain.doFilter(request, response);
    }
}
