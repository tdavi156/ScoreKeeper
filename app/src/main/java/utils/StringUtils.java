package utils;

/**
 * Utility class for various String operations.
 */
public final class StringUtils {

    private static final String EMPTY_STRING = "";
    private static final String ZERO = "0";

    private StringUtils() { }

    /**
     * Check if a String is null or empty.
     * @param s String to check
     * @return true if the input is null or empty, false otherwise
     */
    public static boolean nullOrEmpty(String s) {
        return null == s || EMPTY_STRING.equals(s);
    }

    /**
     * If s1 is null or empty and s2 is not then return s2.
     * If s2 is null or empty and s1 is not then return s1.
     * If s1 and s2 are both either null or empty then return empty string.
     * If s1 and s2 are both valid strings then add them together as numbers and return the result.
     * @param s1 First String to add
     * @param s2 Second String to add
     * @return The result of adding s1 and s2
     */
    public static String addTwoStringsAsIntegers(String s1, String s2) {
        if (nullOrEmpty(s1) && !nullOrEmpty(s2))
            return s2;
        if (nullOrEmpty(s2) && !nullOrEmpty(s1))
            return s1;
        if (nullOrEmpty(s1) && nullOrEmpty(s2))
            return EMPTY_STRING;
        int x1 = 0;
        int x2 = 0;
        try {
            x1 = Integer.parseInt(s1);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        try {
            x2 = Integer.parseInt(s2);
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return Integer.toString(x1 + x2);
    }

    /**
     * Add 1 to a String as an Integer.
     * If the input is not a parsable number then the original input will be returned.
     * @param s The String to add 1 to
     * @return The result of adding 1 to the input
     */
    public static String addOneToString(String s) {
        String retVal = s;
        try {
            retVal = Integer.toString(Integer.parseInt(s) + 1);
        }
        catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
        return retVal;
    }
}
