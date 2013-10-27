package org.carlgo11.anti.p12a.Listener;

import org.carlgo11.anti.p12a.antip12a;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    antip12a plugin;

    public CommandListener(antip12a plug)
    {
        this.plugin = plug;
    }

    @EventHandler
    public void onCMD(PlayerCommandPreprocessEvent e)
    {
        Player player = e.getPlayer();

        if (plugin.randomText != null) {
            if ((!plugin.randomText.contains(player)) && (!e.getMessage().contains("verify"))) {
                e.setCancelled(true);
            }
        }
    }
}
