package cvia.utilities;

/**
 * Created by andhieka on 8/11/15.
 */
public class StringUtilities {

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

}
