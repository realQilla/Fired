package net.qilla.fired.weapon.gun.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.WeaponClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class MagnumGun extends PistolGun {

    public MagnumGun(@NotNull GunType<?> gunType) {
        super(gunType, new Factory<>()
                .weaponClass(WeaponClass.PISTOL)
                .name(MiniMessage.miniMessage().deserialize("<gold>Magnum Pistol"))
                .lore(List.of(
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>Sometimes simply referred to as the"),
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>fabled \"Desert Eagle\".")
                ))
                .itemStack(QStack.ofClean(Material.IRON_HOE))
                .damage(6.5f)
                .accuracy(5.5f)
                .fireCooldown(333)
        );
    }
}
