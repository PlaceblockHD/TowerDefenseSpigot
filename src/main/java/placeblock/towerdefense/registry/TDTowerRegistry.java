package placeblock.towerdefense.registry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import placeblock.towerdefense.TDTowerInstance;
import placeblock.towerdefense.TowerDefense;
import placeblock.towerdefense.creators.TDTower;

import java.io.File;
import java.util.HashMap;

public class TDTowerRegistry {

    private final HashMap<String, TDTower> towers = new HashMap<>();

    public TDTowerInstance getInstance(String name, Location loc) {
        if(!towers.containsKey(name)) {
            Bukkit.getLogger().warning("Tried to get Tower " + name + " which doesnt exists");
        }
        return towers.get(name).getInstance(loc);
    }

    public void loadTowers() {
        File towersfile = new File(TowerDefense.getInstance().getDataFolder() + "/towers.yml");
        FileConfiguration towers = YamlConfiguration.loadConfiguration(towersfile);

        for(String key : towers.getKeys(false)) {
            loadTower(key, towers.getConfigurationSection(key));
        }
    }

    private void loadTower(String name, ConfigurationSection section) {
        if(!section.contains("range") || !section.contains("damage") || !section.contains("cooldown")) {
            Bukkit.getLogger().warning("Tower " + name + " could not be loaded! Please check your towers.yml");
            return;
        }
        int range = section.getInt("range");
        double damage = section.getDouble("damage");
        int cooldown = section.getInt("cooldown");
        towers.put(name, new TDTower(range, damage, cooldown));
    }

}
