package moe.quill.feather.old.scoreboard;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardUtils {

    /**
     * Register the given objective to the scoreboard
     *
     * @param name      of the objective
     * @param criteria  to use for this objective
     * @param component of the name for this objective
     * @return the objective
     */
    public static Objective registerObjective(String name, String criteria, Component component) {
        final var scoreboard = getMainScoreboard();

        Objective objective = scoreboard.getObjective(name);
        if (objective != null) {
            objective.unregister();

        }

        objective = scoreboard.registerNewObjective(name, criteria, component);

        return objective;
    }


    /**
     * Register a team with the given name
     *
     * @param name to register this team under
     * @return the team for this
     */
    public static Team registerTeam(String name) {
        final var scoreboard = getMainScoreboard();
        final var team = scoreboard.getTeam(name);
        if (team != null) {
            team.unregister();
        }

        return scoreboard.registerNewTeam(name);
    }

    /**
     * Get the team for this name
     * @param name for this team
     * @return the team
     */
    public static Team getOrRegisterTeam(String name) {
        final var scoreboard = getMainScoreboard();

        var team = scoreboard.getTeam(name);
        if (team == null) {
            team = registerTeam(name);
        }

        return team;
    }

    /**
     * Get the main scoreboard
     *
     * @return the main scoreboard
     */
    public static Scoreboard getMainScoreboard() {
        return Bukkit.getScoreboardManager().getMainScoreboard();
    }
}
