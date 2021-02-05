package viceCity.models.neighbourhood;

import viceCity.models.guns.BaseGun;
import viceCity.models.guns.Gun;
import viceCity.models.players.Player;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GangNeighbourhood implements Neighbourhood{
    private boolean actionHappened;
    public boolean fightHasHappened(){
        return actionHappened;
    }


    @Override
    public void action(Player mainPlayer, Collection<Player> civilPlayers) {
        while (!(mainPlayer.getGunRepository().getModels().isEmpty() || civilPlayers.isEmpty())){
            List<Gun> guns = mainPlayer.getGunRepository().getModels().stream().collect(Collectors.toList());
            Gun mainPlayerGun = guns.get(0);
            for (Player civilPlayer : civilPlayers) {
                while (civilPlayer.isAlive()) {
                    if (mainPlayerGun.canFire()) {
                        civilPlayer.takeLifePoints(mainPlayerGun.fire());
                        actionHappened = true;

                    } else {
                        guns.remove(0);
                        mainPlayerGun = guns.get(0);
                    }
                    if (!civilPlayer.isAlive()){
                        civilPlayers.remove(civilPlayer);
                    }
                }
            }


        }

        while (mainPlayer.isAlive() || civilPlayers.){

        }
    }
}
