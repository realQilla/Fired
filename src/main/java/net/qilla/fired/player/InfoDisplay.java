package net.qilla.fired.player;

import net.qilla.fired.player.data.PlayerData;
import net.qilla.fired.weapon.gun.implementation.Gun;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;
import java.util.Queue;

public final class InfoDisplay {

    private final PlayerData playerData;
    private final Queue<DisplayStat> leftStat = new LinkedList<>();
    private final Queue<DisplayStat> midStat = new LinkedList<>();
    private final Queue<DisplayStat> rightStat = new LinkedList<>();
    private Gun heldGun;

    public InfoDisplay(@NotNull PlayerData playerData) {
        this.playerData = playerData;
    }

    public void tickActionBar() {
        if(heldGun != null) {

        }
    }

    public void updateWeapon(@Nullable Gun gun) {
        this.heldGun = gun;
    }
}
