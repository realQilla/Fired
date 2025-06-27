package net.qilla.fired;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.qilla.fired.command.FiredCommand;
import net.qilla.fired.event.WeaponEvents;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.magazine.*;
import net.qilla.fired.weapon.gun.implementation.AssaultRifleGun;
import net.qilla.fired.weapon.gun.GunRegistry;
import net.qilla.fired.weapon.gun.LiveGunRegistry;
import net.qilla.fired.weapon.bullet.BulletRegistry;
import net.qilla.fired.weapon.bullet.BulletType;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import net.qilla.fired.weapon.magazine.implementation.RifleMagazine;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Fired extends JavaPlugin {

    private LifecycleEventManager<Plugin> manager;
    private GunRegistry gunRegistry;
    private LiveGunRegistry liveGun;
    private MagazineRegistry magazine;
    private LiveMagazineRegistry liveMagazine;
    private BulletRegistry bulletRegistry;

    @Override
    public void onEnable() {
        this.manager = this.getLifecycleManager();;
        this.gunRegistry = GunRegistry.getInstance();
        this.liveGun = LiveGunRegistry.getInstance();
        this.magazine = MagazineRegistry.getInstance();
        this.liveMagazine = LiveMagazineRegistry.getInstance();
        this.bulletRegistry = BulletRegistry.getInstance();
        GunType<AssaultRifleGun> ak47 = GunType.rifle_assault_rifle;
        MagazineType<RifleMagazine> rifleMagazine = MagazineType.RIFLE_20;
        Bullet regular = BulletType.RIFLE_REGULAR;

        this.registerCommands();
        this.registerListeners();
    }

    private void registerCommands() {
        this.manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();

            new FiredCommand(this, commands);
        });
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new WeaponEvents(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public @NotNull GunRegistry getGunReg() {
        return this.gunRegistry;
    }

    public @NotNull LiveGunRegistry getLiveGunReg() {
        return this.liveGun;
    }

    public @NotNull MagazineRegistry getMagazineReg() {
        return this.magazine;
    }

    public @NotNull LiveMagazineRegistry getLiveMagazineReg() {
        return this.liveMagazine;
    }

    public @NotNull BulletRegistry getBulletReg() {
        return this.bulletRegistry;
    }

    public static @NotNull Fired getInstance() {
        return JavaPlugin.getPlugin(Fired.class);
    }
}