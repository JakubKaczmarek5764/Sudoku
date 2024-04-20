package pl.comp.view;

import java.util.ListResourceBundle;

public class AuthorsBundle extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][]{
                {"Author1", "Jakub Kaczmarek"},
                {"Author2", "Piotr Marszałek"}
        };
    }
}
