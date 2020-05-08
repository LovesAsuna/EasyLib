package org.sct.easylib.util.function.player;

import com.google.common.collect.Sets;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.sct.easylib.api.MaterialAPI;
import org.sct.easylib.api.ReflectAPI;
import org.sct.easylib.util.function.item.MaterialUtil;
import org.sct.easylib.util.reflectutil.Reflections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author LovesAsuna
 * @date 2020/4/7 22:32
 */

@SuppressWarnings("deprecation")
public class LocationUtil implements org.sct.easylib.api.LocationAPI {
    private static Set<Material> WATER_TYPES;
    private static Set<Material> HOLLOW_MATERIALS;
    private static Set<Material> TRANSPARENT_MATERIALS;
    private static MaterialAPI materialAPI;
    private static ReflectAPI reflectAPI;
    private static LocationUtil.Vector3D[] VOLUME;

    static {
        HOLLOW_MATERIALS = Sets.newHashSet();
        TRANSPARENT_MATERIALS = Sets.newHashSet();
        materialAPI = new MaterialUtil();
        reflectAPI = new Reflections();
        WATER_TYPES = reflectAPI.getAllMatchingEnum(Material.class, new String[]{"WATER", "FLOWING_WATER"});
        ;
        Material[] var0 = Material.values();
        int x = var0.length;

        int y;
        for (y = 0; y < x; ++y) {
            Material mat = var0[y];
            if (mat.isTransparent()) {
                HOLLOW_MATERIALS.add(mat);
            }
        }

        TRANSPARENT_MATERIALS.addAll(HOLLOW_MATERIALS);
        TRANSPARENT_MATERIALS.addAll(WATER_TYPES);
        List<LocationUtil.Vector3D> pos = new ArrayList<>();

        for (x = -3; x <= 3; ++x) {
            for (y = -3; y <= 3; ++y) {
                for (int z = -3; z <= 3; ++z) {
                    pos.add(new LocationUtil.Vector3D(x, y, z));
                }
            }
        }

        pos.sort(Comparator.comparingInt((a) -> {
            return a.x * a.x + a.y * a.y + a.z * a.z;
        }));
        VOLUME = pos.toArray(new LocationUtil.Vector3D[0]);
    }

    public static class Vector3D {
        public int x;
        public int y;
        public int z;

        Vector3D(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    @Override
    public Location getSafeDestination(Location loc) throws Exception {
        if (loc != null && loc.getWorld() != null) {
            World world = loc.getWorld();
            int x = loc.getBlockX();
            int y = (int) Math.round(loc.getY());
            int z = loc.getBlockZ();
            int origX = x;
            int origY = y;
            int origZ = z;

            while (isBlockAboveAir(world, x, y, z)) {
                --y;
                if (y < 0) {
                    y = origY;
                    break;
                }
            }

            if (isBlockUnsafe(world, x, y, z)) {
                x = Math.round(loc.getX()) == (long) x ? x - 1 : x + 1;
                z = Math.round(loc.getZ()) == (long) z ? z - 1 : z + 1;
            }

            for (int i = 0; isBlockUnsafe(world, x, y, z); z = origZ + VOLUME[i].z) {
                ++i;
                if (i >= VOLUME.length) {
                    x = origX;
                    y = origY + 3;
                    z = origZ;
                    break;
                }

                x = origX + VOLUME[i].x;
                y = origY + VOLUME[i].y;
            }

            while (isBlockUnsafe(world, x, y, z)) {
                ++y;
                if (y >= world.getMaxHeight()) {
                    ++x;
                    break;
                }
            }

            while (isBlockUnsafe(world, x, y, z)) {
                --y;
                if (y <= 1) {
                    ++x;
                    y = world.getHighestBlockYAt(x, z);
                    if (x - 48 > loc.getBlockX()) {
                        throw new Exception("holeInFloor");
                    }
                }
            }

            return new Location(world, (double) x + 0.5D, (double) y, (double) z + 0.5D, loc.getYaw(), loc.getPitch());
        } else {
            throw new Exception("destinationNotSet");
        }
    }

    @Override
    public boolean isBlockAboveAir(World world, int x, int y, int z) {
        return y > world.getMaxHeight() || HOLLOW_MATERIALS.contains(world.getBlockAt(x, y - 1, z).getType());
    }

    @Override
    public boolean isBlockUnsafe(World world, int x, int y, int z) {
        return isBlockDamaging(world, x, y, z) || isBlockAboveAir(world, x, y, z);
    }

    @Override
    public boolean isBlockDamaging(World world, int x, int y, int z) {
        Block below = world.getBlockAt(x, y - 1, z);
        switch (below.getType()) {
            case LAVA:
            case FIRE:
                return true;
            default:
                if (materialAPI.isBed(below.getType())) {
                    return true;
                } else {
                    try {
                        if (below.getType() == Material.valueOf("FLOWING_LAVA")) {
                            return true;
                        }
                    } catch (Exception var6) {
                    }

                    Material PORTAL = materialAPI.getMaterial(new String[]{"NETHER_PORTAL", "PORTAL"});
                    if (world.getBlockAt(x, y, z).getType() == PORTAL) {
                        return true;
                    } else {
                        return !HOLLOW_MATERIALS.contains(world.getBlockAt(x, y, z).getType()) || !HOLLOW_MATERIALS.contains(world.getBlockAt(x, y + 1, z).getType());
                    }
                }
        }
    }
}
