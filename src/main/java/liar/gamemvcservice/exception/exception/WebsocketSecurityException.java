package liar.gamemvcservice.exception.exception;

import liar.gamemvcservice.exception.type.ExceptionCode;
import liar.gamemvcservice.exception.type.ExceptionMessage;

public class WebsocketSecurityException extends CommonException {

    public WebsocketSecurityException() {
        super(ExceptionCode.BAD_REQUEST, ExceptionMessage.BAD_REQUEST);
    }
}
