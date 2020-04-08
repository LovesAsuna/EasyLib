package org.sct.plugincore.util.function.item;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.sct.plugincore.util.reflectutil.Reflections;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * @author LovesAsuna
 * @date 2020/4/7 23:03
 */

public class MaterialUtil {
    private static final Reflections reflections = new Reflections();
    private static final Set<Material> BEDS = reflections.getAllMatchingEnum(Material.class, new String[]{"BED", "BED_BLOCK", "WHITE_BED", "ORANGE_BED", "MAGENTA_BED", "LIGHT_BLUE_BED", "YELLOW_BED", "LIME_BED", "PINK_BED", "GRAY_BED", "LIGHT_GRAY_BED", "CYAN_BED", "PURPLE_BED", "BLUE_BED", "BROWN_BED", "GREEN_BED", "RED_BED", "BLACK_BED"});
    private static final Set<Material> BANNERS = reflections.getAllMatchingEnum(Material.class, new String[]{"BANNER", "WHITE_BANNER", "ORANGE_BANNER", "MAGENTA_BANNER", "LIGHT_BLUE_BANNER", "YELLOW_BANNER", "LIME_BANNER", "PINK_BANNER", "GRAY_BANNER", "LIGHT_GRAY_BANNER", "CYAN_BANNER", "PURPLE_BANNER", "BLUE_BANNER", "BROWN_BANNER", "GREEN_BANNER", "RED_BANNER", "BLACK_BANNER", "SHIELD"});
    private static final Set<Material> FIREWORKS = reflections.getAllMatchingEnum(Material.class, new String[]{"FIREWORK", "FIREWORK_ROCKET", "FIREWORK_CHARGE", "FIREWORK_STAR"});
    private static final Set<Material> LEGACY_SKULLS = reflections.getAllMatchingEnum(Material.class, new String[]{"SKULL", "SKULL_ITEM"});
    private static final Set<Material> LEATHER_ARMOR = reflections.getAllMatchingEnum(Material.class, new String[]{"LEATHER_HELMET", "LEATHER_CHESTPLATE", "LEATHER_LEGGINGS", "LEATHER_BOOTS"});
    private static final Set<Material> MOB_HEADS = reflections.getAllMatchingEnum(Material.class, new String[]{"SKELETON_SKULL", "SKELETON_WALL_SKULL", "WITHER_SKELETON_SKULL", "WITHER_SKELETON_WALL_SKULL", "CREEPER_HEAD", "CREEPER_WALL_HEAD", "ZOMBIE_HEAD", "ZOMBIE_WALL_HEAD", "DRAGON_HEAD", "DRAGON_WALL_HEAD"});
    private static final Set<Material> PLAYER_HEADS = reflections.getAllMatchingEnum(Material.class, new String[]{"PLAYER_HEAD", "PLAYER_WALL_HEAD"});
    private static final Set<Material> POTIONS = reflections.getAllMatchingEnum(Material.class, new String[]{"POTION", "SPLASH_POTION", "LINGERING_POTION", "TIPPED_ARROW"});
    private static final Set<Material> SIGN_POSTS = reflections.getAllMatchingEnum(Material.class, new String[]{"SIGN", "SIGN_POST", "ACACIA_SIGN", "BIRCH_SIGN", "DARK_OAK_SIGN", "JUNGLE_SIGN", "OAK_SIGN", "SPRUCE_SIGN"});
    private static final Set<Material> WALL_SIGNS = reflections.getAllMatchingEnum(Material.class, new String[]{"WALL_SIGN", "ACACIA_WALL_SIGN", "BIRCH_WALL_SIGN", "DARK_OAK_WALL_SIGN", "JUNGLE_WALL_SIGN", "OAK_WALL_SIGN", "SPRUCE_WALL_SIGN"});

    public static boolean isBed(Material material) {
        return BEDS.contains(material);
    }

    public static boolean isBanner(Material material) {
        return BANNERS.contains(material);
    }

    public static boolean isFirework(Material material) {
        return FIREWORKS.contains(material);
    }

    public static boolean isLeatherArmor(Material material) {
        return LEATHER_ARMOR.contains(material);
    }

    public static boolean isMobHead(Material material, int durability) {
        if (MOB_HEADS.contains(material)) {
            return true;
        } else {
            return LEGACY_SKULLS.contains(material) && (durability < 0 || durability != 3);
        }
    }

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

    public static boolean isPlayerHead(Material material, int durability) {
        if (PLAYER_HEADS.contains(material)) {
            return true;
        } else {
            return LEGACY_SKULLS.contains(material) && durability == 3;
        }
    }

    public static boolean isPotion(Material material) {
        return POTIONS.contains(material);
    }

    public static boolean isSignPost(Material material) {
        return SIGN_POSTS.contains(material);
    }

    public static boolean isWallSign(Material material) {
        return WALL_SIGNS.contains(material);
    }

    public static boolean isSign(Material material) {
        return isSignPost(material) || isWallSign(material);
    }

    public static boolean isSkull(Material material) {
        return isPlayerHead(material, -1) || isMobHead(material, -1);
    }

    public static DyeColor getColorOf(Material material) {
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
