package pl.comp.view;


import javafx.util.StringConverter;

import java.util.Objects;

public class MyConverter extends StringConverter<Number> {

    @Override
    public String toString(Number object) {
        if (object.toString() == "0") {

            return "";
        }
        return object.toString();
    }

    @Override
    public Number fromString(String string) {
        if (Objects.equals(string, "")) {
            return 0;
        }
        return Integer.valueOf(string);
    }
}
