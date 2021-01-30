package org.sct.easylib.util.reflectutil;

import org.bukkit.Bukkit;

/**
 * @author icestar
 * @date 2020/2/11 21:00
 */

public class VersionChecker {
    private int resource = 3742;
    public static String s1 = "471830";
    public static String s2 = "3742";
    public static String s3 = "766629119";

    /**
     * @deprecated
     */
    @Deprecated
    public VersionChecker.Version getVersion() {
        return VersionChecker.Version.getCurrent();
    }

    public Integer convertVersion(String var1) {
        var1 = var1.replaceAll("[^\\d.]", "");
        Integer var2 = 0;
        if (var1.contains(".")) {
            String var3 = "";
            String[] var7;
            int var6 = (var7 = var1.split("\\.")).length;

            for (int var5 = 0; var5 < var6; ++var5) {
                String var4 = var7[var5];
                String var8 = var4;
                if (var4.length() == 1) {
                    var8 = "0" + var4;
                }

                var3 = var3 + var8;
            }

            try {
                var2 = Integer.parseInt(var3);
            } catch (Exception var10) {
                var10.printStackTrace();
            }
        } else {
            try {
                var2 = Integer.parseInt(var1);
            } catch (Exception var9) {
                var9.printStackTrace();
            }
        }

        return var2;
    }

    public String deconvertVersion(Integer var1) {
        StringBuilder var2 = new StringBuilder();

        for(String var3 = String.valueOf(var1); var3.length() > 0; var3 = var3.substring(0, var3.length() - 2)) {
            boolean var4 = false;

            try {
                int var7;
                if (var3.length() > 2) {
                    var7 = Integer.parseInt(var3.substring(var3.length() - 2));
                    var2.insert(0, "." + var7);
                } else {
                    var7 = Integer.parseInt(var3);
                    var2.insert(0, var7);
                }
            } catch (Throwable var6) {
            }

            if (var3.length() <= 2) {
                break;
            }
        }

        return var2.toString();
    }

    public static enum Version {
        v1_7_R1,
        v1_7_R2,
        v1_7_R3,
        v1_7_R4,
        v1_8_R1,
        v1_8_R2,
        v1_8_R3,
        v1_9_R1,
        v1_9_R2,
        v1_10_R1,
        v1_11_R1,
        v1_12_R1,
        v1_13_R1,
        v1_13_R2,
        v1_13_R3,
        v1_14_R1,
        v1_14_R2,
        v1_15_R1,
        v1_15_R2,
        v1_16_R1,
        v1_16_R2,
        v1_16_R3,
        v1_17_R1,
        v1_17_R2,
        v1_18_R1,
        v1_18_R2,
        v1_19_R1,
        v1_19_R2,
        v1_20_R1,
        v1_20_R2;

        private Integer value;
        private String shortVersion;
        private static VersionChecker.Version current = null;

        static {
            getCurrent();
        }

        private Version() {
            try {
                this.value = Integer.valueOf(this.name().replaceAll("[^\\d.]", ""));
            } catch (Exception var4) {
            }

            this.shortVersion = this.name().substring(0, this.name().length() - 3);
        }

        public Integer getValue() {
            return this.value;
        }

        public String getShortVersion() {
            return this.shortVersion;
        }

        public String getShortFormated() {
            return this.shortVersion.replace("v", "").replace("_", ".") + ".x";
        }

        public static VersionChecker.Version getCurrent() {
            if (current != null) {
                return current;
            } else {
                String[] versionPath = Bukkit.getServer().getClass().getPackage().getName().split("\\.");
                String currentVersion = versionPath[versionPath.length - 1];
                VersionChecker.Version[] versions;
                int length = (versions = values()).length;

                for (int i = 0; i < length; ++i) {
                    VersionChecker.Version version = versions[i];
                    if (version.name().equalsIgnoreCase(currentVersion)) {
                        current = version;
                        break;
                    }
                }

                return current == null ? v1_13_R2 : current;
            }
        }

        public boolean isLower(VersionChecker.Version var1) {
            return this.getValue() < var1.getValue();
        }

        public boolean isHigher(VersionChecker.Version var1) {
            return this.getValue() > var1.getValue();
        }

        public boolean isEqualOrLower(VersionChecker.Version var1) {
            return this.getValue() <= var1.getValue();
        }

        public boolean isEqualOrHigher(VersionChecker.Version var1) {
            return this.getValue() >= var1.getValue();
        }

        public static boolean isCurrentEqualOrHigher(VersionChecker.Version var0) {
            return current.getValue() >= var0.getValue();
        }

        public static boolean isCurrentHigher(VersionChecker.Version var0) {
            return current.getValue() > var0.getValue();
        }

        public static boolean isCurrentLower(VersionChecker.Version var0) {
            return current.getValue() < var0.getValue();
        }

        public static boolean isCurrentEqualOrLower(VersionChecker.Version var0) {
            return current.getValue() <= var0.getValue();
        }

        public static boolean isCurrentEqual(VersionChecker.Version var0) {
            return current.getValue().equals(var0.getValue());
        }
    }
}
