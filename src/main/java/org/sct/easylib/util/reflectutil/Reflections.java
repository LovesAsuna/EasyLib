package org.sct.easylib.util.reflectutil;

import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.sct.easylib.EasyLib;
import org.sct.easylib.api.ReflectAPI;
import org.sct.easylib.util.function.container.ServerProperties;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author LovesAsuna
 * @date 2021/1/31 19:01
 */

public class Reflections implements ReflectAPI {
    private Class<?> CraftServerClass;
    private Class<?> CraftWorldClass;
    private Class<?> WorldServerClass;
    private Object CraftServer;
    private Class<?> CraftStatistic;
    private Class<?> Statistics;
    private Class<?> TileEntitySign;
    private Class<?> MinecraftServerClass;
    private Class<?> PropertyManagerClass;
    private Object MinecraftServer;
    private Class<?> ServerStatisticManager;
    private Class<?> MojangsonParser;
    private Class<?> EntityHuman;
    private Class<?> EntityPlayer;
    private Class<?> EnumGameMode;
    private Class<?> CraftPlayer;
    private Class<?> CraftEntity;
    private Class<?> CEntity;
    private Class<?> nbtTagCompound;
    private Class<?> nbtTagList;
    private Class<?> NBTBase;
    private Class<?> EntityLiving;
    private Class<?> BlockPosition;
    private Class<?> EnumHand;
    private Class<?> CraftBeehive;
    private Class<?> TileEntityBeehive;
    private Class<?> CraftItemStack;
    private Class<?> Item;
    private Class<?> IStack;
    public static Class<?> nmsChatSerializer;
    private Class<?> dimensionManager;
    private Class<?> PacketPlayOutAnimation;
    private Class<?> CraftContainer;
    private Class<?> CraftContainers;
    private Class<?> PacketPlayOutOpenWindow;
    private Class<?> PacketPlayOutEntityTeleport;
    private Class<?> PacketPlayOutGameStateChange;
    private Class<?> PacketPlayOutSpawnEntityLiving;
    private Class<?> PacketPlayOutEntityMetadata;
    private Class<?> PacketPlayOutSetSlot;
    private Class<?> DataWatcher;
    private EasyLib plugin;
    private static final Map<Class<?>, Class<?>> CORRESPONDING_TYPES = new HashMap<>();

    public Reflections(EasyLib plugin) {
        this.plugin = plugin;
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
            this.CraftBeehive = getBukkitClass("block.impl.CraftBeehive");
        } catch (Throwable var39) {
        }

        try {
            this.TileEntityBeehive = this.getMinecraftClass("TileEntityBeehive");
        } catch (Throwable var38) {
        }

        try {
            this.MojangsonParser = this.getMinecraftClass("MojangsonParser");
        } catch (Throwable var37) {
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
            this.PacketPlayOutEntityTeleport = this.getMinecraftClass("PacketPlayOutEntityTeleport");
        } catch (Throwable var32) {
            var32.printStackTrace();
        }

        try {
            this.PacketPlayOutGameStateChange = this.getMinecraftClass("PacketPlayOutGameStateChange");
        } catch (Throwable var31) {
            var31.printStackTrace();
        }

        try {
            this.PacketPlayOutSpawnEntityLiving = this.getMinecraftClass("PacketPlayOutSpawnEntityLiving");
        } catch (Throwable var30) {
            var30.printStackTrace();
        }

        try {
            this.DataWatcher = this.getMinecraftClass("DataWatcher");
        } catch (Throwable var29) {
            var29.printStackTrace();
        }

        try {
            this.PacketPlayOutEntityMetadata = this.getMinecraftClass("PacketPlayOutEntityMetadata");
        } catch (Throwable var28) {
            var28.printStackTrace();
        }

        try {
            this.PacketPlayOutSetSlot = this.getMinecraftClass("PacketPlayOutSetSlot");
        } catch (Throwable var27) {
            var27.printStackTrace();
        }

        try {
            this.PacketPlayOutOpenWindow = this.getMinecraftClass("PacketPlayOutOpenWindow");
        } catch (Throwable var26) {
            var26.printStackTrace();
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
            this.TileEntitySign = this.getMinecraftClass("TileEntitySign");
        } catch (Throwable var20) {
            var20.printStackTrace();
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
            this.nbtTagCompound = this.getMinecraftClass("NBTTagCompound");
        } catch (Throwable var8) {
            var8.printStackTrace();
        }

        try {
            this.nbtTagList = this.getMinecraftClass("NBTTagList");
        } catch (Throwable var7) {
            var7.printStackTrace();
        }

        try {
            this.NBTBase = this.getMinecraftClass("NBTBase");
        } catch (Throwable var6) {
            var6.printStackTrace();
        }

        try {
            this.EntityLiving = this.getMinecraftClass("EntityLiving");
        } catch (Throwable var5) {
            var5.printStackTrace();
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

    public String serializeText(String text) {
        try {
            Object o = nmsChatSerializer.getMethod("a", String.class).invoke(null, text);
            return (String) o.getClass().getMethod("e").invoke(o);
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
            return text;
        }
    }

    public Object textToIChatBaseComponent(String text) {
        try {
            Object o = nmsChatSerializer.getMethod("a", String.class).invoke(null, text);
            return o;
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
            return text;
        }
    }

    public void exitBed(Player player) {
        try {
            Object obj = this.CraftPlayer.getMethod("getHandle").invoke(player);
            Constructor constructor = this.PacketPlayOutAnimation.getConstructor(this.CEntity, Integer.TYPE);
            Object var4 = constructor.newInstance(obj, 2);
            Iterator var6 = Bukkit.getOnlinePlayers().iterator();

            while (var6.hasNext()) {
                Player var5 = (Player) var6.next();
                this.sendPlayerPacket(var5, var4);
            }
        } catch (Exception var7) {
            var7.printStackTrace();
        }

    }

    public void setOnGround(Player player, boolean state) {
        try {
            Object var3 = this.CraftPlayer.getMethod("getHandle").invoke(player);
            var3.getClass().getMethod("setOnGround", Boolean.TYPE).invoke(var3, state);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getCurrentTick() {
        try {
            return this.MinecraftServer.getClass().getField("currentTick").getInt(this.CraftServer);
        } catch (IllegalAccessException | NoSuchFieldException | SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
            return (int) (System.currentTimeMillis() / 50L);
        }
    }

    public Object getItemInfo(int id, String fieldName) {
        try {
            Field field = this.getMinecraftClass("Block").getDeclaredField(fieldName);
            field.setAccessible(true);
            Method method;
            Object blockData;
            if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_13_R2)) {
                method = this.getMinecraftClass("Block").getDeclaredMethod("getByCombinedId", Integer.TYPE);
                blockData = method.invoke(this.getMinecraftClass("Block"), id);
                blockData = blockData.getClass().getMethod("getBlock").invoke(blockData);
                return field.get(blockData);
            } else {
                method = this.getMinecraftClass("Block").getDeclaredMethod("getById", Integer.TYPE);
                blockData = method.invoke(this.getMinecraftClass("Block"), id);
                return field.get(blockData);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Class<?> getBukkitClass(String className) {
        try {
            return Class.forName("org.bukkit.craftbukkit." + VersionChecker.Version.getCurrent().toString() + "." + className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    @Override
    public Class<?> getMinecraftClass(String className) {
        try {
            return Class.forName("net.minecraft.server." + VersionChecker.Version.getCurrent().toString() + "." + className);
        } catch (ClassNotFoundException var3) {
            return null;
        }
    }

    public void setServerProperties(ServerProperties var1, Object var2, boolean var3) {
        Method var7;
        if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_14_R1)) {
            try {
                Object var11 = this.MinecraftServer.getClass().getField("propertyManager").get(this.MinecraftServer);
                Object var12 = var11.getClass().getMethod("getProperties").invoke(var11);
                Object var13 = var12.getClass().getField("properties").get(var12);
                var7 = var13.getClass().getDeclaredMethod("setProperty", String.class, String.class);
                var7.invoke(var13, var1.getPath(), String.valueOf(var2));
                if (var3) {
                    var11.getClass().getMethod("save").invoke(var11);
                }
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | InvocationTargetException | NoSuchMethodException | SecurityException var9) {
                var9.printStackTrace();
            }

        } else {
            try {
                Method var4 = this.MinecraftServerClass.getDeclaredMethod("getServer");
                Method var5 = this.MinecraftServerClass.getDeclaredMethod("getPropertyManager");
                Method var6 = this.PropertyManagerClass.getDeclaredMethod("setProperty", String.class, Object.class);
                var7 = this.PropertyManagerClass.getDeclaredMethod("savePropertiesFile");
                Object var8 = var5.invoke(var4.invoke((Object) null));
                var6.invoke(var8, var1.getPath(), var2);
                if (var3) {
                    var7.invoke(var8);
                }
            } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var10) {
                var10.printStackTrace();
            }

        }
    }

    public void changePlayerLimit(int var1) {
        try {
            Object var2 = this.MinecraftServer.getClass().getMethod("getPlayerList").invoke(this.MinecraftServer);
            Field var3 = var2.getClass().getSuperclass().getDeclaredField("maxPlayers");
            var3.setAccessible(true);
            var3.setInt(var2, var1);
        } catch (Throwable var4) {
            var4.printStackTrace();
        }

        this.setServerProperties(ServerProperties.max_players, var1, true);
    }

    @SuppressWarnings("deprecation")
    public void setGameMode(Player var1, GameMode var2) {
        if (var1 != null && var2 != null) {
            if (var1.isOnline()) {
                var1.setGameMode(var2);
            } else {
                Object var3 = this.getPlayerHandle(var1);

                try {
                    Method var4 = var3.getClass().getMethod("a", this.EnumGameMode);
                    Method var5 = this.EnumGameMode.getMethod("getById", Integer.TYPE);
                    Object var6 = this.EnumGameMode.cast(var5.invoke(this.EnumGameMode, var2.getValue()));
                    var4.invoke(var3, var6);
                } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var7) {
                }

            }
        }
    }

    @SuppressWarnings("unchecked")
    public boolean isNbtSimilar(ItemStack itemTarget, ItemStack itemSource) {
        String versionName = VersionChecker.Version.getCurrent().name();

        try {
            Class itemStackClass = Class.forName("net.minecraft.server." + versionName + ".ItemStack");
            Class craftItemStackClass = Class.forName("org.bukkit.craftbukkit." + versionName + ".inventory.CraftItemStack");
            Object var6 = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class).invoke(craftItemStackClass, itemTarget);
            Object var7 = craftItemStackClass.getMethod("asNMSCopy", ItemStack.class).invoke(craftItemStackClass, itemSource);
            Method var8 = var6.getClass().getMethod("equals", itemStackClass, itemStackClass);
            Object var9 = var8.invoke(itemStackClass, var6, var7);
            return (Boolean) var9;
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException | NoSuchMethodException var10) {
            var10.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public void manageEnchantment(Enchantment var1, boolean var2) {
        Field var3;
        boolean var4;
        HashMap var5;
        if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_13_R1)) {
            try {
                var3 = Enchantment.class.getDeclaredField("byKey");
                var4 = var3.isAccessible();
                var3.setAccessible(true);
                var5 = (HashMap) var3.get(this);
                if (var2) {
                    var5.remove(var1.getKey());
                } else {
                    var5.put(var1.getKey(), var1);
                }

                if (!var4) {
                    var3.setAccessible(false);
                }
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException var13) {
                var13.printStackTrace();
            }

            try {
                var3 = Enchantment.class.getDeclaredField("byName");
                var4 = var3.isAccessible();
                var3.setAccessible(true);
                var5 = (HashMap) var3.get(this);
                if (var2) {
                    var5.remove(var1.getName());
                } else {
                    var5.put(var1.getName(), var1);
                }

                if (!var4) {
                    var3.setAccessible(false);
                }
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException var12) {
                var12.printStackTrace();
            }
        } else {
            try {
                var3 = Enchantment.class.getDeclaredField("byId");
                var4 = var3.isAccessible();
                var3.setAccessible(true);
                var5 = (HashMap) var3.get(this);
                Integer var6 = null;

                try {
                    var6 = (Integer) var1.getClass().getMethod("getId").invoke(var1);
                } catch (NoSuchMethodException | InvocationTargetException var10) {
                    var10.printStackTrace();
                }

                Field var7 = Enchantment.class.getDeclaredField("byName");
                boolean var8 = var7.isAccessible();
                var7.setAccessible(true);
                HashMap var9 = (HashMap) var7.get(this);
                if (var2) {
                    var5.remove(var6);
                    var9.remove(var1.getName());
                } else {
                    var5.put(var6, var1);
                    var9.put(var1.getName(), var1);
                }

                if (!var8) {
                    var7.setAccessible(false);
                }

                if (!var4) {
                    var3.setAccessible(false);
                }
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException var11) {
                var11.printStackTrace();
            }
        }

    }

    public void setMotd(String motd) {
        try {
            Method method = this.MinecraftServer.getClass().getMethod("setMotd", String.class);
            method.invoke(this.MinecraftServer, motd);
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
        }

    }

    public GameProfile getProfile(Player player) {
        Object craftPlayer = this.CraftPlayer.cast(player);

        try {
            Method method = this.CraftPlayer.getMethod("getProfile");
            Object o = method.invoke(craftPlayer);
            return (GameProfile) o;
        } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getPing(Player player) {
        if (player == null) {
            return 999;
        } else {
            Object craftPlayer = this.CraftPlayer.cast(player);

            try {
                Object var3 = craftPlayer.getClass().getMethod("getHandle").invoke(craftPlayer);
                Object var4 = var3.getClass().getField("ping").get(var3);
                return (Integer) var4;
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                e.printStackTrace();
                return 999;
            }
        }
    }

    public Long getStatistic(UUID var1, Statistic var2) {
        File var3 = new File(Bukkit.getWorlds().get(0).getName(), "stats");
        if (!var3.exists()) {
            return 0L;
        } else {
            File var4 = new File(var3, var1.toString() + ".json");
            Object var6 = 0L;

            try {
                Constructor var5 = this.ServerStatisticManager.getConstructor(this.MinecraftServerClass, File.class);
                Object var7 = var5.newInstance(this.MinecraftServer, var4);
                Method var8 = var7.getClass().getMethod("a");
                var8.invoke(var7);
                Method var9 = var7.getClass().getMethod("getStatisticValue", this.Statistics);
                Method var10 = this.CraftStatistic.getMethod("getNMSStatistic", Statistic.class);
                Object var11 = var10.invoke(this.CraftStatistic, var2);
                var6 = var9.invoke(var7, var11);
            } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }

            return Long.parseLong(String.valueOf(var6));
        }
    }

    @Override
    public Object getPlayerHandle(Player player) {
        Object getHandle = null;

        try {
            getHandle = player.getClass().getMethod("getHandle").invoke(player);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getHandle;
    }

    private Object getEntityHandle(Entity entity) {
        Object o = null;

        try {
            o = this.CraftEntity.cast(entity).getClass().getMethod("getHandle").invoke(this.CraftEntity.cast(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return o;
    }

    private Object getPlayerConnection(Player player) {
        Object o = null;

        try {
            Object var3 = this.getPlayerHandle(player);
            o = var3.getClass().getField("playerConnection").get(var3);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return o;
    }

    private Object getDimensionManager(World world) {
        Object var2 = null;

        try {
            if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_14_R1)) {
                switch (world.getEnvironment().ordinal()) {
                    case 1:
                        return this.dimensionManager.getField("OVERWORLD").get(this.dimensionManager);
                    case 2:
                        return this.dimensionManager.getField("NETHER").get(this.dimensionManager);
                    case 3:
                        return this.dimensionManager.getField("NETHER").get(this.dimensionManager);
                }
            }

            Object var3 = this.getCraftWorld(world).getClass().getMethod("getHandle").invoke(this.getCraftWorld(world));
            var2 = var3.getClass().getField("dimension").get(var3);
        } catch (IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | NoSuchMethodException | IllegalArgumentException e) {
        }

        return var2;
    }

    public String getServerName() {
        if (VersionChecker.Version.isCurrentEqualOrLower(VersionChecker.Version.v1_13_R2)) {
            Server var6 = Bukkit.getServer();

            try {
                Object var2 = var6.getClass().getMethod("getServerName").invoke(var6);
                return (String) var2;
            } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | IllegalAccessException var4) {
                var4.printStackTrace();
                return Bukkit.getName();
            }
        } else {
            Object var1 = this.getCraftWorld((World) Bukkit.getWorlds().get(0));

            try {
                return (String) var1.getClass().getMethod("getName").invoke(var1);
            } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | IllegalAccessException var5) {
                var5.printStackTrace();
                return Bukkit.getName();
            }
        }
    }

    private Object getCraftWorld(World world) {
        return this.CraftWorldClass.cast(world);
    }

    private Object getWorldServer(World world) {
        Object var2 = this.getCraftWorld(world);

        try {
            return this.WorldServerClass.cast(var2.getClass().getMethod("getHandle").invoke(var2));
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object getBlockPosition(Location location) {
        try {
            Constructor constructor = this.BlockPosition.getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
            return constructor.newInstance(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        } catch (SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object getTileEntityAt(Location var1) {
        Object var2 = this.getCraftWorld(var1.getWorld());

        try {
            if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_13_R2)) {
                try {
                    Object var8 = this.getWorldServer(var1.getWorld());
                    Method var9 = var8.getClass().getMethod("getTileEntity", this.BlockPosition);
                    Object var5 = var9.invoke(var8, this.getBlockPosition(var1));
                    return var5;
                } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }

            Method var3 = var2.getClass().getMethod("getTileEntityAt", Integer.TYPE, Integer.TYPE, Integer.TYPE);
            Object var4 = var3.invoke(var2, var1.getBlockX(), var1.getBlockY(), var1.getBlockZ());
            return var4;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public boolean openSignUI(Player player, Sign sign) {
        String[] var3 = new String[4];
        String[] var4 = new String[4];

        for (int var5 = 0; var5 < 4; ++var5) {
            var3[var5] = sign.getLine(var5);
            sign.setLine(var5, sign.getLine(var5));
            var4[var5] = sign.getLine(var5);
        }

        sign.update();
        if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_16_R2)) {
            player.sendSignChange(sign.getLocation(), var4);
        }

        try {
            Object var21 = this.getTileEntityAt(sign.getLocation());
            if (var21 == null) {
                return false;
            } else {
                Object var22 = this.getPlayerHandle(player);
                Field field = var21.getClass().getDeclaredField("isEditable");
                field.setAccessible(true);
                field.set(var21, true);
                String var8 = "h";
                String var9 = "a";
                switch (VersionChecker.Version.getCurrent().ordinal()) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                        break;
                    case 13:
                    case 14:
                        var8 = "g";
                        break;
                    case 15:
                    default:
                        var8 = "c";
                        var9 = "a";
                        break;
                    case 16:
                    case 17:
                        var8 = "j";
                        var9 = "a";
                        break;
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                        var8 = "c";
                        var9 = "a";
                }

                Field var10 = var21.getClass().getDeclaredField(var8);
                var10.setAccessible(true);
                var10.set(var21, this.EntityHuman.cast(var22));

                try {
                    Method var11 = var21.getClass().getMethod(var9, this.EntityHuman);
                    var11.invoke(var21, this.EntityHuman.cast(var22));
                } catch (Throwable var19) {
                    var19.printStackTrace();
                }

                int var23 = sign.getX();
                int var12 = sign.getY();
                int var13 = sign.getZ();
                String var14 = "d";
                switch (VersionChecker.Version.getCurrent().ordinal()) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                        return false;
                    case 8:
                        var14 = "c";
                        break;
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                        var14 = "d";
                }

                Object var17;
                if (VersionChecker.Version.isCurrentEqualOrLower(VersionChecker.Version.v1_12_R1)) {
                    Class var15 = this.getMinecraftClass("BlockPosition$PooledBlockPosition");
                    Method var16 = var15.getMethod(var14, Double.TYPE, Double.TYPE, Double.TYPE);
                    var17 = var16.invoke(null, var23, var12, var13);
                    Object var18 = this.getMinecraftClass("PacketPlayOutOpenSignEditor").getConstructor(this.getMinecraftClass("BlockPosition")).newInstance(var17);
                    this.sendPlayerPacket(player, var18);
                } else if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_17_R2)) {
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
                        try {
                            Constructor var5 = this.getMinecraftClass("BlockPosition").getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
                            Object var6 = var5.newInstance(var23, var12, var13);
                            Object packet = this.getMinecraftClass("PacketPlayOutOpenSignEditor").getConstructor(this.getMinecraftClass("BlockPosition")).newInstance(var6);
                            this.sendPlayerPacket(player, packet);
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }

                    }, 5L);
                } else {
                    Constructor var24 = this.getMinecraftClass("BlockPosition").getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE);
                    Object var25 = var24.newInstance(var23, var12, var13);
                    var17 = this.getMinecraftClass("PacketPlayOutOpenSignEditor").getConstructor(this.getMinecraftClass("BlockPosition")).newInstance(var25);
                    this.sendPlayerPacket(player, var17);
                }

                return true;
            }
        } catch (Throwable var20) {
            var20.printStackTrace();

            for (int var6 = 0; var6 < 4; ++var6) {
                sign.setLine(var6, var3[var6]);
            }

            sign.update();
            return false;
        }
    }

    public void sendPlayerPacket(Player player, Object o) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object var3 = this.getPlayerConnection(player);
        var3.getClass().getMethod("sendPacket", this.getClass("{nms}.Packet")).invoke(var3, o);
    }

    public String toJson(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        } else {
            Object var2 = this.asNMSCopy(itemStack);

            try {
                Method var3 = this.IStack.getMethod("save", this.nbtTagCompound);
                Object var4 = var3.invoke(var2, this.nbtTagCompound.newInstance());
                return var4.toString();
            } catch (Throwable e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public String getItemMinecraftName(ItemStack itemStack) {
        try {
            Object var2 = this.asNMSCopy(itemStack);
            Object var4;
            if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_13_R1)) {
                Object var10 = var2.getClass().getMethod("getItem").invoke(var2);
                var4 = var10.getClass().getMethod("getName").invoke(var10);
                Class var11 = Class.forName("net.minecraft.server." + VersionChecker.Version.getCurrent() + ".LocaleLanguage");
                Object var12 = var11.getMethod("a").invoke(var11);
                if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_16_R1)) {
                    Method var13 = var12.getClass().getMethod("a", String.class);
                    var13.setAccessible(true);
                    return (String) var13.invoke(var12, (String) var4);
                } else {
                    return (String) var12.getClass().getMethod("a", String.class).invoke(var12, (String) var4);
                }
            } else {
                Field var3 = this.Item.getField("REGISTRY");
                var4 = var3.get(var3);
                Method var5 = var4.getClass().getMethod("b", Object.class);
                var5.setAccessible(true);
                Method var6 = var2.getClass().getMethod("getItem");
                Object var7 = var6.invoke(var2);
                Object var8 = var5.invoke(var4, var7);
                return var8.toString();
            }
        } catch (Throwable e) {
            return null;
        }
    }

    public void upadteItemWithPacket(Player player, ItemStack itemStack, int var3) {
        try {
            Constructor var4 = this.PacketPlayOutSetSlot.getConstructor(Integer.TYPE, Integer.TYPE, this.IStack);
            Object var5 = var4.newInstance(this.getActiveContainerId(player), var3, this.asNMSCopy(itemStack));
            this.sendPlayerPacket(player, var5);
        } catch (Throwable var6) {
            var6.printStackTrace();
        }

    }

    @SuppressWarnings("deprecation")
    public String getItemMinecraftNamePath(ItemStack itemStack) {
        try {
            Object var2 = this.asNMSCopy(itemStack);
            Method var3 = this.Item.getMethod("getById", Integer.TYPE);
            Object var4 = var3.invoke(this.Item, itemStack.getType().getId());
            Method var5 = this.Item.getMethod("j", this.IStack);
            Object var6 = var5.invoke(var4, var2);
            return var6.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public ItemStack setTag(ItemStack itemStack, Object tag) {
        try {
            Object var3 = this.asNMSCopy(itemStack);
            if (var3 == null) {
                return null;
            } else {
                Method method = var3.getClass().getMethod("setTag", this.nbtTagCompound);
                method.invoke(var3, tag);
                return (ItemStack) this.asBukkitCopy(var3);
            }
        } catch (Throwable e) {
            if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_7_R4)) {
                e.printStackTrace();
            }

            return itemStack;
        }
    }

    public Object getNbt(Entity entity) {
        if (entity == null) {
            return null;
        } else {
            try {
                Object var2 = this.nbtTagCompound.newInstance();
                Object var3 = this.getEntityHandle(entity);
                Method var4 = var3.getClass().getMethod("save", this.nbtTagCompound);
                var2 = var4.invoke(var3, var2);
                return var2;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public Object getNbt(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        } else {
            try {
                Object var2 = this.asNMSCopy(itemStack);
                if (var2 == null) {
                    return null;
                } else {
                    Method var3 = var2.getClass().getMethod("getTag");
                    Object var4 = var3.invoke(var2);
                    if (var4 == null) {
                        var4 = this.nbtTagCompound.newInstance();
                    }

                    return var4;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    public Object getNbt(ItemStack itemStack, String string) {
        if (itemStack == null) {
            return null;
        } else {
            try {
                Object var3 = this.getNbt(itemStack);
                if (var3 == null) {
                    return null;
                } else {
                    Method method = var3.getClass().getMethod("getString", String.class);
                    Object var5 = method.invoke(var3, string);
                    return var5;
                }
            } catch (Exception e) {
                return null;
            }
        }
    }

    public Object getNbt(Block block) {
        if (block == null) {
            return false;
        } else {
            try {
                Object var2 = this.getTileEntityAt(block.getLocation());
                String var3 = "d";
                switch (VersionChecker.Version.getCurrent().ordinal()) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                        var3 = "d";
                        break;
                    case 13:
                    case 14:
                        var3 = "aa_";
                        break;
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    default:
                        var3 = "b";
                }

                if (var2 == null) {
                    return null;
                } else {
                    Method var4 = var2.getClass().getMethod(var3);
                    Object var5 = var4.invoke(var2);
                    return var5;
                }
            } catch (Exception var6) {
                var6.printStackTrace();
                return null;
            }
        }
    }

    public void updateTileEntity(Location location, Object newTileEntity) {
        if (newTileEntity != null) {
            try {
                Object tileEntity = this.getTileEntityAt(location);
                String var4 = "load";
                switch (VersionChecker.Version.getCurrent().ordinal()) {
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                        var4 = "a";
                        break;
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                        var4 = "a";
                        break;
                    case 12:
                        var4 = "load";
                        break;
                    case 13:
                    case 14:
                        var4 = "create";
                }

                Method method = tileEntity.getClass().getMethod(var4, this.nbtTagCompound);
                method.invoke(tileEntity, newTileEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object asNMSCopy(ItemStack itemStack) {
        try {
            Method var2 = this.CraftItemStack.getMethod("asNMSCopy", ItemStack.class);
            return var2.invoke(this.CraftItemStack, itemStack);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object asBukkitCopy(Object var1) {
        try {
            Method var2 = this.CraftItemStack.getMethod("asBukkitCopy", this.IStack);
            return var2.invoke(this.CraftItemStack, var1);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void respawn(Player player) {
        try {
            Object var2 = this.MinecraftServerClass.getDeclaredMethod("getServer").invoke(null);
            Object var3 = var2.getClass().getMethod("getPlayerList").invoke(var2);
            if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_16_R1)) {
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(EasyLib.getInstance(), () -> {
                    try {
                        Object var3x = this.getCraftWorld(player.getWorld()).getClass().getMethod("getHandle").invoke(this.getCraftWorld(player.getWorld()));
                        var3.getClass().getMethod("moveToWorld", this.getPlayerHandle(player).getClass(), this.WorldServerClass, Boolean.TYPE, player.getLocation().getClass(), Boolean.TYPE).invoke(var3, this.getPlayerHandle(player), var3x, false, null, false);
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }

                }, 1L);
            } else if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_13_R1)) {
                Object var4 = this.getDimensionManager(player.getWorld());
                var3.getClass().getMethod("moveToWorld", this.getPlayerHandle(player).getClass(), this.dimensionManager, Boolean.TYPE).invoke(var3, this.getPlayerHandle(player), var4, false);
            } else {
                var3.getClass().getMethod("moveToWorld", this.getPlayerHandle(player).getClass(), Integer.TYPE, Boolean.TYPE).invoke(var3, this.getPlayerHandle(player), 0, false);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public Object getCraftServer() {
        return this.CraftServer;
    }

    public void openBook(ItemStack itemStack, Player player) {
        if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_14_R1)) {
            player.openBook(itemStack);
        } else {
            ItemStack var3 = player.getInventory().getItemInMainHand();

            try {
                player.getInventory().setItemInMainHand(itemStack.clone());
                Object var4 = this.CraftPlayer.getMethod("getHandle").invoke(player);
                Object[] var5 = this.EnumHand.getEnumConstants();
                Method var6 = null;
                String var7 = "a";
                switch (VersionChecker.Version.getCurrent().ordinal()) {
                    case 4:
                        break;
                    case 5:
                    case 6:
                    case 7:
                        var7 = "openBook";
                        var6 = this.EntityPlayer.getMethod(var7, this.IStack);
                        break;
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                        var7 = "a";
                        var6 = this.EntityPlayer.getMethod(var7, this.IStack, this.EnumHand);
                        break;
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                    case 18:
                    case 19:
                    case 20:
                    case 21:
                    case 22:
                    case 23:
                    case 24:
                    default:
                        var7 = "openBook";
                        var6 = this.EntityPlayer.getMethod(var7, this.IStack, this.EnumHand);
                }

                if (var6 != null) {
                    Method var8 = this.CraftItemStack.getMethod("asNMSCopy", ItemStack.class);
                    Object var9 = var8.invoke(this.CraftItemStack, itemStack);
                    switch (VersionChecker.Version.getCurrent().ordinal()) {
                        case 5:
                        case 6:
                        case 7:
                            var6.invoke(var4, var9);
                            break;
                        default:
                            var6.invoke(var4, var9, var5[0]);
                    }
                }
            } catch (ReflectiveOperationException e) {
            }

            player.getInventory().setItemInMainHand(var3);
        }
    }

    public ItemStack getItemInOffHand(Player player) {
        return VersionChecker.Version.isCurrentLower(VersionChecker.Version.v1_9_R1) ? null : player.getInventory().getItemInOffHand();
    }

    @Override
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

    @Override
    public Object getNmsPlayer(Player player) {
        try {
            Method getHandle = player.getClass().getMethod("getHandle");
            return getHandle.invoke(player);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    @Override
    public Object getNmsScoreboard(Scoreboard var1) {
        try {
            Method var2 = var1.getClass().getMethod("getHandle");
            return var2.invoke(var1);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return null;
        }
    }

    public Object getFieldValue(Object o, String filedName) {
        try {
            Field var3 = o.getClass().getDeclaredField(filedName);
            var3.setAccessible(true);
            return var3.get(o);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T getFieldValue(Field field, Object o) {
        try {
            return (T) field.get(o);
        } catch (Exception var4) {
            var4.printStackTrace();
            return null;
        }
    }

    public Field getField(Class<?> c, String fieldName) {
        try {
            Field field = c.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field;
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setValue(Object o, String fieldName, Object value) {
        try {
            Field field = o.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(o, value);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendAllPacket(Object packet) {
        try {
            Iterator var3 = Bukkit.getOnlinePlayers().iterator();

            while (var3.hasNext()) {
                Player var2 = (Player) var3.next();
                Object var4 = this.getNmsPlayer(var2);
                Object var5 = var4.getClass().getField("playerConnection").get(var4);
                var5.getClass().getMethod("sendPacket", this.getClass("{nms}.Packet")).invoke(var5, packet);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendListPacket(List<String> list, Object packet) {
        try {
            Iterator var4 = list.iterator();

            while (var4.hasNext()) {
                String var3 = (String) var4.next();
                Object var5 = this.getNmsPlayer(Bukkit.getPlayer(var3));
                Object var6 = var5.getClass().getField("playerConnection").get(var5);
                var6.getClass().getMethod("sendPacket", this.getClass("{nms}.Packet")).invoke(var6, packet);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public Class<?> getPrimitiveType(Class<?> c) {
        return CORRESPONDING_TYPES.containsKey(c) ? CORRESPONDING_TYPES.get(c) : c;
    }

    public Method getMethod(String methodName, Class<?> var2, Class<?>... var3) {
        Class[] var4 = this.toPrimitiveTypeArray(var3);
        Method[] methods;
        int methodNum = (methods = var2.getMethods()).length;

        for (int i = 0; i < methodNum; ++i) {
            Method method = methods[i];
            if (method.getName().equals(methodName) && this.equalsTypeArray(this.toPrimitiveTypeArray(method.getParameterTypes()), var4)) {
                return method;
            }
        }

        return null;
    }

    public boolean equalsTypeArray(Class<?>[] var1, Class<?>[] var2) {
        if (var1.length != var2.length) {
            return false;
        } else {
            for (int var3 = 0; var3 < var1.length; ++var3) {
                if (!var1[var3].equals(var2[var3]) && !var1[var3].isAssignableFrom(var2[var3])) {
                    return false;
                }
            }

            return true;
        }
    }

    public Class<?>[] toPrimitiveTypeArray(Class<?>[] cArray) {
        int length = cArray != null ? cArray.length : 0;
        Class[] classes = new Class[length];

        for (int i = 0; i < length; ++i) {
            if (cArray != null) {
                classes[i] = this.getPrimitiveType(cArray[i]);
            }
        }

        return classes;
    }

    public Object invokeMethod(String var1, Object var2) {
        try {
            return this.getMethod(var1, var2.getClass()).invoke(var2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Object getPlayerField(Player player, String fieldName) {
        try {
            Object playerHandle = player.getClass().getMethod("getHandle").invoke(player);
            return playerHandle.getClass().getField(fieldName).get(playerHandle);
        } catch (Exception | Error e) {
            return null;
        }
    }

    public Integer getActiveContainerId(Player player) {
        try {
            return this.getActiveContainerId(this.CraftPlayer.getMethod("getHandle").invoke(player));
        } catch (IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Integer getActiveContainerId(Object o) {
        try {
            Field field = o.getClass().getField("activeContainer");
            Object var3 = this.CraftContainer.cast(field.get(o));
            Field var4 = var3.getClass().getField("windowId");
            Object var5 = var4.get(var3);
            return (Integer) var5;
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object getContainer(String fieldName) {
        try {
            Field var2 = this.CraftContainers.getDeclaredField(fieldName);
            return var2.get(this.CraftContainers);
        } catch (IllegalAccessException | NoSuchFieldException | SecurityException | IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateInventoryTitle(Player player, String text) {
        if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_16_R1)) {
            if (text.length() > 64) {
                text = text.substring(0, 63) + "~";
            }
        } else if (text.length() > 32) {
            text = text.substring(0, 31) + "~";
        }

        try {
            Object var3;
            if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_14_R1)) {
                var3 = this.CraftPlayer.getMethod("getHandle").invoke(player);
                Object var4 = this.getContainer("GENERIC_9X1");
                switch (player.getOpenInventory().getTopInventory().getSize()) {
                    case 9:
                    default:
                        break;
                    case 18:
                        var4 = this.getContainer("GENERIC_9X2");
                        break;
                    case 27:
                        var4 = this.getContainer("GENERIC_9X3");
                        break;
                    case 36:
                        var4 = this.getContainer("GENERIC_9X4");
                        break;
                    case 45:
                        var4 = this.getContainer("GENERIC_9X5");
                        break;
                    case 54:
                        var4 = this.getContainer("GENERIC_9X6");
                }

                Constructor constructor = this.PacketPlayOutOpenWindow.getConstructor(Integer.TYPE, this.CraftContainers, this.getMinecraftClass("IChatBaseComponent"));
                Object var7 = constructor.newInstance(this.getActiveContainerId(var3), var4, this.textToIChatBaseComponent(text));
                this.sendPlayerPacket(player, var7);
                Field var8 = var3.getClass().getField("activeContainer");
                Object var9 = this.CraftContainer.cast(var8.get(var3));
                Method var10 = var3.getClass().getMethod("updateInventory", this.CraftContainer);
                var10.invoke(var3, var9);
            } else if (VersionChecker.Version.isCurrentEqualOrHigher(VersionChecker.Version.v1_8_R2)) {
                var3 = this.CraftPlayer.getMethod("getHandle").invoke(player);
                Constructor var12 = this.PacketPlayOutOpenWindow.getConstructor(Integer.TYPE, String.class, this.getMinecraftClass("IChatBaseComponent"), Integer.TYPE);
                Object var14 = var12.newInstance(this.getActiveContainerId(var3), "minecraft:chest", this.textToIChatBaseComponent(text), player.getOpenInventory().getTopInventory().getSize());
                this.sendPlayerPacket(player, var14);
                Field var15 = var3.getClass().getField("activeContainer");
                Object var16 = this.CraftContainer.cast(var15.get(var3));
                Method var17 = var3.getClass().getMethod("updateInventory", this.CraftContainer);
                var17.invoke(var3, var16);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public ItemStack HideFlag(ItemStack itemStack, int flag) {
        Object var3 = this.asNMSCopy(itemStack);

        try {
            Method var4 = var3.getClass().getMethod("getTag");
            Object nbtTagCompound = var4.invoke(var3);
            if (nbtTagCompound == null) {
                nbtTagCompound = this.nbtTagCompound.newInstance();
            }

            Method method = nbtTagCompound.getClass().getMethod("setInt", String.class, Integer.TYPE);
            method.invoke(nbtTagCompound, "HideFlags", flag);
            Method var7 = var3.getClass().getMethod("setTag", this.nbtTagCompound);
            var7.invoke(var3, nbtTagCompound);
            return (ItemStack) this.asBukkitCopy(var3);
        } catch (Exception e) {
            return itemStack;
        }
    }

    public void superficialEntityTeleport(Player player, Object entity, Location location) {
        try {
            Object var4 = this.CEntity.cast(entity);
            Method var5 = var4.getClass().getMethod("setPosition", Double.TYPE, Double.TYPE, Double.TYPE);
            var5.invoke(var4, location.getX(), location.getY(), location.getZ());
            Constructor packetConstructor = this.PacketPlayOutEntityTeleport.getConstructor(this.CEntity);
            Object packet = packetConstructor.newInstance(var4);
            this.sendPlayerPacket(player, packet);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    public void spawnInEntityData(Player player, Entity entity) {
        Object var5;
        Object var6;
        try {
            Constructor packetConstructor = this.PacketPlayOutSpawnEntityLiving.getConstructor(this.EntityLiving);
            Object var4 = this.CraftEntity.cast(entity);
            var5 = this.CraftEntity.getMethod("getHandle").invoke(var4);
            if (this.EntityLiving.isInstance(var5)) {
                var6 = this.EntityLiving.cast(var5);
                Object var7 = packetConstructor.newInstance(var6);
                this.sendPlayerPacket(player, var7);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }

        try {
            Object var11 = this.CraftEntity.cast(entity);
            int var12 = (Integer) this.CraftEntity.getMethod("getEntityId").invoke(var11);
            var5 = this.CraftEntity.getMethod("getHandle").invoke(var11);
            var6 = var5.getClass().getMethod("getDataWatcher").invoke(var5);
            Constructor var13 = this.PacketPlayOutEntityMetadata.getConstructor(Integer.TYPE, this.DataWatcher, Boolean.TYPE);
            Object var8 = var13.newInstance(var12, var6, true);
            this.sendPlayerPacket(player, var8);
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
                try {
                    this.sendPlayerPacket(player, var8);
                } catch (Throwable var4) {
                    var4.printStackTrace();
                }

            }, 20L);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Enum> Set<T> getAllMatchingEnum(Class<T> enumClass, String... names) {
        Set<T> set = Sets.newHashSet();
        String[] var3 = names;
        int length = names.length;

        for (int i = 0; i < length; i++) {
            String name = var3[i];

            try {
                Field enumField = enumClass.getDeclaredField(name);
                if (enumField.isEnumConstant()) {
                    set.add((T) enumField.get(null));
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
            }
        }

        return set;
    }

    @SuppressWarnings("deprecation")
    public ItemStack getItemInMainHand(Player player) {
        return VersionChecker.Version.isCurrentHigher(VersionChecker.Version.v1_8_R3) ? player.getInventory().getItemInMainHand() : player.getItemInHand();
    }

    @SuppressWarnings("deprecation")
    public void setItemInMainHand(Player player, ItemStack itemStack) {
        if (VersionChecker.Version.isCurrentHigher(VersionChecker.Version.v1_8_R3)) {
            player.getInventory().setItemInMainHand(itemStack);
        } else {
            player.setItemInHand(itemStack);
        }

    }

    public ItemStack tryToMakeShulkerBox(ItemStack itemStack) {
        if (itemStack == null) {
            return null;
        } else if (VersionChecker.Version.isCurrentEqualOrLower(VersionChecker.Version.v1_10_R1)) {
            return itemStack;
        } else {
            Object var2 = this.getNbt(itemStack);

            try {
                Object var3 = var2.getClass().getMethod("getCompound", String.class).invoke(var2, "BlockEntityTag");
                var3.getClass().getMethod("set", String.class, this.NBTBase).invoke(var3, "Items", this.nbtTagList.newInstance());
                var2.getClass().getMethod("set", String.class, this.NBTBase).invoke(var2, "BlockEntityTag", var3);
            } catch (Throwable e) {
                e.printStackTrace();
            }

            return this.setTag(itemStack, var2);
        }
    }

    public ItemStack modifyItemStack(ItemStack itemStack, String var2) {
        Object var3 = this.asNMSCopy(itemStack);

        try {
            Object var4 = this.MojangsonParser.getMethod("parse", String.class).invoke(this.MojangsonParser, var2);
            var3.getClass().getMethod("setTag", this.nbtTagCompound).invoke(var3, var4);
            Object var5 = this.CraftItemStack.getMethod("getItemMeta", this.IStack).invoke(this.CraftItemStack, var3);
            itemStack.setItemMeta((ItemMeta) var5);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        return itemStack;
    }

}
