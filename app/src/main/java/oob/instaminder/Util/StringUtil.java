package oob.instaminder.Util;

public class StringUtil {
    public static String limitIfGreaterThan(String stringToBeLimited, int charLimit) {
        return stringToBeLimited.length() > charLimit ? stringToBeLimited.substring(0, charLimit) + " ..." : stringToBeLimited;
    }
}
