package net.qilla.fired.weapon.gun.implementation;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import io.papermc.paper.datacomponent.item.UseCooldown;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.Fired;
import net.qilla.fired.misc.NKey;
import net.qilla.fired.weapon.Rarity;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import net.qilla.fired.weapon.gun.implementation.magazine.MagazineLogic;
import net.qilla.fired.weapon.gun.implementation.magazine.MagazineLogicDynamic;
import net.qilla.fired.weapon.gun.implementation.magazine.MagazineLogicStatic;
import net.qilla.fired.weapon.magazine.MagazineClass;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.fired.weapon.magazine.StaticMagazineType;
import net.qilla.fired.weapon.magazine.implementation.Magazine;
import net.qilla.fired.weapon.visualstats.StatHolder;
import net.qilla.fired.weapon.visualstats.GunStat;
import net.qilla.qlibrary.items.QStack;
import net.qilla.qlibrary.util.QSound;
import net.qilla.qlibrary.util.tools.PlayerUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.*;

public abstract class GunImpl implements Gun {

    private static final Plugin PLUGIN = Fired.getInstance();

    static final int RELOAD_COOLDOWN = 750;
    static final QSound HIT_ENTITY = QSound.of(Sound.ENTITY_FISHING_BOBBER_THROW, 1.5F, 0.5f, SoundCategory.PLAYERS);
    static final QSound HIT_MARKER = QSound.of(Sound.ENTITY_FISHING_BOBBER_THROW, 2.0F, 0.5f, SoundCategory.PLAYERS);
    static final QSound DIMINISH_AMMO = QSound.of(Sound.BLOCK_IRON_TRAPDOOR_OPEN, 2.0F, 0.5F, SoundCategory.PLAYERS);
    static final QSound NO_AMMO = QSound.of(Sound.BLOCK_BAMBOO_STEP, 2.0F, 0.1F, SoundCategory.PLAYERS);

    private final MagazineLogic magazineLogic;

    private final String uuid;
    private final GunType<?> gunType;
    private final MagazineClass magazineClass;
    private final double damageMod;
    private final double accuracyMod;
    private final int fireCooldown;

    private final Rarity rarity;
    private final Component name;
    private final List<Component> description;
    private final ItemStack itemStack;
    private final QSound fireSound;

    private long lastFire;
    private long lastReload;

    public GunImpl(@NotNull GunType<?> gunType, @NotNull GunImpl.Factory<?> factory) {
        Preconditions.checkNotNull(gunType, "GunType cannot be null.");
        Preconditions.checkNotNull(factory, "Builder cannot be null.");

        this.gunType = gunType;
        this.uuid = factory.uuid;
        this.magazineClass = factory.magazineClass;
        this.damageMod = factory.damageMod;
        this.accuracyMod = factory.accuracyMod;
        this.fireCooldown = factory.fireCooldown;

        this.rarity = factory.rarity;
        this.name = factory.name;
        this.description = factory.description;
        this.itemStack = factory.itemStack;
        this.fireSound = factory.fireSound;

        this.magazineLogic = factory.magazineType == null ?
                new MagazineLogicDynamic() :
                new MagazineLogicStatic(factory.magazineType.createNew(this));

        this.lastFire = this.lastReload = System.currentTimeMillis();
    }

    @Override
    public boolean preFire(@NotNull Player holder, @NotNull ItemStack gunItem) {
        Preconditions.checkNotNull(holder, "Player cannot be null!");
        Preconditions.checkNotNull(gunItem, "Gun Item cannot be null!");

        Magazine magazine = this.getMagazine();

        if(magazine == null || magazine.isMagazineEmpty()) {
            PlayerUtil.Sound.loc(holder.getLocation(), NO_AMMO, 0.2f);
            return false;
        }

        long now = System.currentTimeMillis();

        if(this.lastFire > (now - this.fireCooldown) || !magazine.isBulletQueueEmpty()) return false;
        this.lastFire = now;

        return this.fire(holder, holder.getEyeLocation(), gunItem);
    }

    @Override
    public boolean fire(@NotNull Player shooter, @NotNull Location originLoc, @NotNull ItemStack gunItem) {
        Magazine magazine = this.getMagazine();

        if(magazine == null) return false;
        Bullet bullet = magazine.pullNextBullet();

        if(bullet == null) return false;
        if(magazine.isMagazineEmpty()) Bukkit.getScheduler().runTaskLater(PLUGIN, () -> PlayerUtil.Sound.loc(shooter, DIMINISH_AMMO, 0.2f), 5);

        PlayerUtil.Sound.loc(shooter, this.fireSound, 0.2f);

        bullet.fire(shooter, originLoc, originLoc.getDirection(), this);
        this.updateItem(gunItem);

        int cooldown = this.fireCooldown / 50;
        if(cooldown > 5) {
            shooter.setCooldown(Key.key(this.uuid), cooldown);
        }

        return true;
    }

    @Override
    public boolean loadIntoGun(@NotNull Player holder, @NotNull ItemStack heldItem, @NotNull ItemStack loadItem) {
        long curTime = System.currentTimeMillis();

        if(GunImpl.RELOAD_COOLDOWN > (curTime - this.lastReload)) return false;
        this.lastReload = curTime;

        return this.magazineLogic.loadIntoGun(holder, this, heldItem, loadItem);
    }

    @Override
    public @Nullable Magazine getMagazine() {
        return this.magazineLogic.getMagazine();
    }

    @Override
    public void hitBlock(@NotNull Player shooter, @NotNull Block block, @NotNull Vector hitVec) {

    }

    @Override
    public void hitEntity(@NotNull Player shooter, @NotNull LivingEntity entity, @NotNull Vector hitVec) {
        Location targetLoc = entity.getLocation();

        PlayerUtil.Sound.player(shooter, GunImpl.HIT_MARKER, 0);
        PlayerUtil.Sound.loc(targetLoc, GunImpl.HIT_ENTITY, 0);
    }

    @Override
    public @NotNull String getUUID() {
        return this.uuid;
    }

    @Override
    public @NotNull GunType<?> getType() {
        return this.gunType;
    }

    @Override
    public @NotNull Rarity getRarity() {
        return this.rarity;
    }

    @Override
    public @NotNull Component getName() {
        return this.name;
    }

    @Override
    public @NotNull StatHolder buildWeaponStats() {
        StatHolder statHolder = new StatHolder();
        Magazine magazine = this.getMagazine();
        Bullet nextBullet = null;

        if(magazine != null) nextBullet = magazine.peekNextBullet();

        statHolder.set(GunStat.DamageMod.of(nextBullet == null ? 0 : nextBullet.getDamage(), this.getDamageMod()));
        statHolder.set(GunStat.FireRate.of(this.fireCooldown));
        statHolder.set(GunStat.Accuracy.of(nextBullet == null ? 0.0f : nextBullet.getBulletSpread(), this.accuracyMod));

        return statHolder;
    }

    @Override
    public @NotNull List<Component> buildLore() {
        List<Component> lore = new ArrayList<>();

        lore.add(Component.empty());
        lore.addAll(this.buildWeaponStats().getLore());
        lore.add(Component.empty());
        lore.addAll(this.magazineLogic.buildLoreForGun());

        if(!this.description.isEmpty()) {
            lore.add(Component.empty());
            lore.addAll(this.description);
        }

        lore.add(Component.empty());
        lore.add(this.rarity.display().append(Component.text(" WEAPON")));

        return lore;
    }

    @Override
    public @NotNull ItemStack getItem() {
        this.itemStack.setData(DataComponentTypes.ITEM_NAME, this.getName());
        this.itemStack.setData(DataComponentTypes.LORE, ItemLore.lore(this.buildLore()));
        this.itemStack.editMeta(meta -> {
            meta.getPersistentDataContainer().set(NKey.GUN, PersistentDataType.STRING, this.uuid);
        });
        this.itemStack.setData(DataComponentTypes.USE_COOLDOWN, UseCooldown.useCooldown(this.fireCooldown / 1000f).cooldownGroup(Key.key(this.uuid)).build());

        return this.itemStack;
    }

    @Override
    public void updateItem(@NotNull ItemStack itemStack) {
        itemStack.setData(DataComponentTypes.LORE, ItemLore.lore(this.buildLore()));
    }

    @Override
    public @NotNull MagazineClass getMagazineClass() {
        return this.magazineClass;
    }

    @Override
    public double getDamageMod() {
        return this.damageMod;
    }

    @Override
    public double getAccuracyMod() {
        return this.accuracyMod;
    }

    @Override
    public int getFireCD() {
        return this.fireCooldown;
    }

    @Override
    public @NotNull QSound getFireSound() {
        return this.fireSound;
    }

    public long getLastFire() {
        return this.lastFire;
    }

    public long getLastReload() {
        return this.lastReload;
    }

    public void setLastFire(long lastFire) {
        this.lastFire = lastFire;
    }

    public void setLastReload(long lastReload) {
        this.lastReload = lastReload;
    }

    public @NotNull Plugin getPlugin() {
        return PLUGIN;
    }

    public static class Factory<T extends GunImpl.Factory<T>> {

        private final String uuid;
        private StaticMagazineType<?> magazineType;

        private MagazineClass magazineClass;
        private double damageMod;
        private double accuracyMod;
        private int fireCooldown;

        private Rarity rarity;
        private Component name;
        private List<Component> description;
        private ItemStack itemStack;
        private QSound fireSound;

        public Factory() {
            this.uuid = UUID.randomUUID().toString();
            this.magazineClass = MagazineClass.PISTOL;
            this.damageMod = 1.0;
            this.accuracyMod = 1;
            this.fireCooldown = 250;
            this.magazineType = null;

            this.rarity = Rarity.COMMON_I;
            this.name = MiniMessage.miniMessage().deserialize("<red>Missing Field");
            this.description = List.of();
            this.itemStack = QStack.ofClean(Material.WOODEN_HOE).clearData();
            this.fireSound = QSound.of(Sound.ENTITY_IRON_GOLEM_REPAIR, 1.0F, 0.33F, SoundCategory.PLAYERS);
        }

        public @NotNull T staticMagazine(@NotNull StaticMagazineType<?> magazineType) {
            this.magazineType = magazineType;
            return this.self();
        }

        /**
         * The Magazine type to look for when validating weapon compatability.
         */

        public @NotNull T magazineClass(@NotNull MagazineClass magazineClass) {
            Preconditions.checkNotNull(magazineClass, "Magazine Class cannot be null!");
            this.magazineClass = magazineClass;
            return this.self();
        }

        public @NotNull T damageMod(double modifier) {
            this.damageMod = Math.max(0, modifier);
            return this.self();
        }

        /**
         * An accuracy multiplier to use for this gun. Must be greater than 0.
         * Values less than 1 will tighten the bullet spread, values over 1
         * will multiply the bullet's final spread.
         * @param modifier Any number greater than 0.
         */

        public @NotNull T accuracyMod(double modifier) {
            this.accuracyMod = Math.max(0, modifier);
            return this.self();
        }

        /**
         * Cooldown in milliseconds that the gun will wait for before allowing another
         * shot to be fired.
         */

        public @NotNull T fireCooldown(int cooldown) {
            this.fireCooldown = Math.max(0, cooldown);
            return this.self();
        }

        public @NotNull T rarity(@NotNull Rarity rarity) {
            Preconditions.checkNotNull(rarity, "Rarity cannot be null!");
            this.rarity = rarity;
            return self();
        }

        public @NotNull T name(@NotNull Component name) {
            Preconditions.checkNotNull(name, "Name cannot be null!");
            this.name = name;
            return self();
        }

        public @NotNull T description(@Nullable List<Component> description) {
            Preconditions.checkNotNull(description, "Lore cannot be null!");
            this.description = description;
            return self();
        }

        public @NotNull T itemStack(@NotNull ItemStack itemStack) {
            Preconditions.checkNotNull(itemStack, "Item cannot be null!");
            this.itemStack = itemStack;
            return self();
        }

        public @NotNull T fireSound(@NotNull QSound sound) {
            Preconditions.checkNotNull(sound, "Fire sound cannot be null!");
            this.fireSound = sound;
            return this.self();
        }

        @SuppressWarnings("unchecked")
        protected @NotNull T self() {
            return (T) this;
        }
    }
}