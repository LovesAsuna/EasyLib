package org.sct.easylib.api;

import org.bukkit.Location;
import org.bukkit.World;

public interface LocationAPI {
    Location getSafeDestination(Location loc) throws Exception;

    boolean isBlockAboveAir(World world, int x, int y, int z);

    boolean isBlockUnsafe(World world, int x, int y, int z);

    boolean isBlockDamaging(World world, int x, int y, int z);
}
