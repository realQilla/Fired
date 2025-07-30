package net.qilla.fired.weapon.gun.implementation.magazine;

import net.kyori.adventure.text.Component;
import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.fired.weapon.magazine.implementation.Magazine;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class MagazineLogic {

    private Gun gun;

    public abstract boolean loadIntoGun(@NotNull Player holder, @NotNull Gun gun, @NotNull ItemStack heldItem, @NotNull ItemStack loadItem);

    public abstract @NotNull List<Component> buildLoreForGun();

    public abstract @Nullable Magazine getMagazine();

    public @Nullable Gun getGun() {
        return gun;
    }
}
