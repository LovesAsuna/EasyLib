package org.sct.easylib.util.function.item;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.sct.easylib.api.ReflectAPI;
import org.sct.easylib.util.reflectutil.Reflections;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author LovesAsuna
 * @date 2020/4/7 23:03
 */

public class MaterialUtil implements org.sct.easylib.api.MaterialAPI {
    private static final ReflectAPI REFLECT_API = new Reflections();
    private static final Set<Material> BEDS = REFLECT_API.getAllMatchingEnum(Material.class, new String[]{"BED", "BED_BLOCK", "WHITE_BED", "ORANGE_BED", "MAGENTA_BED", "LIGHT_BLUE_BED", "YELLOW_BED", "LIME_BED", "PINK_BED", "GRAY_BED", "LIGHT_GRAY_BED", "CYAN_BED", "PURPLE_BED", "BLUE_BED", "BROWN_BED", "GREEN_BED", "RED_BED", "BLACK_BED"});
    private static final Set<Material> BANNERS = REFLECT_API.getAllMatchingEnum(Material.class, new String[]{"BANNER", "WHITE_BANNER", "ORANGE_BANNER", "MAGENTA_BANNER", "LIGHT_BLUE_BANNER", "YELLOW_BANNER", "LIME_BANNER", "PINK_BANNER", "GRAY_BANNER", "LIGHT_GRAY_BANNER", "CYAN_BANNER", "PURPLE_BANNER", "BLUE_BANNER", "BROWN_BANNER", "GREEN_BANNER", "RED_BANNER", "BLACK_BANNER", "SHIELD"});
    private static final Set<Material> FIREWORKS = REFLECT_API.getAllMatchingEnum(Material.class, new String[]{"FIREWORK", "FIREWORK_ROCKET", "FIREWORK_CHARGE", "FIREWORK_STAR"});
    private static final Set<Material> LEGACY_SKULLS = REFLECT_API.getAllMatchingEnum(Material.class, new String[]{"SKULL", "SKULL_ITEM"});
    private static final Set<Material> LEATHER_ARMOR = REFLECT_API.getAllMatchingEnum(Material.class, new String[]{"LEATHER_HELMET", "LEATHER_CHESTPLATE", "LEATHER_LEGGINGS", "LEATHER_BOOTS"});
    private static final Set<Material> MOB_HEADS = REFLECT_API.getAllMatchingEnum(Material.class, new String[]{"SKELETON_SKULL", "SKELETON_WALL_SKULL", "WITHER_SKELETON_SKULL", "WITHER_SKELETON_WALL_SKULL", "CREEPER_HEAD", "CREEPER_WALL_HEAD", "ZOMBIE_HEAD", "ZOMBIE_WALL_HEAD", "DRAGON_HEAD", "DRAGON_WALL_HEAD"});
    private static final Set<Material> PLAYER_HEADS = REFLECT_API.getAllMatchingEnum(Material.class, new String[]{"PLAYER_HEAD", "PLAYER_WALL_HEAD"});
    private static final Set<Material> POTIONS = REFLECT_API.getAllMatchingEnum(Material.class, new String[]{"POTION", "SPLASH_POTION", "LINGERING_POTION", "TIPPED_ARROW"});
    private static final Set<Material> SIGN_POSTS = REFLECT_API.getAllMatchingEnum(Material.class, new String[]{"SIGN", "SIGN_POST", "ACACIA_SIGN", "BIRCH_SIGN", "DARK_OAK_SIGN", "JUNGLE_SIGN", "OAK_SIGN", "SPRUCE_SIGN"});
    private static final Set<Material> WALL_SIGNS = REFLECT_API.getAllMatchingEnum(Material.class, new String[]{"WALL_SIGN", "ACACIA_WALL_SIGN", "BIRCH_WALL_SIGN", "DARK_OAK_WALL_SIGN", "JUNGLE_WALL_SIGN", "OAK_WALL_SIGN", "SPRUCE_WALL_SIGN"});

    @Override
    public boolean isBed(Material material) {
        return BEDS.contains(material);
    }

    @Override
    public boolean isBanner(Material material) {
        return BANNERS.contains(material);
    }

    @Override
    public boolean isFirework(Material material) {
        return FIREWORKS.contains(material);
    }

    @Override
    public boolean isLeatherArmor(Material material) {
        return LEATHER_ARMOR.contains(material);
    }

    @Override
    public boolean isMobHead(Material material, int durability) {
        if (MOB_HEADS.contains(material)) {
            return true;
        } else {
            return LEGACY_SKULLS.contains(material) && (durability < 0 || durability != 3);
        }
    }

    @Override
    public Material getMaterial(String... materialNames) {
        String[] var2 = materialNames;
        int length = materialNames.length;

        for (int i = 0; i < length; i++) {
            String name = var2[i];

            try {
                Field enumField = Material.class.getDeclaredField(name);
                if (enumField.isEnumConstant()) {
                    return (Material) enumField.get(null);
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
            }
        }

        return null;
    }

    @Override
    public  boolean isPlayerHead(Material material, int durability) {
        if (PLAYER_HEADS.contains(material)) {
            return true;
        } else {
            return LEGACY_SKULLS.contains(material) && durability == 3;
        }
    }

    @Override
    public boolean isPotion(Material material) {
        return POTIONS.contains(material);
    }

    @Override
    public boolean isSignPost(Material material) {
        return SIGN_POSTS.contains(material);
    }

    @Override
    public boolean isWallSign(Material material) {
        return WALL_SIGNS.contains(material);
    }

    @Override
    public boolean isSign(Material material) {
        return isSignPost(material) || isWallSign(material);
    }

    @Override
    public boolean isSkull(Material material) {
        return isPlayerHead(material, -1) || isMobHead(material, -1);
    }

    @Override
    public DyeColor getColorOf(Material material) {
        DyeColor[] var1 = DyeColor.values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            DyeColor color = var1[var3];
            if (material.toString().contains(color.name())) {
                return color;
            }
        }

        return DyeColor.WHITE;
    }
}
