package mate.academy.mybookshop.security;

import java.nio.charset.StandardCharsets;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

@Component
public class TokenUtil {
    public static final String SEPARATOR = ":";
    public static final String SECRET = "JavaIsTheCapitalOfGreatBritain";
    public static final int TOKEN_EXPIRATION_TIME = 30 * 60 * 1000; //30 minutes
    public static final int USERNAME_INDEX = 0;
    public static final int CREATED_TIME_INDEX = 1;
    public static final int SECRET_INDEX = 2;
    public static final int SEPARATED_TOKEN_LENGTH = 3;

    public String generateToken(String username) {
        String rawToken = username + SEPARATOR + System.currentTimeMillis() + SEPARATOR + SECRET;
        return toHexString(rawToken.getBytes(StandardCharsets.UTF_8));
    }

    public boolean isValidToken(String token) {
        try {
            String rawToken = new String(fromHexString(token), StandardCharsets.UTF_8);
            String[] parts = rawToken.split(SEPARATOR);
            if (parts.length == SEPARATED_TOKEN_LENGTH) {
                long creationTime = Long.parseLong(parts[CREATED_TIME_INDEX]);
                long currentTime = System.currentTimeMillis();
                if (currentTime - creationTime < TOKEN_EXPIRATION_TIME) {
                    return SECRET.equals(parts[SECRET_INDEX]);
                }
            }
        } catch (IllegalArgumentException e) {
             throw new BadCredentialsException("Invalid token");
        }
        return false;
    }

    public String extractUsername(String token) {
        if (!isValidToken(token)) {
            return null;
        }
        String rawToken = new String(fromHexString(token), StandardCharsets.UTF_8);
        return rawToken.split(SEPARATOR)[USERNAME_INDEX];
    }

    private String toHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    private byte[] fromHexString(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}
