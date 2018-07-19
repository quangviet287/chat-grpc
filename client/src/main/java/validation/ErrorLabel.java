package validation;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;

public class ErrorLabel extends Label implements IValidationDisplay {

    public static final String DEFAULT_STYLE_CLASS = "error-label";

    public ErrorLabel() {
        this.getStyleClass().setAll(DEFAULT_STYLE_CLASS);
    }

    protected SimpleObjectProperty<ValidatorBase> validator = new SimpleObjectProperty<ValidatorBase>()
    {
        @Override
        protected void invalidated() {
            if(get() != null)
                ErrorLabel.this.visibleProperty().bind(get().hasErrors);
        }
    };


    @Override
    public ValidatorBase getValidator() {
        return this.validator.get();
    }

    @Override
    public void setValidator(ValidatorBase validator) {
        this.validator.set(validator);
    }

    protected SimpleStringProperty message = new SimpleStringProperty()
    {
        @Override
        protected void invalidated() {
            ErrorLabel.this.setText(message.get());
        }
    };

    public void setMessage(String message)
    {
        this.message.set(message);
    }

    public String getMessage()
    {
        return this.message.get();
    }

    public StringProperty messageProperty()
    {
        return this.message;
    }
}
