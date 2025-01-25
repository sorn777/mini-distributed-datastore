package com.maksumic.datastore;

public final class BytesUtil {
    private BytesUtil() {
        // Non-instantiable class
    }

    public static int bytesToInt(byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4 && i < bytes.length; i++) {
            result = (result << 8) | (bytes[i] & 0xFF);
        }
        return result;
    }
}
