package cvia.utilities;

import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * Created by andhieka on 8/11/15.
 */
public class StringUtilities {
    // Match 1 single non-ASCII pattern
    private static final Pattern BULLET_POINT_PATTERN = Pattern.compile("[^\\x00-\\x7F](.*?)");

    // Case Insensitive String contains method
    // Modified from http://stackoverflow.com/questions/86780/is-the-contains-method-in-java-lang-string-case-sensitive
    public static boolean containsIgnoreCase(final String input, final String keyword) {
        if (keyword.length() == 0) {
            return true;
        }
        final char firstLo = Character.toLowerCase(keyword.charAt(0));
        final char firstUp = Character.toUpperCase(keyword.charAt(0));

        for (int i = input.length() - keyword.length(); i >= 0; i--) {
            final char ch = input.charAt(i);
            if (ch != firstLo && ch != firstUp)
                continue;

            if (input.regionMatches(true, i, keyword, 0, keyword.length()))
                return true;
        }

        return false;
    }

    public static int numberOfWords(final String input) {
        return input.split("\\s+").length;
    }

    public static String removeRedundantSpaces(String input) {
        return input.replaceAll("\\s+", " ");
    }

    public static boolean beginsWithBullet(String line) {
        return BULLET_POINT_PATTERN.matcher(line).matches();
    }

}
