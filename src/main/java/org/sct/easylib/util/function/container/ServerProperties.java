package org.sct.easylib.util.function.container;

/**
 * @author LovesAsuna
 **/
public enum ServerProperties {
    max_players,
    motd,
    rate_limit;

    public String getPath() {
        return this.name().replace("_", "-");
    }
}
