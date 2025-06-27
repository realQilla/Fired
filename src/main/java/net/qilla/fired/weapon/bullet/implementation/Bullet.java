package net.qilla.fired.weapon.bullet.implementation;

import net.kyori.adventure.text.Component;
import net.qilla.fired.weapon.WeaponClass;
import net.qilla.fired.weapon.gun.implementation.Gun;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Bullet {
    void fire(@NotNull Player shooter, @NotNull Location loc, @NotNull Vector dir, @NotNull Gun gun);

    void hitEntity(@NotNull Gun gun, @NotNull Player shooter, @NotNull LivingEntity entity, @NotNull Location loc);

    void hitBlock(@NotNull Gun gun, @NotNull Player shooter, @NotNull Block block, @NotNull Location loc);

    @NotNull String getID();

    @NotNull WeaponClass getWeaponClass();

    int getRange();

    float getDamage();

    float getBleed();

    float getScale();

    @NotNull ItemStack getItem();

    @NotNull Component getName();

    @NotNull List<Component> getStats();

    @NotNull List<Component> getLore();
}
