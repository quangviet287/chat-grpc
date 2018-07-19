package chat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Objects;

public class ChatClient extends Application {
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chat client view");
        Pane myPane = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("ChatView.fxml")));
        Scene myScene = new Scene(myPane);
        primaryStage.setScene(myScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
