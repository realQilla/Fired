package net.qilla.fired.weapon.magazine.implementation;

import net.kyori.adventure.text.Component;
import net.qilla.fired.weapon.WeaponClass;
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

    @Nullable Bullet unload();

    void updateItem(@NotNull ItemStack itemStack);

    @NotNull MagazineType<?> getMagazineType();

    @Nullable Bullet getNext();

    long getReloadSpeed();

    @NotNull WeaponClass getWeaponClass();

    int getCapacity();

    int getLoaded();

    int getQueued();

    boolean isFull();

    boolean hasQueued();

    boolean isEmpty();

    @NotNull ItemStack getItem();

    @NotNull Component getName();

    @NotNull List<Component> getLore();
}
