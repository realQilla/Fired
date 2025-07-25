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
import net.qilla.fired.weapon.bullet.BulletClass;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import net.qilla.fired.weapon.magazine.implementation.Magazine;
import net.qilla.fired.weapon.gun.GunType;
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

    private final String uuid;
    private final GunType<?> gunType;
    private final BulletClass bulletClass;
    private final float damage;
    private final double accuracy;
    private final int fireCooldown;

    private final Rarity rarity;
    private final Component name;
    private final List<Component> description;
    private final ItemStack itemStack;
    private final QSound fireSound;
    private final QSound loadSound;
    private final QSound unloadSound;

    private Magazine magazine;
    private ItemStack magazineItem;
    private long lastFire;
    private long lastReload;

    public GunImpl(@NotNull GunType<?> gunType, @NotNull GunImpl.Factory<?> factory) {
        Preconditions.checkNotNull(gunType, "GunType cannot be null.");
        Preconditions.checkNotNull(factory, "Builder cannot be null.");

        this.uuid = UUID.randomUUID().toString();
        this.gunType = gunType;
        this.bulletClass = factory.bulletClass;
        this.damage = factory.damage;
        this.accuracy = factory.accuracy;
        this.fireCooldown = factory.fireCooldown;

        this.rarity = factory.rarity;
        this.name = factory.name;
        this.description = factory.description;
        this.itemStack = factory.itemStack;
        this.fireSound = factory.fireSound;
        this.loadSound = factory.loadSound;
        this.unloadSound = factory.unloadSound;
        this.magazine = factory.magazine;

        this.magazineItem = ItemStack.empty();
        this.lastFire = this.lastReload = System.currentTimeMillis();
    }

    @Override
    public boolean attemptFire(@NotNull Player holder, @NotNull ItemStack gunItem) {
        Preconditions.checkNotNull(holder, "Player cannot be null!");
        Preconditions.checkNotNull(gunItem, "Gun Item cannot be null!");

        if(this.magazine == null || this.magazine.isMagazineEmpty()) {
            PlayerUtil.Sound.loc(holder.getLocation(), NO_AMMO, 0.2f);
            return false;
        }

        long now = System.currentTimeMillis();

        if(this.lastFire > (now - this.fireCooldown)) return false;
        this.lastFire = now;

        return this.fire(holder, holder.getEyeLocation(), gunItem);
    }

    @Override
    public boolean fire(@NotNull Player shooter, @NotNull Location originLoc, @NotNull ItemStack gunItem) {
        Bullet bullet = this.magazine.pullBullet();

        if(bullet == null) return false;
        if(this.magazine.isMagazineEmpty()) Bukkit.getScheduler().runTaskLater(PLUGIN, () -> PlayerUtil.Sound.loc(shooter, DIMINISH_AMMO, 0.2f), 5);

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
    public boolean attemptLoad(@NotNull Player holder, @NotNull ItemStack gunItem, @NotNull ItemStack magazineItem, @Nullable Magazine magazine) {
        long curTime = System.currentTimeMillis();

        if(GunImpl.RELOAD_COOLDOWN > (curTime - this.lastReload)) return false;
        this.lastReload = curTime;

        if(magazineItem.isEmpty() && this.magazineItem.isEmpty()) return false;

        return this.load(holder, gunItem, magazineItem, magazine);
    }

    @Override
    public boolean load(@NotNull Player holder,  @NotNull ItemStack gunItem, @NotNull ItemStack magazineItem, @Nullable Magazine magazine) {

        // First: Return item in the magazine slot - if one is loaded.
        if(!this.magazineItem.isEmpty()) {
            if(this.magazine != null) holder.give(this.magazine.getItem());
            else holder.give(this.magazineItem);
            this.magazineItem = ItemStack.empty();
            PlayerUtil.Sound.loc(holder, this.unloadSound, 0.2f);
            // Then: Otherwise load item in cursor if one exists.
        } else if(!magazineItem.isEmpty()) {
            this.magazineItem = magazineItem.clone();
            magazineItem.setAmount(0);
            PlayerUtil.Sound.loc(holder, this.loadSound, 0.2f);
        }

        // Finally: Set the magazine field if the input magazine is valid for this gun.
        if(magazine == null || magazine.getBulletClass() != this.bulletClass || magazine.hasQueuedBullets()) this.magazine = null;
        else this.magazine = magazine;

        this.updateItem(gunItem);
        return true;
    }

    @Override
    public @Nullable Magazine getMagazine() {
        return this.magazine;
    }

    @Override
    public @Nullable ItemStack getMagazineItem() {
        return this.magazineItem;
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
    public @NotNull StatHolder buildStats() {
        StatHolder statHolder = new StatHolder();
        Bullet nextBullet = null;

        if(this.magazine != null) nextBullet = this.magazine.viewNextBullet();

        statHolder.set(GunStat.Damage.of(this.getDamage(), nextBullet == null ? 0 : nextBullet.getDamage()));
        statHolder.set(GunStat.FireRate.of(this.fireCooldown));
        statHolder.set(GunStat.Accuracy.of(nextBullet == null ? 0.0f : nextBullet.getBulletSpread(), this.accuracy));

        return statHolder;
    }

    @Override
    public @NotNull List<Component> buildLore() {
        List<Component> lore = new ArrayList<>();

        if(this.magazine == null) {
            if(!this.magazineItem.isEmpty()) lore.add(MiniMessage.miniMessage().deserialize("<!italic><red><bold>MAGAZINE MALFUNCTION</red>"));
        } else {
            if(this.magazine.isMagazineEmpty()) lore.add(MiniMessage.miniMessage().deserialize("<!italic><red><bold>MAGAZINE EMPTY</red>"));
            else if(this.magazine.getLoadedBulletsSize() <= this.magazine.getCapacity() / 4) lore.add(MiniMessage.miniMessage().deserialize("<!italic><gold><bold>MAGAZINE LOW</gold>"));
            else if(this.magazine.getLoadedBulletsSize() <= this.magazine.getCapacity() / 2) lore.add(MiniMessage.miniMessage().deserialize("<!italic><yellow><bold>MAGAZINE HALF</yellow>"));
            else lore.add(MiniMessage.miniMessage().deserialize("<!italic><green><bold>MAGAZINE FULL</green>"));
        }

        String magazineLore = this.magazine != null ? MiniMessage.miniMessage().serialize(this.magazine.getName()) :
                this.magazineItem.isEmpty() ? "<red><bold>EMPTY</bold><red>" : MiniMessage.miniMessage().serialize(this.magazineItem.getData(DataComponentTypes.ITEM_NAME));

        lore.add(Component.empty());
        lore.addAll(this.buildStats().getLore());
        lore.add(Component.empty());
        lore.add(MiniMessage.miniMessage().deserialize("<!italic><white><blue>☄</blue> Magazine: " + magazineLore));

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
    public @NotNull BulletClass getBulletClass() {
        return this.bulletClass;
    }

    @Override
    public float getDamage() {
        return this.damage;
    }

    @Override
    public double getAccuracy() {
        return this.accuracy;
    }

    @Override
    public int getFireCD() {
        return this.fireCooldown;
    }

    @Override
    public @NotNull QSound getFireSound() {
        return this.fireSound;
    }

    @Override
    public @NotNull QSound getLoadSound() {
        return this.loadSound;
    }

    @Override
    public @NotNull QSound getUnloadSound() {
        return this.unloadSound;
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

        private BulletClass bulletClass;
        private float damage;
        private double accuracy;
        private int fireCooldown;

        private Rarity rarity;
        private Component name;
        private List<Component> description;
        private ItemStack itemStack;
        private QSound fireSound;
        private QSound loadSound;
        private QSound unloadSound;
        private Magazine magazine;

        public Factory() {
            this.bulletClass = BulletClass.PISTOL;
            this.rarity = Rarity.COMMON_I;
            this.damage = 2.5F;
            this.accuracy = 1;
            this.fireCooldown = 250;

            this.name = MiniMessage.miniMessage().deserialize("<red>Missing Field");
            this.description = List.of();
            this.itemStack = QStack.ofClean(Material.WOODEN_HOE).clearData();
            this.fireSound = QSound.of(Sound.ENTITY_IRON_GOLEM_REPAIR, 1.0F, 0.33F, SoundCategory.PLAYERS);
            this.loadSound = QSound.of(Sound.BLOCK_CHEST_LOCKED, 2.0F, 0.25F, SoundCategory.PLAYERS);
            this.unloadSound = QSound.of(Sound.BLOCK_IRON_TRAPDOOR_CLOSE, 1.5F, 0.35F, SoundCategory.PLAYERS);
            this.magazine = null;
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

        /**
         * The magazine type to look for when validating a magazine to be compatible.
         */

        public @NotNull T bulletClass(@NotNull BulletClass bulletClass) {
            Preconditions.checkNotNull(bulletClass, "Weapon Class cannot be null!");
            this.bulletClass = bulletClass;
            return this.self();
        }

        public @NotNull T damage(float damage) {
            this.damage = Math.max(0, damage);
            return this.self();
        }

        /**
         * An accuracy multiplier to use for this gun. Must be greater than 0.
         * Values less than 1 will tighten the bullet spread, values over 1
         * will multiply the bullet's final spread.
         * @param accuracy Any number greater than 0.
         */

        public @NotNull T accuracy(double accuracy) {
            this.accuracy = Math.max(0, accuracy);
            return this.self();
        }

        /**
         * Cooldown in milliseconds that the gun will wait for before allowing another
         * shot to be fired.
         */

        public @NotNull T fireCooldown(int fireCooldown) {
            this.fireCooldown = Math.max(0, fireCooldown);
            return this.self();
        }

        public @NotNull T fireSound(@NotNull QSound sound) {
            Preconditions.checkNotNull(sound, "Fire sound cannot be null!");
            this.fireSound = sound;
            return this.self();
        }

        public @NotNull T loadSound(@NotNull QSound sound) {
            Preconditions.checkNotNull(sound, "Reload start sound cannot be null!");
            this.loadSound = sound;
            return this.self();
        }

        public @NotNull T unloadSound(@NotNull QSound sound) {
            Preconditions.checkNotNull(sound, "Reload end sound cannot be null!");
            this.unloadSound = sound;
            return this.self();
        }

        /**
         * The initial {@link net.qilla.fired.weapon.magazine.implementation.MagazineImpl} magazine that is loaded
         * at creation time.
         */

        public @NotNull T magazine(@Nullable Magazine magazine) {
            this.magazine = magazine;
            return this.self();
        }

        @SuppressWarnings("unchecked")
        protected @NotNull T self() {
            return (T) this;
        }
    }
}