package net.qilla.fired.player.data;

import net.qilla.qlibrary.data.QSessionData;
import net.qilla.qlibrary.data.QSessionDataImpl;
import net.qilla.qlibrary.data.QSessionDataRegistryImpl;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.Map;

public class SessionDataRegistry extends QSessionDataRegistryImpl<QSessionData> {

    private static SessionDataRegistry INSTANCE;

    private SessionDataRegistry() {
    }

    public static SessionDataRegistry getInstance() {
        if(INSTANCE == null) INSTANCE = new SessionDataRegistry();
        return INSTANCE;
    }

    @Override
    public @NotNull QSessionData getOrCreate(@NotNull Player player) {
        String uuid = player.getUniqueId().toString();
        Map<String, QSessionData> registry = this.registry();

        return registry.compute(uuid , (k, v) -> {
            if(v == null) return new QSessionDataImpl(player);
            return registry.get(uuid);
        });
    }
}
