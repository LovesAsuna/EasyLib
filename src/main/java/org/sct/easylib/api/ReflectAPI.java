package org.sct.easylib.api;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.Set;

public interface ReflectAPI {
    Object asNMSCopy(ItemStack itemStack);

    Object asBukkitCopy(Object var1);

    void respawn(Player player);

    Object getPlayerHandle(Player player);

    Class<?> getBukkitClass(String className);

    Class<?> getMinecraftClass(String className);

    @SuppressWarnings("unchecked")
    <T extends Enum> Set<T> getAllMatchingEnum(Class<T> enumClass, String... names);

    Object getNmsPlayer(Player player);

    Object getNmsScoreboard(Scoreboard var1);

    ItemStack setNbt(ItemStack itemStack, String string, String content);

    Object getNBT(ItemStack itemStack, String string);

    ItemStack removeNBT(ItemStack itemStack, String string);

    void sendAllPacket(Object packet);

    void sendListPacket(List<String> list, Object packet);

    Class<?> getClass(String var1);
}
