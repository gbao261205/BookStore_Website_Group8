package murach.checkout.util;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

public class Ids {
    private static final SecureRandom rnd = new SecureRandom();

    public static String oid() { return UUID.randomUUID().toString(); }

    public static String token() {
        byte[] b = new byte[24];
        rnd.nextBytes(b);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(b);
    }
}
