package me.brunobelloni.api.utils;

public class StringUtil {


    /**
     * Check if two characters are equal ignoring the case
     *
     * @param c1 A char
     * @param c2 Another char
     * @return {@code true} if the argument represents an equivalent {@code String} ignoring case; {@code false}
     * otherwise
     */
    public static boolean equalsIgnoreCase(final char c1, final char c2) {
        return Character.toUpperCase(c1) == Character.toUpperCase(c2);
    }
}
