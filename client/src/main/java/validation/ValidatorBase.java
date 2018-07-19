package validation;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Parent;

public abstract class ValidatorBase extends Parent {

    public static final String DEFAULT_ERROR_STYLE_CLASS = "error";

    protected SimpleObjectProperty<Node> srcControl = new SimpleObjectProperty<>();

    protected ReadOnlyBooleanWrapper hasErrors = new ReadOnlyBooleanWrapper(false);

    protected SimpleStringProperty src = new SimpleStringProperty()
    {
        @Override
        protected void invalidated() {
            updateSrcControl();
        }
    };

    protected SimpleStringProperty errorStyleClass = new SimpleStringProperty(DEFAULT_ERROR_STYLE_CLASS);


    ValidatorBase() {
        parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {
                parentChange();
            }
        });
    }

    private void parentChange(){
        updateSrcControl();
    }

    private void updateSrcControl(){
        Parent parent = getParent();
        if (parent != null) {
            Node control = parent.lookup(getSrc());
            srcControl.set(control);
        }
    }

    public abstract void eval();

    protected void onEval()
    {
        Node control = getSrcControl();
        if (hasErrors.get()) {
            if (control.getStyleClass().indexOf(errorStyleClass) == -1)
                control.getStyleClass().add(errorStyleClass.get());
        } else
        {
            control.getStyleClass().remove(errorStyleClass.get());
        }
    }

    public void setSrcControl(Node srcControl)
    {
        this.srcControl.set(srcControl);
    }

    public Node getSrcControl()
    {
        return this.srcControl.get();
    }

    public ObjectProperty srcControlProperty()
    {
        return this.srcControl;
    }

    public boolean getHasErrors()
    {
        return hasErrors.get();
    }

    public ReadOnlyBooleanProperty hasErrorsProperty(){
        return hasErrors.getReadOnlyProperty();
    }

    public void setSrc(String src)
    {
        this.src.set(src);
    }

    public String getSrc()
    {
        return this.src.get();
    }

    public StringProperty srcProperty()
    {
        return this.src;
    }

    public void setErrorStyleClass(String styleClass)
    {
        this.errorStyleClass.set(styleClass);
    }

    public String getErrorStyleClass()
    {
        return this.errorStyleClass.get();
    }

    public StringProperty errorStyleClassProperty()
    {
        return this.errorStyleClass;
    }

}
