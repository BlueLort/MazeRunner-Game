package playerState;

import entities.player.Player;

public interface PlayerState {
    void updateMove(Player player,double deltaTime);
    void pauseUnPause(Player player);
}
