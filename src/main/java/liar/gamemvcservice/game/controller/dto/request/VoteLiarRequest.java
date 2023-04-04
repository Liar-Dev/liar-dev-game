package liar.gamemvcservice.game.controller.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class VoteLiarRequest {

    @NotNull
    private String gameId;
    @NotNull
    private String userId;
    @NotNull
    private String liarId;

}
