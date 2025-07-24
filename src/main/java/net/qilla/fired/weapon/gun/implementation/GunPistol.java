package net.qilla.fired.weapon.gun.implementation;

import net.qilla.fired.weapon.gun.GunType;
import org.jetbrains.annotations.NotNull;

public abstract class GunPistol extends GunImpl {

    public GunPistol(@NotNull GunType<?> gunType, @NotNull Factory<?> factory) {
        super(gunType, factory);
    }
}