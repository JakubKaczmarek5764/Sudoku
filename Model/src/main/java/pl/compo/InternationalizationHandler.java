package pl.compo;

import java.util.Locale;
import java.util.ResourceBundle;

public class InternationalizationHandler {
    private static Locale userLocale = Locale.getDefault();
    private static ResourceBundle msgs = ResourceBundle.getBundle("messages", userLocale);

    public static String getMessage(String key) {
        return msgs.getString(key);
    }
}
