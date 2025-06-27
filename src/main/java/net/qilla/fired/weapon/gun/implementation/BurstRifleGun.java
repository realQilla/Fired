package net.qilla.fired.weapon.gun.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.WeaponClass;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public final class BurstRifleGun extends BurstGun {

    public BurstRifleGun(@NotNull GunType<?> gunType) {
        super(gunType, new BurstGun.Factory()
                .weaponClass(WeaponClass.RIFLE)
                .name(MiniMessage.miniMessage().deserialize("<red>Burst Rifle"))
                .lore(List.of(MiniMessage.miniMessage().deserialize("<!italic><dark_gray>A high powered burst rifle")))
                .itemStack(QStack.ofClean(Material.NETHERITE_HOE))
                .damage(5.0f)
                .accuracy(2.0f)
                .fireCooldown(500)
                .burstCount(3)
                .burstInterval(80)
        );
    }
}