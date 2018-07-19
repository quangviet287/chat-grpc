package validation;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextInputControl;

public class RequiredField extends ValidatorBase {

    public RequiredField(){}

    @Override
    public void eval() {
        if (srcControl.get() instanceof TextInputControl) {
            evalTextInputField();
        } else if (srcControl.get() instanceof ComboBox) {
            evalComboBox();
        }
    }

    private void evalComboBox() {
        ComboBox comboBox = (ComboBox) srcControl.get();
        if (comboBox.getValue() == null || comboBox.getValue().toString().trim().isEmpty())
            hasErrors.set(true);
        else
            hasErrors.set(false);

        onEval();
    }

    private void evalTextInputField() {
        TextInputControl textField = (TextInputControl) srcControl.get();
        if (textField.getText() == null || textField.getText().equals(""))
            hasErrors.set(true);
        else
            hasErrors.set(false);

        onEval();
    }
}
