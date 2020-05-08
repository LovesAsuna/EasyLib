package org.sct.easylib.api;

import org.bukkit.DyeColor;
import org.bukkit.Material;

public interface MaterialAPI {
    boolean isBed(Material material);

    boolean isBanner(Material material);

    boolean isFirework(Material material);

    boolean isLeatherArmor(Material material);

    boolean isMobHead(Material material, int durability);

    Material getMaterial(String... materialNames);

    boolean isPlayerHead(Material material, int durability);

    boolean isPotion(Material material);

    boolean isSignPost(Material material);

    boolean isWallSign(Material material);

    boolean isSign(Material material);

    boolean isSkull(Material material);

    DyeColor getColorOf(Material material);
}
