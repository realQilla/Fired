package net.qilla.fired.player;

import net.qilla.fired.player.data.SessionDataRegistry;
import net.qilla.qlibrary.data.QSessionData;
import net.qilla.qlibrary.player.QPacketListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PacketListener extends QPacketListener implements Listener {

    private final SessionDataRegistry sessionDataRegistry;

    public PacketListener(SessionDataRegistry sessionDataRegistry) {
        this.sessionDataRegistry = sessionDataRegistry;
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        QSessionData sessionData = sessionDataRegistry.getOrCreate(event.getPlayer());

        this.addListener(sessionData);
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        this.removeListener(event.getPlayer());
    }
}
