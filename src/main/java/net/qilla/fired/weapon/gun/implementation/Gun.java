package net.qilla.fired.weapon.gun.implementation;

import net.kyori.adventure.text.Component;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.magazine.implementation.Magazine;
import net.qilla.fired.weapon.visualstats.StatHolder;
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

    boolean preFire(@NotNull Player holder, @NotNull ItemStack gunItem);

    boolean fire(@NotNull Player holder, @NotNull ItemStack gunItem);

    void hitBlock(@NotNull Player shooter, @NotNull Block block, @NotNull Vector hitVec);

    void hitEntity(@NotNull Player shooter, @NotNull LivingEntity entity, @NotNull Vector hitVec);

    @Nullable Magazine getMagazine();

    boolean loadIntoGun(@NotNull Player holder, @NotNull ItemStack heldItem, @NotNull ItemStack loadItem);

    @NotNull String getUUID();

    @NotNull Rarity getRarity();

    @NotNull Component getName();

    @NotNull List<Component> buildLore();

    @NotNull ItemStack getItem();

    void updateItem(@NotNull ItemStack item);

    @NotNull MagazineClass getMagazineClass();

    float getDamageMod();

    double getAccuracyMod();

    int getFireCD();

    @NotNull StatHolder buildWeaponStats();

    @NotNull QSound getFireSound();
}