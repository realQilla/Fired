package net.qilla.fired.weapon.magazine.implementation;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.bullet.BulletType;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.fired.weapon.magazine.MagazineType;
import net.qilla.qlibrary.items.QStack;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class MachineMagazine extends MagazineImpl {

    public MachineMagazine(@NotNull MagazineType<?> magazineType, @NotNull Factory factory) {
        super(magazineType, factory);
    }

    public static final class Magazine250 extends MachineMagazine {
        public Magazine250(@NotNull MagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>M-XX Magazine Drum"))
                    .rarity(Rarity.RARE_III)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.HOPPER_MINECART, 1))
                    .magazineClass(MagazineClass.ASSAULT)
                    .bulletClass(BulletClass.ASSAULT)
                    .capacity(250)
                    .reloadSpeed(200)
            );
        }
    }

    public static final class Magazine1000 extends MachineMagazine {
        public Magazine1000(@NotNull MagazineType<?> magazineType) {
            super(magazineType, new Factory()
                    .name(MiniMessage.miniMessage().deserialize("<white>M-XX Magazine Drum"))
                    .rarity(Rarity.LEGENDARY)
                    .itemStack(QStack.ofClean(Material.IRON_INGOT, Material.HOPPER_MINECART, 1))
                    .magazineClass(MagazineClass.MACHINE_GUN)
                    .bulletClass(BulletClass.ASSAULT)
                    .capacity(1000)
                    .reloadSpeed(50)
                    .bullets(List.of(
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,
                            BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD,BulletType.A_SD
                            ))
            );
        }
    }
}
