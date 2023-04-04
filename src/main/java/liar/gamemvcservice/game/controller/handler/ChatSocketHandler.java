package liar.gamemvcservice.game.controller.handler;

import jakarta.validation.Valid;
import liar.gamemvcservice.exception.exception.NotEqualUserIdException;
import liar.gamemvcservice.game.controller.dto.message.ChatMessage;
import liar.gamemvcservice.game.controller.dto.message.message.ChatMessageResponse;
import liar.gamemvcservice.game.domain.NextTurn;
import liar.gamemvcservice.game.service.GameFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ChatSocketHandler {

    private final GameFacadeService gameFacadeService;

    @MessageMapping("/game/pub/{gameId}")
    @SendTo("/game-service/game/sub/{gameId}")
    public ChatMessageResponse chatTopic(@Valid @RequestBody ChatMessage message,
                          @DestinationVariable String gameId,
                          StompHeaderAccessor headerAccessor) throws InterruptedException {

        isMatchUserIdAndRequestMessageUserId(headerAccessor, message);
        NextTurn nextTurn = gameFacadeService
                .setNextTurnWhenValidated(gameId, message.getCharMessage());

        return ChatMessageResponse.of(message, nextTurn);
    }

    private boolean isMatchUserIdAndRequestMessageUserId(SimpMessageHeaderAccessor headerAccessor, ChatMessage message) {
        String userId = headerAccessor.getFirstNativeHeader("userId");
        if (Objects.requireNonNull(userId).equals(message.getUserId())) {
            return true;
        }
        throw new NotEqualUserIdException();
    }
}
