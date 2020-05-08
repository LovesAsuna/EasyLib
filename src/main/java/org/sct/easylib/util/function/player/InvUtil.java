package org.sct.easylib.util.function.player;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.sct.easylib.EasyLib;
import org.sct.easylib.EasyLibAPI;
import org.sct.easylib.api.DataBaseManager;
import org.sct.easylib.util.function.stack.StackTrace;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author LovesAsuna
 * @date 2020/3/3 13:22
 */

public class InvUtil {
    private static DataBaseManager dataBaseManager;
    
    static {
        dataBaseManager = EasyLib.getEasyLibAPI().getDataBaseManager(EasyLibAPI.dataBaseType.SQLITE, "jdbc:sqlite:" + EasyLib.getInstance().getDataFolder().getPath() + File.separator + "data.db");
    }

    public static boolean store(Player player) throws IOException {
        Inventory inv = player.getInventory();
        if (!dataBaseManager.checkTable(getUUID(player))) {
            dataBaseManager.createTable(getUUID(player), "CREATE TABLE " + getUUID(player) + "(" +
                    "ID INT PRIMARY KEY NOT NULL," +
                    "ITEM TEXT);");
            for (int i = 0; i < inv.getSize(); i++) {
                ItemStack itemStack = inv.getItem(i);
                int id = i;
                String item = EasyLib.getEasyLibAPI().getSerializerAPI().singleObjectToString(itemStack);
                try {
                    dataBaseManager.store(getUUID(player), id, item);
                } catch (SQLException e) {
                    StackTrace.printStackTrace(e);
                }
            }
            return true;
        } else {
            for (int i = 0; i < inv.getSize(); i++) {
                ItemStack itemStack = inv.getItem(i);
                int id = i;
                String item = EasyLib.getEasyLibAPI().getSerializerAPI().singleObjectToString(itemStack);
                try {
                    dataBaseManager.update(getUUID(player), id, item);
                } catch (SQLException e) {
                    StackTrace.printStackTrace(e);
                }
            }
            return true;
        }
    }

    public static boolean saveInv(Player player) {
        try {
            Inventory inv = player.getInventory();
            ResultSet resultSet =  dataBaseManager.read(getUUID(player));
            resultSet.beforeFirst();
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String string = resultSet.getString("ITEM");
                ItemStack itemStack = null;
                if (!"null".equalsIgnoreCase(string)) {
                    itemStack = EasyLib.getEasyLibAPI().getSerializerAPI().singleObjectFromString(string, ItemStack.class);
                    inv.setItem(id, itemStack);
                }
                inv.setItem(id, itemStack);
            }
            return true;
        } catch (IOException | SQLException e) {
            return false;
        }
    }
    
    private static String getUUID(Player player) {
        return "uuid" + player.getUniqueId().toString().replace("-", "");
    }
}
