package net.qilla.fired.weapon.magazine.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.WeaponClass;
import net.qilla.fired.weapon.magazine.MagazineType;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public final class RifleDrum extends MagazineImpl {

    public RifleDrum(@NotNull MagazineType<?> magazineType, int capacity) {
        super(magazineType, new MagazineImpl.Factory()
                .name(MiniMessage.miniMessage().deserialize("<white>Large Rifle Drum"))
                .itemStack(QStack.ofClean(Material.NETHERITE_INGOT, Material.HOPPER_MINECART, 1))
                .weaponClass(WeaponClass.RIFLE)
                .capacity(capacity)
                .reloadSpeed(25)
        );
    }
}
