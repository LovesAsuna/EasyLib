package org.sct.plugincore.api;

import org.bukkit.OfflinePlayer;

public interface EcoAPI {
    boolean loadVault() throws NullPointerException;

    double get(OfflinePlayer player);

    boolean has(OfflinePlayer player, double money);

    void give(OfflinePlayer player, double money);

    void take(OfflinePlayer player, double money);
}
