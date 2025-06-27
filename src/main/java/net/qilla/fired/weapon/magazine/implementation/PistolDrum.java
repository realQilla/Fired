package net.qilla.fired.weapon.magazine.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.WeaponClass;
import net.qilla.fired.weapon.magazine.MagazineType;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public final class PistolDrum extends MagazineImpl {

    public PistolDrum(@NotNull MagazineType<?> magazineType, int capacity) {
        super(magazineType, new Factory()
                .name(MiniMessage.miniMessage().deserialize("<white>Large Pistol Drum"))
                .itemStack(QStack.ofClean(Material.NETHERITE_INGOT, Material.HOPPER_MINECART, 1))
                .weaponClass(WeaponClass.PISTOL)
                .capacity(capacity)
                .reloadSpeed(250)
        );
    }
}
