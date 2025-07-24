package net.qilla.fired.weapon.magazine.implementation;

import net.kyori.adventure.text.Component;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.magazine.MagazineType;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Magazine {

    @NotNull String getUUID();

    boolean attemptLoad(@NotNull Player player, @NotNull ItemStack magItem, @NotNull ItemStack bulletItem);

    void load(@NotNull Player player, @NotNull ItemStack magItem);

    @Nullable Bullet pullBullet();

    void updateItem(@NotNull ItemStack itemStack);

    @NotNull MagazineType<?> getMagazineType();

    @Nullable Bullet viewNextBullet();

    long getReloadSpeed();

    @NotNull BulletClass getBulletClass();

    int getCapacity();

    int getLoadedBulletsSize();

    int getQueuedBulletsSize();

    boolean isMagazineFull();

    boolean hasQueuedBullets();

    boolean isMagazineEmpty();

    @NotNull ItemStack getItem();

    @NotNull Rarity getRarity();

    @NotNull Component getName();

    @NotNull List<Component> getLore();
}
