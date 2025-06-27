package net.qilla.fired.weapon.gun.implementation;

import net.kyori.adventure.text.Component;
import net.qilla.fired.weapon.WeaponClass;
import net.qilla.fired.weapon.magazine.implementation.Magazine;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.qlibrary.util.particle.QParticle;
import net.qilla.qlibrary.util.sound.QSound;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Gun {

    boolean attemptFire(@NotNull Player holder, @NotNull ItemStack gunItem);

    boolean fire(@NotNull Player holder, @NotNull Location loc, @NotNull ItemStack gunItem);

    void hitBlock(@NotNull Player shooter, @NotNull Block block, @NotNull Location loc);

    void hitEntity(@NotNull Player shooter, @NotNull LivingEntity entity, @NotNull Location loc);

    boolean attemptReload(@NotNull Player holder, @Nullable Magazine magazine, @NotNull ItemStack magazineItem);

    boolean reload(@NotNull Player holder, @NotNull Magazine magazine, @NotNull ItemStack magItem);

    @Nullable Magazine getMagazine();

    @Nullable ItemStack getMagazineItem();

    @NotNull String getUUID();

    @NotNull Component getName();

    @NotNull List<Component> getLore();

    @NotNull ItemStack getItem();

    void updateItem(@NotNull ItemStack item);

    @NotNull GunType<?> getType();

    @NotNull WeaponClass getWeaponClass();

    float getDamage();

    float getAccuracy();

    int getFireCD();

    @NotNull List<Component> getStats();

    @NotNull QParticle getFireParticle();

    @NotNull QSound getFireSound();

    @NotNull QSound getLoadSound();

    @NotNull QSound getUnloadSound();
}