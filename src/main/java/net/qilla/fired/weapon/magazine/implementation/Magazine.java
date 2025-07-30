package net.qilla.fired.weapon.magazine.implementation;

import net.kyori.adventure.text.Component;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Magazine {

    boolean loadToQueue(@NotNull Player holder, @NotNull ItemStack heldItem, @NotNull ItemStack loadItem);

     void loadIntoMagazine(@NotNull Player holder, @NotNull ItemStack heldItem);

    @Nullable Bullet pullNextBullet();

    @Nullable Bullet pullNextQueuedBullet();

    @NotNull String getUUID();

    @Nullable Bullet peekNextBullet();

    long getReloadSpeed();

    @NotNull MagazineClass getMagazineClass();

    @NotNull BulletClass getBulletClass();

    int getMagazineCapacity();

    int getMagazineAmount();

    int getQueuedBulletAmount();

    boolean isMagazineFull();

    boolean isBulletQueueEmpty();

    boolean isMagazineEmpty();

    Component buildAmmoStatus();
}
