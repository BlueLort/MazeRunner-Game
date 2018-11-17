package playerState;

import entities.ENUMS.PlayerDirection;
import entities.player.Player;

public class Pause implements PlayerState {
    @Override
    public void updateMove(Player player, double deltaTime) {
        if(player instanceof Player)
            ((Player)player).setDirection(PlayerDirection.NONE);
    }
    public void pauseUnPause(Player player)
    {
        player.setPlayerState(new Playing());
    }
}
