package liar.gamemvcservice.game.controller.interceptor;

import liar.gamemvcservice.common.auth.token.tokenprovider.TokenProviderPolicy;
import liar.gamemvcservice.exception.exception.WebsocketSecurityException;
import liar.gamemvcservice.game.service.GameFacadeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebsocketSecurityInterceptor implements ChannelInterceptor {

    private final TokenProviderPolicy tokenProviderPolicy;
    private final GameFacadeService gameFacadeService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(headerAccessor.getCommand())) {
            isValidateWaitRoomIdAndJoinMember(headerAccessor);
        }
        return message;
    }

    /**
     * 참여 조건: accessToken, refreshToken, gameId, userId 존재
     */
    private void isValidateWaitRoomIdAndJoinMember(StompHeaderAccessor headerAccessor) {
        String accessToken = headerAccessor.getFirstNativeHeader("Authorization");
        String refreshToken = headerAccessor.getFirstNativeHeader("RefreshToken");
        String gameId = headerAccessor.getFirstNativeHeader("GameId");
        String userId = headerAccessor.getFirstNativeHeader("UserId");

        if (accessToken == null || refreshToken == null || gameId == null || userId == null)
            throw new WebsocketSecurityException();

        validateUserAccessor(validateTokenAccessor(accessToken, refreshToken), userId);

        String destination = headerAccessor.getDestination();
        if (destination == null) throw new WebsocketSecurityException();

        isValidateGameIdAndUserId(gameId, userId);
    }

    private String validateTokenAccessor(String accessToken, String refreshToken) {
        try {
            String userIdFromAccess = tokenProviderPolicy.getUserIdFromToken(tokenProviderPolicy.removeType(accessToken));
            String userIdFromRefresh = tokenProviderPolicy.getUserIdFromToken(refreshToken);

            if (!userIdFromAccess.equals(userIdFromRefresh)) throw new WebsocketSecurityException();

            return userIdFromAccess;
        } catch (Exception e) {
            throw new WebsocketSecurityException();
        }
    }

    private void validateUserAccessor(String parseUserId, String headerUserId) {
        if (!parseUserId.equals(headerUserId)) throw new WebsocketSecurityException();
    }

    private boolean isValidateGameIdAndUserId(String gameId, String userId) {

        if(gameId != null && userId != null) {
            if (gameFacadeService.findJoinPlayer(gameId, userId) != null) {
                return true;
            }
        }

        throw new WebsocketSecurityException();
    }
}
