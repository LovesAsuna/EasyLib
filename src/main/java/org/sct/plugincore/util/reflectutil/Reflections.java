package org.sct.plugincore.util.reflectutil;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.Scoreboard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 * @author icestar
 * @date 2020/2/11 21:15
 */

public class Reflections {
    private Class<?> CraftServerClass;
    private Class<?> CraftWorldClass;
    private Class<?> WorldServerClass;
    private Object CraftServer;
    private Class<?> CraftStatistic;
    private Class<?> Statistics;
    private Class<?> MinecraftServerClass;
    private Class<?> PropertyManagerClass;
    private Object MinecraftServer;
    private Class<?> ServerStatisticManager;
    private Class<?> EntityHuman;
    private Class<?> EntityPlayer;
    private Class<?> EnumGameMode;
    private Class<?> CraftPlayer;
    private Class<?> CraftEntity;
    private Class<?> CEntity;
    private Class<?> NBTTagCompound;
    private Class<?> BlockPosition;
    private Class<?> EnumHand;
    private Class<?> CraftItemStack;
    private Class<?> Item;
    private Class<?> IStack;
    public static Class<?> nmsChatSerializer;
    private Class<?> dimensionManager;
    private Class<?> PacketPlayOutAnimation;
    private Class<?> CraftContainer;
    private Class<?> CraftContainers;
    private Class<?> PacketPlayOutOpenWindow;

    public Reflections() {
        this.initialize();
    }

    private void initialize() {
        try {
            this.CraftServerClass = getBukkitClass("CraftServer");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            this.CraftWorldClass = getBukkitClass("CraftWorld");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            this.EntityHuman = this.getMinecraftClass("EntityHuman");
        } catch (Throwable e) {
        }

        try {
            this.PacketPlayOutAnimation = this.getMinecraftClass("PacketPlayOutAnimation");
        } catch (Throwable e) {
        }

        try {
            this.CraftContainer = this.getMinecraftClass("Container");
        } catch (Throwable e) {
        }

        try {
            this.CraftContainers = this.getMinecraftClass("Containers");
        } catch (Throwable e) {
        }

        try {
            this.PacketPlayOutOpenWindow = this.getMinecraftClass("PacketPlayOutOpenWindow");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            this.WorldServerClass = this.getMinecraftClass("WorldServer");
        } catch (Throwable e) {
        }

        try {
            this.dimensionManager = this.getMinecraftClass("DimensionManager");
        } catch (Throwable e) {
        }

        try {
            this.BlockPosition = this.getMinecraftClass("BlockPosition");
        } catch (Throwable e) {
        }

        try {
            this.CraftServer = this.CraftServerClass.cast(Bukkit.getServer());
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            this.MinecraftServerClass = this.getMinecraftClass("MinecraftServer");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            if (!VersionChecker.Version.isCurrentHigher(VersionChecker.Version.v1_8_R2)) {
                nmsChatSerializer = this.getMinecraftClass("ChatSerializer");
            } else {
                nmsChatSerializer = this.getMinecraftClass("IChatBaseComponent$ChatSerializer");
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            this.MinecraftServer = this.CraftServer.getClass().getMethod("getServer").invoke(this.CraftServer);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            this.CraftStatistic = getBukkitClass("CraftStatistic");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            this.Statistics = this.getMinecraftClass("Statistic");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            this.ServerStatisticManager = this.getMinecraftClass("ServerStatisticManager");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            this.PropertyManagerClass = this.getMinecraftClass("PropertyManager");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            this.EnumGameMode = this.getMinecraftClass("EnumGamemode");
        } catch (Throwable e) {
        }

        try {
            this.EntityPlayer = this.getMinecraftClass("EntityPlayer");
        } catch (Throwable e) {
        }

        try {
            this.CraftPlayer = getBukkitClass("entity.CraftPlayer");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            this.CraftEntity = getBukkitClass("entity.CraftEntity");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            this.CEntity = this.getMinecraftClass("Entity");
        } catch (Throwable var6) {
            var6.printStackTrace();
        }

        try {
            this.NBTTagCompound = this.getMinecraftClass("NBTTagCompound");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            this.CraftItemStack = getBukkitClass("inventory.CraftItemStack");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            this.Item = this.getMinecraftClass("Item");
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            this.IStack = this.getMinecraftClass("ItemStack");
            this.EnumHand = this.getMinecraftClass("EnumHand");
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public Object asNMSCopy(ItemStack itemStack) {
        try {
            Method var2 = this.CraftItemStack.getMethod("asNMSCopy", ItemStack.class);
            return var2.invoke(this.CraftItemStack, itemStack);
        } catch (Exception e) {
            return null;
        }
    }

    public Object asBukkitCopy(Object var1) {
        try {
            Method var2 = this.CraftItemStack.getMethod("asBukkitCopy", this.IStack);
            return var2.invoke(this.CraftItemStack, var1);
        } catch (Exception e) {
            return null;
        }
    }

    public void respawn(Player player) {
        try {
            Object var2 = this.MinecraftServerClass.getDeclaredMethod("getServer").invoke((Object)null);
            Object var3 = var2.getClass().getMethod("getPlayerList").invoke(var2);
            if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_13_R1)) {
                Object var4 = this.getDimensionManager(player.getWorld());
                System.out.println(var4);
                var3.getClass().getMethod("moveToWorld", this.getPlayerHandle(player).getClass(), this.dimensionManager, Boolean.TYPE).invoke(var3, this.getPlayerHandle(player), var4, false);
                System.out.println("调用");
            } else {
                var3.getClass().getMethod("moveToWorld", this.getPlayerHandle(player).getClass(), Integer.TYPE, Boolean.TYPE).invoke(var3, this.getPlayerHandle(player), 0, false);
            }
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    public Object getPlayerHandle(Player player) {
        Object getHandle = null;

        try {
            getHandle = player.getClass().getMethod("getHandle").invoke(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getHandle;
    }

    private Object getDimensionManager(World world) {
        Object var2 = null;

        try {
            if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_14_R1)) {
                switch(world.getEnvironment().ordinal()) {
                    case 0:
                        return this.dimensionManager.getField("OVERWORLD").get(this.dimensionManager);
                    case 1:
                        return this.dimensionManager.getField("NETHER").get(this.dimensionManager);
                    case 2:
                        return this.dimensionManager.getField("THE_END").get(this.dimensionManager);
                }
            }

            Object var3 = this.getCraftWorld(world).getClass().getMethod("getHandle").invoke(this.getCraftWorld(world));
            var2 = var3.getClass().getField("dimension").get(var3);
        } catch (IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | NoSuchMethodException | IllegalArgumentException e) {
        }

        return var2;
    }

    private static Class<?> getBukkitClass(String className) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + VersionChecker.Version.getCurrent().toString() + "." + className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    private Object getCraftWorld(World world) {
        return this.CraftWorldClass.cast(world);
    }

    public Class<?> getMinecraftClass(String className) {
        try {
            return Class.forName("net.minecraft.server." + VersionChecker.Version.getCurrent().toString() + "." + className);
        } catch (ClassNotFoundException var3) {
            return null;
        }
    }

    public Object getNmsPlayer(Player player) {
        try {
            Method getHandle = player.getClass().getMethod("getHandle");
            return getHandle.invoke(player);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    public Object getNmsScoreboard(Scoreboard var1) {
        try {
            Method var2 = var1.getClass().getMethod("getHandle");
            return var2.invoke(var1);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    public ItemStack setNbt(ItemStack itemStack, String string, String content) {
        if (itemStack == null) {
            return null;
        } else {
            try {
                Object nmsItem = this.asNMSCopy(itemStack);
                if (nmsItem == null) {
                    nmsItem = this.asNMSCopy(new ItemStack(Material.STONE));
                }

                Method getTag = nmsItem.getClass().getMethod("getTag");
                Object compound = getTag.invoke(nmsItem);
                if (compound == null) {
                    compound = this.NBTTagCompound.newInstance();
                }

                Method setString = compound.getClass().getMethod("setString", String.class, String.class);
                setString.invoke(compound, string, content);
                Method setTag = nmsItem.getClass().getMethod("setTag", this.NBTTagCompound);
                setTag.invoke(nmsItem, compound);
                return (ItemStack)this.asBukkitCopy(nmsItem);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public Object getNBT(ItemStack itemStack, String string) {
        if (itemStack == null) {
            return null;
        } {
            try {
                Object nmsItem = asNMSCopy(itemStack);
                if (nmsItem == null) {
                    return null;
                }

                Method getTag = nmsItem.getClass().getMethod("getTag");
                Object compound = getTag.invoke(nmsItem);
                Class<?> NBTTagCompound = getMinecraftClass("NBTTagCompound");
                if (compound == null) {
                    compound = NBTTagCompound.newInstance();
                }

                Method getString = compound.getClass().getMethod("getString", String.class);
                return getString.invoke(compound, string);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public ItemStack removeNBT(ItemStack itemStack, String string) {
        if (itemStack == null) {
            return null;
        } {
            try {
                Object nmsItem = asNMSCopy(itemStack);
                if (nmsItem == null) {
                    return null;
                }

                Method getTag = nmsItem.getClass().getMethod("getTag");
                Object compound = getTag.invoke(nmsItem);
                Class<?> NBTTagCompound = getMinecraftClass("NBTTagCompound");
                if (compound == null) {
                    compound = NBTTagCompound.newInstance();
                }

                Method remove = compound.getClass().getMethod("remove", String.class);
                remove.invoke(compound, string);
                Method setTag = nmsItem.getClass().getMethod("setTag", this.NBTTagCompound);
                setTag.invoke(nmsItem, compound);
                return (ItemStack)this.asBukkitCopy(nmsItem);
            } catch (Exception e) {
                return null;
            }
        }
    }

    public void sendAllPacket(Object packet) {
        try {
            Iterator var3 = Bukkit.getOnlinePlayers().iterator();

            while(var3.hasNext()) {
                Player var2 = (Player)var3.next();
                Object var4 = this.getNmsPlayer(var2);
                Object var5 = var4.getClass().getField("playerConnection").get(var4);
                var5.getClass().getMethod("sendPacket", this.getClass("{nms}.Packet")).invoke(var5, packet);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void sendListPacket(List<String> list, Object packet) {
        try {
            Iterator var4 = list.iterator();

            while(var4.hasNext()) {
                String var3 = (String)var4.next();
                Object var5 = this.getNmsPlayer(Bukkit.getPlayer(var3));
                Object var6 = var5.getClass().getField("playerConnection").get(var5);
                var6.getClass().getMethod("sendPacket", this.getClass("{nms}.Packet")).invoke(var6, packet);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public Class<?> getClass(String var1) {
        try {
            String var2 = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
            String var3 = var1.replace("{nms}", "net.minecraft.server." + var2).replace("{nm}", "net.minecraft." + var2).replace("{cb}", "org.bukkit.craftbukkit.." + var2);
            return Class.forName(var3);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

}
