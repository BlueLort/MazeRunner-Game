package playerState;

import entities.player.Player;

public class Playing implements PlayerState{
    @Override
    public void updateMove(Player player, double deltaTime) {
        player.update(deltaTime);
    }
    public void pauseUnPause(Player player)
    {
        player.setPlayerState(new Pause());
    }
}
