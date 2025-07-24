package net.qilla.fired.weapon.gun.implementation;

import net.kyori.adventure.text.Component;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.magazine.implementation.Magazine;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.visualstats.StatDisplay;
import net.qilla.qlibrary.util.QSound;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Gun {

    boolean attemptFire(@NotNull Player holder, @NotNull ItemStack gunItem);

    boolean fire(@NotNull Player holder, @NotNull Location loc, @NotNull ItemStack gunItem);

    void hitBlock(@NotNull Player shooter, @NotNull Block block, @NotNull Vector hitVec);

    void hitEntity(@NotNull Player shooter, @NotNull LivingEntity entity, @NotNull Vector hitVec);

    boolean attemptLoad(@NotNull Player holder, @NotNull ItemStack gunItem, @NotNull ItemStack magazineItem, @Nullable Magazine magazine);

    boolean load(@NotNull Player holder, @NotNull ItemStack gunItem, @NotNull ItemStack magazineItem, @NotNull Magazine magazine);

    @Nullable Magazine getMagazine();

    @Nullable ItemStack getMagazineItem();

    @NotNull String getUUID();

    @NotNull Rarity getRarity();

    @NotNull Component getName();

    @NotNull List<Component> buildLore();

    @NotNull ItemStack getItem();

    void updateItem(@NotNull ItemStack item);

    @NotNull GunType<?> getType();

    @NotNull BulletClass getBulletClass();

    float getDamage();

    int getFireCD();

    @NotNull StatDisplay buildStats();

    @NotNull QSound getFireSound();

    @NotNull QSound getLoadSound();

    @NotNull QSound getUnloadSound();
}