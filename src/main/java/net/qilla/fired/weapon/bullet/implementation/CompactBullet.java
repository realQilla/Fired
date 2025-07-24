package net.qilla.fired.weapon.bullet.implementation;

import org.jetbrains.annotations.NotNull;

public abstract class CompactBullet extends BulletImpl {

    public CompactBullet(@NotNull String id, @NotNull Factory<?> factory) {
        super(id, factory);
    }
}
