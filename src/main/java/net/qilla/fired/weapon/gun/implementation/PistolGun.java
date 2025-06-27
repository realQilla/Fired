package net.qilla.fired.weapon.gun.implementation;

import net.qilla.fired.weapon.gun.GunType;
import org.jetbrains.annotations.NotNull;

public abstract class PistolGun extends GunImpl {

    PistolGun(@NotNull GunType<?> gunType, @NotNull Factory<?> factory) {
        super(gunType, factory);
    }
}