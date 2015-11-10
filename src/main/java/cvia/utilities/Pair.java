package cvia.utilities;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Created by Michael Limantara on 11/11/2015.
 */
public class Pair {
    private TextField textField;
    private ComboBox comboBox;

    public Pair(TextField textField, ComboBox comboBox) {
        this.textField = textField;
        this.comboBox = comboBox;
    }

    public TextField getTextField() {
        return textField;
    }

    public void setTextField(TextField textField) {
        this.textField = textField;
    }

    public ComboBox getComboBox() {
        return comboBox;
    }

    public void setComboBox(ComboBox comboBox) {
        this.comboBox = comboBox;
    }
}