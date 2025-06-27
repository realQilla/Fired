package net.qilla.fired.weapon.gun.implementation;

import com.google.common.base.Preconditions;
import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.qilla.fired.Fired;
import net.qilla.fired.misc.NKey;
import net.qilla.fired.weapon.WeaponClass;
import net.qilla.fired.weapon.bullet.implementation.Bullet;
import net.qilla.fired.weapon.magazine.implementation.Magazine;
import net.qilla.fired.weapon.gun.GunType;
import net.qilla.qlibrary.items.QStack;
import net.qilla.qlibrary.util.particle.QParticle;
import net.qilla.qlibrary.util.sound.QSound;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public abstract class GunImpl implements Gun {

    private static final Plugin PLUGIN = Fired.getInstance();

    private static final Random RANDOM = new Random();
    protected static final int RELOAD_COOLDOWN = 250;
    private static final QSound HIT_ENTITY = QSound.of(Sound.ENTITY_FISHING_BOBBER_THROW, 1.5F, 0.5f, SoundCategory.PLAYERS);
    private static final QSound HIT_MARKER = QSound.of(Sound.ENTITY_FISHING_BOBBER_THROW, 2.0F, 5.0f, SoundCategory.PLAYERS);
    private static final QSound DIMINISH_AMMO = QSound.of(Sound.BLOCK_IRON_TRAPDOOR_OPEN, 2.0F, 0.5F, SoundCategory.PLAYERS);
    private static final QSound MAGAZINE_EMPTY = QSound.of(Sound.ENTITY_IRON_GOLEM_STEP, 2.0F, 0.25F, SoundCategory.PLAYERS);

    private final String uuid;
    private final GunType<?> gunType;
    private final WeaponClass weaponClass;
    private final float damage;
    private final int fireCooldown;
    private final float accuracy;

    private final Component name;
    private final List<Component> lore;
    private final ItemStack itemStack;
    private final QParticle fireParticle;
    private final QSound fireSound;
    private final QSound loadSound;
    private final QSound unloadSound;

    private Magazine magazine;
    private ItemStack magazineItem;
    private long lastFire;
    private long lastReload;

    GunImpl(@NotNull GunType<?> gunType, @NotNull GunImpl.Factory<?> factory) {
        Preconditions.checkNotNull(gunType, "GunType cannot be null.");
        Preconditions.checkNotNull(factory, "Builder cannot be null.");

        this.uuid = UUID.randomUUID().toString();
        this.gunType = gunType;
        this.weaponClass = factory.weaponClass;
        this.damage = factory.damage;
        this.fireCooldown = factory.fireCooldown;
        this.accuracy = factory.accurecy;

        this.name = factory.name;
        this.lore = factory.lore;
        this.itemStack = factory.itemStack;
        this.fireParticle = factory.fireParticle;
        this.fireSound = factory.fireSound;
        this.loadSound = factory.loadSound;
        this.unloadSound = factory.unloadSound;

        this.magazine = null;
        this.magazineItem = ItemStack.empty();
        this.lastFire = this.lastReload = System.currentTimeMillis();
    }

    @Override
    public boolean attemptFire(@NotNull Player holder, @NotNull ItemStack gunItem) {
        Preconditions.checkNotNull(holder, "Player cannot be null!");

        if(this.magazine == null || this.magazine.isEmpty()) {
            PlayerUtil.sound(holder, MAGAZINE_EMPTY, false);
            return false;
        }

        long curTime = System.currentTimeMillis();

        if(this.lastFire > (curTime - this.fireCooldown)) return false;
        this.lastFire = curTime;

        this.fire(holder, holder.getEyeLocation(), gunItem);
        return true;
    }

    @Override
    public boolean fire(@NotNull Player shooter, @NotNull Location originLoc, @NotNull ItemStack gunItem) {
        Bullet bullet = this.magazine.unload();

        if(bullet == null) return false;
        if(this.magazine.isEmpty()) Bukkit.getScheduler().runTaskLater(PLUGIN, () -> PlayerUtil.sound(shooter, DIMINISH_AMMO, true), 5);

        PlayerUtil.sound(shooter, this.fireSound, true);

        Vector originDir = originLoc.getDirection();
        float offset = this.accuracy / 100;
        double theta = RANDOM.nextDouble() * 2 * Math.PI;
        double phi = Math.acos(1 - RANDOM.nextDouble() * (1 - Math.cos(offset)));

        Vector perpendicular1 = originDir.clone().crossProduct(new Vector(1, 0, 0));
        if (perpendicular1.lengthSquared() < 0.01) {
            perpendicular1 = originDir.clone().crossProduct(new Vector(0, 1, 0));
        }
        perpendicular1.normalize();

        Vector perpendicular2 = originDir.clone().crossProduct(perpendicular1).normalize();

        double sinPhi = Math.sin(phi);
        Vector dir = originDir.clone().multiply(Math.cos(phi))
                .add(perpendicular1.multiply(sinPhi * Math.cos(theta)))
                .add(perpendicular2.multiply(sinPhi * Math.sin(theta))).normalize();

        bullet.fire(shooter, originLoc, dir, this);
        this.updateItem(gunItem);

        return true;
    }

    @Override
    public boolean attemptReload(@NotNull Player holder, @Nullable Magazine magazine, @NotNull ItemStack magazineItem) {
        long curTime = System.currentTimeMillis();

        if(GunImpl.RELOAD_COOLDOWN > (curTime - this.lastReload)) return false;
        this.lastReload = curTime;

        if(magazineItem.isEmpty() && this.magazineItem.isEmpty()) return false;

        this.reload(holder, magazine, magazineItem);
        return true;
    }

    @Override
    public boolean reload(@NotNull Player holder, @Nullable Magazine magazine, @NotNull ItemStack magazineItem) {
        PlayerUtil.sound(holder, this.loadSound, true);

        if(!this.magazineItem.isEmpty()) {
            if(this.magazine != null) holder.give(this.magazine.getItem());
            else holder.give(this.magazineItem);
            this.magazineItem = ItemStack.empty();
            this.magazine = null;
        }

        if(!magazineItem.isEmpty()) {
            this.magazineItem = magazineItem.clone();
            magazineItem.setAmount(0);
        }

        if(magazine == null || magazine.getWeaponClass() != this.weaponClass || magazine.hasQueued()) return false;

        this.magazine = magazine;
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
    public void hitBlock(@NotNull Player shooter, @NotNull Block block, @NotNull Location loc) {

    }

    @Override
    public void hitEntity(@NotNull Player shooter, @NotNull LivingEntity entity, @NotNull Location loc) {
        Location targetLoc = entity.getLocation();

        PlayerUtil.sound(shooter, GunImpl.HIT_MARKER, true);
        PlayerUtil.sound(targetLoc, GunImpl.HIT_ENTITY, true);
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
    public @NotNull Component getName() {
        return this.name;
    }

    @Override
    public @NotNull List<Component> getStats() {
        Bullet nextBullet = null;

        if(this.magazine != null) {
            nextBullet = this.magazine.getNext();
        }

        StringBuilder damage = new StringBuilder().append("<!italic><white><gold>\uD83D\uDD25</gold> Total Damage: <gold>");

        if(nextBullet == null) damage.append(this.getDamage()).append("</gold>");
        else damage.append(this.getDamage() + nextBullet.getDamage()).append("</gold>").append(" <green>(+").append(nextBullet.getDamage()).append(")</green>");

        return new ArrayList<>(List.of(
                MiniMessage.miniMessage().deserialize(damage.toString()),
                MiniMessage.miniMessage().deserialize("<!italic><white><yellow>\uD83C\uDFF9</yellow> Bullet Spread: <yellow>" + this.getAccuracy()),
                MiniMessage.miniMessage().deserialize("<!italic><white><aqua>⏳</aqua> Fire Speed: <aqua>" + 1000 / this.fireCooldown)
        ));
    }

    @Override
    public @NotNull List<Component> getLore() {
        List<Component> lore = new ArrayList<>();

        if(!this.magazineItem.isEmpty() && this.magazine == null) lore.add(MiniMessage.miniMessage().deserialize("<!italic><red><bold>MAGAZINE MALFUNCTION</red>"));

        String magazineLore = this.magazineItem.isEmpty() ?
                "<red><bold>EMPTY</bold><red>" : this.magazine == null ?
                MiniMessage.miniMessage().serialize(this.magazineItem.getData(DataComponentTypes.ITEM_NAME)) : MiniMessage.miniMessage().serialize(this.magazine.getName());

        lore.add(Component.empty());
        lore.addAll(this.getStats());
        lore.add(Component.empty());
        lore.add(MiniMessage.miniMessage().deserialize("<!italic><white><blue>☄</blue> Magazine: " + magazineLore));

        if(!this.lore.isEmpty()) {
            lore.add(Component.empty());
            lore.addAll(this.lore);
        }

        return lore;
    }

    @Override
    public @NotNull ItemStack getItem() {
        this.itemStack.setData(DataComponentTypes.ITEM_NAME, this.getName());
        this.itemStack.setData(DataComponentTypes.LORE, ItemLore.lore(this.getLore()));
        this.itemStack.editMeta(meta -> {
            meta.getPersistentDataContainer().set(NKey.GUN, PersistentDataType.STRING, this.uuid);
        });

        return this.itemStack;
    }

    @Override
    public void updateItem(@NotNull ItemStack itemStack) {
        itemStack.setData(DataComponentTypes.LORE, ItemLore.lore(this.getLore()));
    }

    @Override
    public @NotNull WeaponClass getWeaponClass() {
        return this.weaponClass;
    }

    @Override
    public float getDamage() {
        return this.damage;
    }

    @Override
    public int getFireCD() {
        return this.fireCooldown;
    }

    @Override
    public float getAccuracy() {
        return this.accuracy;
    }

    @Override
    public @NotNull QParticle getFireParticle() {
        return this.fireParticle;
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

    public static class Factory<T extends Factory<T>> {

        private WeaponClass weaponClass;
        private float damage;
        private int fireCooldown;
        private float accurecy;

        private Component name;
        private List<Component> lore;
        private ItemStack itemStack;
        private QParticle fireParticle;
        private QSound fireSound;
        private QSound loadSound;
        private QSound unloadSound;

        protected Factory() {
            this.weaponClass = WeaponClass.PISTOL;
            this.damage = 2.5F;
            this.fireCooldown = 250;
            this.accurecy = 0.25f;

            this.name = MiniMessage.miniMessage().deserialize("<red>Missing Field");
            this.lore = List.of();
            this.itemStack = QStack.ofClean(Material.WOODEN_HOE).clearData();
            this.fireParticle = QParticle.of(Material.COAL_BLOCK, 1, 0.0F);
            this.fireSound = QSound.of(Sound.ENTITY_IRON_GOLEM_REPAIR, 1.0F, 0.5F, SoundCategory.PLAYERS);
            this.loadSound = QSound.of(Sound.BLOCK_PISTON_EXTEND, 1.0F, 0.2F, SoundCategory.PLAYERS);
            this.unloadSound = QSound.of(Sound.BLOCK_PISTON_CONTRACT, 1.0F, 0.2F, SoundCategory.PLAYERS);
        }

        public @NotNull T name(@NotNull Component name) {
            Preconditions.checkNotNull(name, "Name cannot be null!");
            this.name = name;
            return self();
        }

        public @NotNull T lore(@Nullable List<Component> lore) {
            Preconditions.checkNotNull(lore, "Lore cannot be null!");
            this.lore = lore;
            return self();
        }

        public @NotNull T itemStack(@NotNull ItemStack itemStack) {
            Preconditions.checkNotNull(itemStack, "Item cannot be null!");
            this.itemStack = itemStack;
            return self();
        }

        public @NotNull T weaponClass(@NotNull WeaponClass weaponClass) {
            Preconditions.checkNotNull(weaponClass, "Weapon Class cannot be null!");
            this.weaponClass = weaponClass;
            return this.self();
        }

        public @NotNull T damage(float damage) {
            this.damage = Math.max(0, damage);
            return this.self();
        }

        public @NotNull T fireCooldown(int fireCooldown) {
            this.fireCooldown = Math.max(0, fireCooldown);
            return this.self();
        }

        public @NotNull T accuracy(float accuracy) {
            this.accurecy = Math.max(0, accuracy);
            return this.self();
        }

        public @NotNull T fireParticle(@NotNull QParticle sound) {
            Preconditions.checkNotNull(sound, "Fire particle cannot be null!");
            this.fireParticle = sound;
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

        @SuppressWarnings("unchecked")
        protected @NotNull T self() {
            return (T) this;
        }
    }
}