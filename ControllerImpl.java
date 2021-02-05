package viceCity.core;

import viceCity.Main;
import viceCity.core.interfaces.Controller;
import viceCity.models.guns.BaseGun;
import viceCity.models.guns.Gun;
import viceCity.models.guns.Pistol;
import viceCity.models.guns.Rifle;
import viceCity.models.neighbourhood.GangNeighbourhood;
import viceCity.models.players.CivilPlayer;
import viceCity.models.players.MainPlayer;
import viceCity.models.players.Player;

import java.util.*;
import java.util.stream.Collectors;

import static viceCity.common.ConstantMessages.*;

public class ControllerImpl implements Controller {
    private static Deque<Gun> guns = new ArrayDeque<>();
    private static Map<String, Player> players = new HashMap<>();
    private Player mainPlayer;


    public ControllerImpl() {
        mainPlayer = new MainPlayer();
    }

    @Override
    public String addPlayer(String name) {
        Player civilPlayer = new CivilPlayer(name);
        players.put("name", civilPlayer);
        return String.format(PLAYER_ADDED, name);
    }

    @Override
    public String addGun(String type, String name) {
        if (type.equals("Pistol")) {
            BaseGun pistol = new Pistol(name);
            guns.offer(pistol);
            return String.format(GUN_ADDED, name, type);
        } else if (type.equals("Rifle")) {
            BaseGun rifle = new Rifle(name);
            guns.offer(rifle);
            return String.format(GUN_ADDED, name, type);
        }
        return GUN_TYPE_INVALID;
    }

    @Override
    public String addGunToPlayer(String name) {
        if (guns.isEmpty()) {
            return GUN_QUEUE_IS_EMPTY;
        }
        Gun gun = guns.poll();

        if (players.containsKey(name)) {
            Player player = players.get(name);
            player.getGunRepository().add(gun);
            return String.format(GUN_ADDED_TO_CIVIL_PLAYER, gun.getName(), name);
        } else if (name.equals("Vercetti")) {
            mainPlayer.getGunRepository().add(gun);
            return String.format(GUN_ADDED_TO_MAIN_PLAYER, gun.getName(), "Tommy Vercetti");
        }
        return CIVIL_PLAYER_DOES_NOT_EXIST;
    }

    @Override
    public String fight() {
        int mainPlayersPoints = mainPlayer.getLifePoints();
        List<Player> civilPlayers = players.values().stream().collect(Collectors.toList());
        GangNeighbourhood gangNeighbourhood = new GangNeighbourhood();
        gangNeighbourhood.action(mainPlayer, civilPlayers);
        int playersCount = civilPlayers.size();

        if (mainPlayer.isAlive() && playersCount == civilPlayers.size()
                && !gangNeighbourhood.fightHasHappened()) {
            return FIGHT_HOT_HAPPENED;
        }
        return FIGHT_HAPPENED;

    }
}


