package net.qilla.fired.event;

import io.papermc.paper.persistence.PersistentDataContainerView;
import net.qilla.fired.Fired;
import net.qilla.fired.misc.NKey;
import net.qilla.fired.player.data.EntityDataRegistry;
import net.qilla.fired.weapon.magazine.LiveMagazineRegistry;
import net.qilla.fired.weapon.magazine.implementation.Magazine;
import net.qilla.fired.weapon.gun.implementation.Gun;
import net.qilla.fired.weapon.gun.LiveGunRegistry;
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

        gun.attemptFire(player, gunItem);
        event.setCancelled(true);
    }

    @EventHandler
    private void loadMagazine(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;

        ItemStack magazineItem = event.getCursor();
        ItemStack gunItem = event.getCurrentItem();

        if(!event.getClick().isRightClick() || gunItem == null) return;

        PersistentDataContainerView magPDC = magazineItem.getPersistentDataContainer();
        PersistentDataContainerView gunPDC = gunItem.getPersistentDataContainer();

        String magID = magPDC.get(NKey.MAGAZINE, PersistentDataType.STRING);
        String gunID = gunPDC.get(NKey.GUN, PersistentDataType.STRING);

        if(gunID == null) return;
        Gun gun = this.liveGunReg.getGun(gunID);

        if(gun == null) return;

        Magazine magazine = null;

        if(magID != null) magazine = this.liveMagazineReg.getMag(magID);

        Player player = (Player) event.getWhoClicked();

        gun.attemptLoad(player, gunItem, magazineItem, magazine);
        event.setCancelled(true);
    }

    @EventHandler
    private void loadBullets(InventoryClickEvent event) {
        if(event.getClickedInventory() == null) return;

        ItemStack magItem = event.getCurrentItem();
        ItemStack bulletItem = event.getCursor();

        if(!event.getClick().isRightClick() || magItem == null) return;

        PersistentDataContainerView magPDC = magItem.getPersistentDataContainer();

        String magID = magPDC.get(NKey.MAGAZINE, PersistentDataType.STRING);

        if(magID == null) return;

        Magazine mag = this.liveMagazineReg.getMag(magID);
        Player player = (Player) event.getWhoClicked();

        if(mag != null) {
            mag.attemptLoad(player, magItem, bulletItem);
            if(event.getSlot() < 9) player.getInventory().setHeldItemSlot(event.getSlot());
        }
        event.setCancelled(true);
    }

    @EventHandler
    private void resumeLoadBullets(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        ItemStack magItem = player.getInventory().getItem(event.getNewSlot());

        if(magItem == null) return;

        String magID = magItem.getPersistentDataContainer().get(NKey.MAGAZINE, PersistentDataType.STRING);

        if(magID == null) return;

        Magazine mag = this.liveMagazineReg.getMag(magID);

        if(mag == null || !mag.hasQueuedBullets()) return;

        mag.load(player, magItem);
    }

    @EventHandler
    private void entityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();

        if(entity instanceof Player) this.entityDataReg.getOrCreate(entity).resetBleeding();
        else this.entityDataReg.remove(entity);
    }
}
