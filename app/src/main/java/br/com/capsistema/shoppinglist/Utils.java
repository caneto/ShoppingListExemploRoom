package br.com.capsistema.shoppinglist;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static String getCurrentDate() {
        return new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss",
                Locale.getDefault())
                .format(new Date());
    }
}
