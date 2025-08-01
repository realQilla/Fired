package net.qilla.fired.event;

import io.papermc.paper.persistence.PersistentDataContainerView;
import net.qilla.fired.Fired;
import net.qilla.fired.misc.NKey;
import net.qilla.fired.player.data.EntityDataRegistry;
import net.qilla.fired.weapon.bullet.BulletRegistry;
import net.qilla.fired.weapon.magazine.LiveMagazineRegistry;
import net.qilla.fired.weapon.magazine.implementation.Magazine;
import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.fired.weapon.gun.LiveGunRegistry;
import net.qilla.fired.weapon.magazine.implementation.MagazineDynamic;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public final class WeaponEvents implements Listener {

    private final EntityDataRegistry entityDataReg;
    private final LiveGunRegistry liveGunReg;
    private final LiveMagazineRegistry liveMagazineReg;

    public WeaponEvents(@NotNull Fired fired) {
        this.entityDataReg = fired.getEntityDataReg();
        this.liveGunReg = fired.getLiveGunReg();
        this.liveMagazineReg = fired.getLiveMagazineReg();
    }


    @EventHandler
    private void useWeapon(PlayerInteractEvent event) {
        ItemStack gunItem = event.getItem();

        if(event.getHand() != EquipmentSlot.HAND || gunItem == null) return;

        PersistentDataContainerView pdc = gunItem.getPersistentDataContainer();

        String id = pdc.get(NKey.GUN, PersistentDataType.STRING);

        if(id == null) return;

        Gun gun = this.liveGunReg.getGun(id);

        if(gun == null) return;

        Player player = event.getPlayer();

        if(!event.getAction().isRightClick()) return;

        gun.preFire(player, gunItem);
        event.setCancelled(true);
    }

    @EventHandler
    private void loadWeapon(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;

        ItemStack loadItem = event.getCursor();
        ItemStack heldItem = event.getCurrentItem();

        if(!event.getClick().isRightClick() || heldItem == null) return;

        String gunID = heldItem.getPersistentDataContainer()
                .get(NKey.GUN, PersistentDataType.STRING);

        if(gunID == null) return;

        Gun gun = this.liveGunReg.getGun(gunID);

        if(gun == null) return;

        Player player = (Player) event.getWhoClicked();

        gun.loadIntoGun(player, heldItem, loadItem);
        event.setCancelled(true);
    }

    @EventHandler
    private void loadMagazine(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;

        ItemStack heldItem = event.getCurrentItem();
        ItemStack loadItem = event.getCursor();

        if(!event.getClick().isRightClick() || heldItem == null) return;

        String magazineID = heldItem.getPersistentDataContainer()
                .get(NKey.MAGAZINE, PersistentDataType.STRING);

        if(magazineID == null) return;

        Player player = (Player) event.getWhoClicked();
        Magazine magazine = this.liveMagazineReg.getMag(magazineID);

        if(magazine != null) magazine.loadToQueue(player, heldItem, loadItem);
        event.setCancelled(true);
    }

    @EventHandler
    private void resumeLoadBullets(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack heldItem = player.getInventory().getItem(event.getNewSlot());

        if(heldItem == null) return;

        String magazineID = heldItem.getPersistentDataContainer().get(NKey.MAGAZINE, PersistentDataType.STRING);

        if(magazineID != null) {
            MagazineDynamic magazine = this.liveMagazineReg.getMag(magazineID);

            if(magazine != null) magazine.loadIntoMagazine(player, heldItem);
        }

        String gunID = heldItem.getPersistentDataContainer().get(NKey.GUN, PersistentDataType.STRING);

        if(gunID != null) {
            Gun gun = this.liveGunReg.getGun(gunID);

            if(gun != null) {
                Magazine magazine = gun.getMagazine();

                if(magazine != null) magazine.loadIntoMagazine(player, heldItem);
            }
        }
    }

    @EventHandler
    private void entityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();

        if(entity instanceof Player) this.entityDataReg.getOrCreate(entity).resetBleeding();
        else this.entityDataReg.remove(entity);
    }
}
