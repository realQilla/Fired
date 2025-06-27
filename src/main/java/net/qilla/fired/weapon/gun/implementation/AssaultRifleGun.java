package net.qilla.fired.weapon.gun.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.WeaponClass;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public final class AssaultRifleGun extends AutomaticGun {

    public AssaultRifleGun(@NotNull GunType<?> gunType) {
        super(gunType, new AutomaticGun.Factory<>()
                .weaponClass(WeaponClass.RIFLE)
                .name(MiniMessage.miniMessage().deserialize("<red>Assault Rifle"))
                .lore(List.of(MiniMessage.miniMessage().deserialize("<!italic><dark_gray>A high powered fully automatic rifle")))
                .itemStack(QStack.ofClean(Material.NETHERITE_HOE))
                .damage(4.0f)
                .accuracy(2.75f)
                .fireCooldown(125)
        );
    }
}