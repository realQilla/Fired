package net.qilla.fired.weapon.bullet.implementation;

import net.kyori.adventure.text.Component;
import net.qilla.fired.weapon.bullet.ActiveBullet;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.fired.weapon.visualstats.StatHolder;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Bullet {
    void fire(@NotNull Player shooter, @NotNull Gun gun, @NotNull Location originLoc);

    void hitEntity(@NotNull Player shooter, @NotNull Gun gun, @NotNull ActiveBullet activeBullet, @NotNull LivingEntity entity, @NotNull Vector hitVec);

    void hitBlock(@NotNull Player shooter, @NotNull Gun gun, @NotNull Block block, @NotNull Vector hitVec);

    @NotNull String getID();

    @NotNull BulletClass getBulletClass();

    double getRange();

    double getBulletSpread();

    float getDamage();

    float getBleed();

    float getScale();

    @NotNull ItemStack getItem();

    @NotNull Component getName();

    @NotNull StatHolder buildStats();

    @NotNull List<Component> buildLore();
}
