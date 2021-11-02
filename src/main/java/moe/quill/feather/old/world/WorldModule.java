package moe.quill.feather.old.world;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;

public class WorldModule {

    private final World world;

    private final String worldName;

    public WorldModule(Plugin plugin, String worldName) {
        this.world = Bukkit.getWorld(worldName);
        this.worldName = worldName;

        if (world == null) {
            Bukkit.getLogger().severe("Could not find required world %s, Disabling plugin".formatted(worldName));
            Bukkit.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        world.setAutoSave(false);
    }


    public void disable() {
        Bukkit.getOnlinePlayers().forEach(player -> player.kick(
                Component.text("Server restarting").color(NamedTextColor.RED)
        ));

        try {
            Bukkit.unloadWorld(worldName, true);
        } catch (Exception e) {
            e.printStackTrace();
        }


        final var worldsPath = Bukkit.getServer().getWorldContainer().toPath();
        Bukkit.getLogger().info(worldsPath.toString());
        final var worldPath = Path.of(worldsPath.toString(), worldName);
        final var rawWorldPath = Path.of(worldsPath.toString(), (worldName + "_raw"));

        Bukkit.getLogger().info("Attempting to copy world files |  %s => %s".formatted(rawWorldPath, worldPath));

        try {
            delete(worldPath);
            copyDir(rawWorldPath, worldPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove the
     *
     * @param source the directory to remove
     */
    public void delete(Path source) throws IOException {
        Files.walk(source)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }


    public void copyDir(Path from, Path to) throws IOException {
        Files.walk(from).forEach(file -> {
            final var destination = Paths.get(to.toString(), file.toString()
                    .substring(from.toString().length()));
            try {
                Files.copy(file, destination, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public World getWorld() {
        return world;
    }

    public <T> void setGameRule(@NotNull GameRule<T> rule, @NotNull T newValue) {
        getWorld().setGameRule(rule, newValue);
    }

    public void setDifficulty(Difficulty difficulty) {
        getWorld().setDifficulty(difficulty);
    }

    public void setPVP(boolean pvp) {
        getWorld().setPVP(pvp);
    }
}
