package chat;

import com.example.grpc.chat.Chat;
import com.example.grpc.chat.ChatServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import validation.RequiredField;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatViewController implements Initializable {

    @FXML private ListView listView;

    @FXML public RequiredField requiredFieldUsername = new RequiredField();

    @FXML private RequiredField requiredFieldMessage = new RequiredField();

    @FXML private TextField message = new TextField();

    @FXML private TextField name = new TextField();

    @FXML private Label error = new Label();

    private ObservableList<String> chatMessages = FXCollections.observableArrayList();

    private ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080).usePlaintext().build();
    private ChatServiceGrpc.ChatServiceStub chatService = ChatServiceGrpc.newStub(channel);
    private StreamObserver<Chat.ChatMessage> chat = chatService.chat(new StreamObserver<Chat.ChatMessageFromServer>() {
        @Override
        public void onNext(Chat.ChatMessageFromServer value) {
            Platform.runLater(() -> {
                chatMessages.add(value.getMessage().getFrom() + ": " + value.getMessage().getMessage());
                listView.scrollTo(chatMessages.size());
            });
        }

        @Override
        public void onError(Throwable t) {
            t.printStackTrace();
            System.out.println("Disconnected");
        }

        @Override
        public void onCompleted() {
            System.out.println("Disconnected");
        }


    });

    public void sent(ActionEvent actionEvent) {
//        if (name.getText().equals("") || message.getText().equals("")) {
//            error.setText("Name or Message must not empty!");
//            return;
//        }
        requiredFieldUsername.eval();
        requiredFieldMessage.eval();
        chat.onNext(Chat.ChatMessage.newBuilder().setFrom(name.getText()).setMessage(message.getText()).build());
        message.setText("");
//        error.setVisible(false);

    }

    public void initialize(URL location, ResourceBundle resources) {
        listView.setItems(chatMessages);
    }
}
