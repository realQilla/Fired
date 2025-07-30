package net.qilla.fired.weapon.gun.implementation.magazine;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.bullet.BulletRegistry;
import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.fired.weapon.magazine.implementation.Magazine;
import net.qilla.fired.weapon.magazine.implementation.MagazineStatic;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class MagazineLogicStatic extends MagazineLogic {

    private static final BulletRegistry BULLET_REGISTRY = BulletRegistry.getInstance();

    private final MagazineStatic magazine;

    public MagazineLogicStatic(@NotNull MagazineStatic magazine) {
        this.magazine = magazine;
    }

    @Override
    public boolean loadIntoGun(@NotNull Player holder, @NotNull Gun gun, @NotNull ItemStack heldItem, @NotNull ItemStack loadItem) {
        return this.magazine.loadToQueue(holder, heldItem, loadItem);
    }

    @Override
    public @NotNull List<Component> buildLoreForGun() {
        return List.of(
                MiniMessage.miniMessage().deserialize("<!italic><white><blue>☄</blue> Magazine: <green><bold>BUILT-IN</green>"),
                this.magazine.buildAmmoStatus()
        );
    }
//lore.add(MiniMessage.miniMessage().deserialize("<!italic><white><blue>☄</blue> Magazine: " + magazineLore));
    @Override
    public Magazine getMagazine() {
        return this.magazine;
    }
}
