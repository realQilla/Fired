package net.qilla.fired;

import io.papermc.paper.command.brigadier.Commands;
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import net.qilla.fired.command.FindSoundCommand;
import net.qilla.fired.command.FiredCommand;
import net.qilla.fired.event.WeaponEvents;
import net.qilla.fired.player.PacketListener;
import net.qilla.fired.player.data.EntityDataRegistry;
import net.qilla.fired.player.data.SessionDataRegistry;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.magazine.*;
import net.qilla.fired.weapon.gun.GunRegistry;
import net.qilla.fired.weapon.gun.LiveGunRegistry;
import net.qilla.fired.weapon.bullet.BulletRegistry;
import net.qilla.fired.weapon.bullet.BulletType;
import net.qilla.qlibrary.menu.QStaticMenu;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class Fired extends JavaPlugin {

    private LifecycleEventManager<Plugin> manager;
    private SessionDataRegistry sessionDataReg;
    private EntityDataRegistry entityDataReg;
    private GunRegistry gunReg;
    private LiveGunRegistry liveGunReg;
    private MagazineRegistry magazineReg;
    private LiveMagazineRegistry liveMagazineReg;
    private BulletRegistry bulletReg;

    @Override
    public void onEnable() {
        this.manager = this.getLifecycleManager();;
        this.sessionDataReg = SessionDataRegistry.getInstance();
        this.entityDataReg = EntityDataRegistry.getInstance();
        this.gunReg = GunRegistry.getInstance();
        this.liveGunReg = LiveGunRegistry.getInstance();
        this.magazineReg = MagazineRegistry.getInstance();
        this.liveMagazineReg = LiveMagazineRegistry.getInstance();
        this.bulletReg = BulletRegistry.getInstance();
        var gun = GunType.MACHINE_MINIGUN;
        var magazine = DynamicMagazineType.ASSAULT_20;
        var bullet = BulletType.A_SD;

        this.registerCommands();
        this.registerListeners();
    }

    private void registerCommands() {
        this.manager.registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            final Commands commands = event.registrar();

            new FiredCommand(this, commands);
            new FindSoundCommand(this, commands);
        });
    }

    private void registerListeners() {
        this.getServer().getPluginManager().registerEvents(new WeaponEvents(this), this);
        this.getServer().getPluginManager().registerEvents(new QStaticMenu.Events(), this);
        this.getServer().getPluginManager().registerEvents(new PacketListener(sessionDataReg), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public @NotNull EntityDataRegistry getEntityDataReg() {
        return this.entityDataReg;
    }

    public @NotNull SessionDataRegistry getSessionDataReg() {
        return this.sessionDataReg;
    }

    public @NotNull GunRegistry getGunReg() {
        return this.gunReg;
    }

    public @NotNull LiveGunRegistry getLiveGunReg() {
        return this.liveGunReg;
    }

    public @NotNull MagazineRegistry getMagazineReg() {
        return this.magazineReg;
    }

    public @NotNull LiveMagazineRegistry getLiveMagazineReg() {
        return this.liveMagazineReg;
    }

    public @NotNull BulletRegistry getBulletReg() {
        return this.bulletReg;
    }

    public static @NotNull Fired getInstance() {
        return JavaPlugin.getPlugin(Fired.class);
    }
}