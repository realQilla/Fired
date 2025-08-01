package net.qilla.fired.weapon.bullet;

import com.google.common.base.Preconditions;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import net.qilla.fired.weapon.gun.implementation.Gun;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.*;

public class ActiveBullet {

    private final List<Integer> ignoreBlocks;
    private final List<UUID> ignoreEntities;
    private double range;
    private float damage;
    private float bleed;

    public ActiveBullet(@NotNull Player shooter, @NotNull Gun gun, @NotNull Bullet bullet) {
        this.ignoreBlocks = new ArrayList<>();
        this.ignoreEntities = new ArrayList<>();
        this.range = bullet.getRange();
        this.damage = bullet.getDamage() * gun.getDamageMod();
        this.bleed = bullet.getBleed();

        this.ignoreEntities.add(shooter.getUniqueId());
    }

    public void impact(@NotNull Entity entity) {
        Preconditions.checkNotNull(entity, "Entity cannot be null.");

        this.ignoreEntities.add(entity.getUniqueId());
        this.range *= 0.5;
        this.damage *= 0.5f;
        this.bleed *= 0.5f;
    }

    public void impact(@NotNull Block block) {
        Preconditions.checkNotNull(block, "Block cannot be null.");

        this.ignoreBlocks.add(block.hashCode());
        this.range *= 0.5;
        this.damage *= 0.5f;
        this.bleed *= 0.5f;
    }

    public void fireDistance(double distance) {
        this.range -= distance;
    }

    public @NotNull List<Integer> ignoreBlocks() {
        return Collections.unmodifiableList(this.ignoreBlocks);
    }

    public @NotNull List<UUID> ignoreEntities() {
        return Collections.unmodifiableList(this.ignoreEntities);
    }

    public double range() {
        return this.range;
    }

    public float damage() {
        return this.damage;
    }

    public float bleed() {
        return this.bleed;
    }
}
