package org.sct.easylib.util.function.player;

import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.InventoryHolder;

/**
 * 自定义容器
 * @author LovesAsuna
 */
public interface CustomInventoryHolder extends InventoryHolder {
    /**
     * 运行逻辑
     * @param event 与背包有关的事件，请自己转换
     **/
    void run(InventoryEvent event);
}
