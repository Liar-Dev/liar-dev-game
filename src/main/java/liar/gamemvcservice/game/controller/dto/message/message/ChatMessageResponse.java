package liar.gamemvcservice.game.controller.dto.message.message;


import liar.gamemvcservice.game.controller.dto.message.ChatMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatMessageResponse<T> {

    private String userId;
    private String chatMessage;
    private LocalDateTime createdAt;
    private T body;

    public ChatMessageResponse(String userId, String chatMessage) {
        this.userId = userId;
        this.chatMessage = chatMessage;
        this.createdAt = LocalDateTime.now();
    }

    public ChatMessageResponse(String userId, String chatMessage, T body) {
        this.userId = userId;
        this.chatMessage = chatMessage;
        this.createdAt = LocalDateTime.now();
        this.body = body;
    }

    public static ChatMessageResponse of(ChatMessage msg) {
        return new ChatMessageResponse(msg.getUserId(), msg.getCharMessage());
    }

    public static <T> ChatMessageResponse of(ChatMessage msg, T body) {
        return new ChatMessageResponse(msg.getUserId(), msg.getCharMessage(), body);
    }
}
