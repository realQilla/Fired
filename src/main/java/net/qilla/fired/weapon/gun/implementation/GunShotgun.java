package net.qilla.fired.weapon.gun.implementation;

import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.visualstats.Stat;
import net.qilla.fired.weapon.visualstats.StatDisplay;
import org.jetbrains.annotations.NotNull;

public abstract class GunShotgun extends GunImpl {

    public GunShotgun(@NotNull GunType<?> gunType, @NotNull Factory<?> factory) {
        super(gunType, factory);
    }
}