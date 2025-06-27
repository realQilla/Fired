package net.qilla.fired.weapon.magazine.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.WeaponClass;
import net.qilla.fired.weapon.magazine.MagazineType;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public final class RifleMagazine extends MagazineImpl {

    public RifleMagazine(@NotNull MagazineType<?> magazineType, int capacity) {
        super(magazineType, new MagazineImpl.Factory()
                .name(MiniMessage.miniMessage().deserialize("<white>Standard Rifle Magazine"))
                .itemStack(QStack.ofClean(Material.NETHERITE_INGOT, Material.SNOUT_ARMOR_TRIM_SMITHING_TEMPLATE, 1))
                .weaponClass(WeaponClass.RIFLE)
                .capacity(capacity)
                .reloadSpeed(400)
        );
    }
}
