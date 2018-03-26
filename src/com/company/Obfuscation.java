package com.company;

class Obfuscation {
    private static byte ascii2hex(char h) {
        return (byte) (h >= 'a' ? (h - 97) + 10 : h - 48);
    }

    private static void scramble(byte[] buffer, int len) {
        for (int i = 0; i < len / 2; i++) {
            byte c = buffer[i];
            buffer[i] = (byte) (buffer[i] & 240);
            buffer[i] = (byte) ((buffer[i] & 240) | (buffer[(len - i) - 1] & 15));
            buffer[(len - i) - 1] = (byte) ((c & 15) | (buffer[(len - i) - 1] & 240));
        }
    }

    private static char getBits(byte[] temp, int from) {
        int i = from / 8;
        return (char) (((((temp[i] << 8) | (temp[i + 1] & 255)) << (from - (i * 8))) & 65280) >> 10);
    }

    private static char deoffsetVal(char v) {
        if (v == '\u0000') {
            return '\u0000';
        }
        char c = (char) (v + 47);
        if (c <= '9') {
            return c;
        }
        c = (char) (c + 6);
        if (c > 'Z') {
            return (char) (c + 6);
        }
        return c;
    }

    static String deobfuscate(String from) {
        int i;
        char[] to = new char[from.length()];
        int len = (from.length() + 1) / 2;
        byte[] temp = new byte[(len + 1)];
        for (i = 0; i < len; i++) {
            temp[i] = (byte) ((ascii2hex(from.charAt(i * 2)) << 4) + ascii2hex(from.charAt((i * 2) + 1)));
        }
        temp[len] = (byte) 0;
        scramble(temp, len);
        int j = 0;
        i = 0;
        while (j < len * 8) {
            to[i] = getBits(temp, j);
            i++;
            j += 6;
        }
        to[i] = '\u0000';
        len = i;
        for (i = 0; i < len; i++) {
            to[i] = deoffsetVal(to[i]);
        }
        return new String(to).trim();
    }
}