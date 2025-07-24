package net.qilla.fired.weapon.gun.implementation.assault;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.gun.implementation.GunBurst;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public final class AssaultFenrir6 extends GunBurst {

    public AssaultFenrir6(@NotNull GunType<?> gunType) {
        super(gunType, new GunBurst.Factory()
                .bulletClass(BulletClass.ASSAULT)
                .name(MiniMessage.miniMessage().deserialize("<red>Fenrir 6"))
                .description(List.of(
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>High powered burst rifle designed for"),
                        MiniMessage.miniMessage().deserialize("<!italic><dark_gray>accurate shots in quick repetition.")
                ))
                .rarity(Rarity.RARE_II)
                .itemStack(QStack.ofClean(Material.IRON_HOE))
                .damage(5.0f)
                .fireCooldown(500)
                .burstCount(3)
                .burstInterval(80)
        );
    }
}