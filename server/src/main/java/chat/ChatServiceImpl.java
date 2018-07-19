package chat;

import com.example.grpc.chat.Chat;
import com.example.grpc.chat.ChatServiceGrpc;
import com.google.protobuf.Timestamp;
import io.grpc.stub.StreamObserver;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServiceImpl extends ChatServiceGrpc.ChatServiceImplBase {

    private static Set<StreamObserver<Chat.ChatMessageFromServer>> observers = ConcurrentHashMap.newKeySet();

    @Override
    public StreamObserver<Chat.ChatMessage> chat(StreamObserver<Chat.ChatMessageFromServer> responseObserver) {
        observers.add(responseObserver);

        return new StreamObserver<Chat.ChatMessage>() {
            @Override
            public void onNext(Chat.ChatMessage chatMessage) {
                System.out.println(chatMessage);
                Chat.ChatMessageFromServer message = Chat.ChatMessageFromServer.newBuilder().setMessage(chatMessage)
                        .setTimestamp(Timestamp.newBuilder().setSeconds(System.currentTimeMillis()/1000)).build();

                for (StreamObserver<Chat.ChatMessageFromServer> observer : observers) {
                    observer.onNext(message);
                }
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onCompleted() {
                observers.remove(responseObserver);
            }
        };
    }
}
