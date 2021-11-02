package moe.quill.feather.old.scoreboard;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.HashMap;

public abstract class MHScoreboard {

    private static final LegacyComponentSerializer serializer = LegacyComponentSerializer.legacyAmpersand();

    private final Scoreboard board;
    private final HashMap<Integer, BoardLine> lines;
    private final Objective objective;

    public MHScoreboard(Component title) {
        this.board = Bukkit.getScoreboardManager().getMainScoreboard();
        this.lines = new HashMap<>();

        this.objective = ScoreboardUtils.registerObjective("SB", "dummy", title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public BoardLine addLine(Component component) {
        final var nextOpenLine = 15 - lines.size();
        return setLine(nextOpenLine, component);
    }

    /**
     * Set the line at the given index to use the given text
     *
     * @param index     to set the line at
     * @param component to set this text to
     */
    public BoardLine setLine(int index, Component component) {
        final var existingData = lines.get(index);
        final var teamName = String.valueOf(index);

        if (existingData != null) {
            if (serializer.serialize(existingData.getText()).equals(serializer.serialize(component))) {
                return existingData;
            }
        }

        // Use odl value
        final var entryColorText = ChatColor.values()[index]; //This may be the hackiest thing I've done in my life

        //Set the team data to the existing team data if it exists
        Team team = null;
        if (existingData != null) {
            team = board.getTeam(teamName);
        }
        if (team == null) {
            team = ScoreboardUtils.registerTeam(teamName);
        }

        //Add the entry name to this team if it does not exist
        final var entryName = entryColorText + "" + ChatColor.WHITE;
        if (!team.hasEntry(entryName)) {
            team.addEntry(entryName);
        }

        team.prefix(component);

        final var boardLine = new BoardLine(this, entryName, component, index);
        objective.getScore(entryName).setScore(index);
        lines.put(index, boardLine);

        return boardLine;
    }

    /**
     * Show all the online players this score board
     */
    public void showOnlinePlayers() {
        Bukkit.getOnlinePlayers().forEach((this::showScoreboard));
    }

    /**
     * Show this scoreboard to the given player
     *
     * @param player to show the scoreboard
     */
    public void showScoreboard(Player player) {
        player.setScoreboard(board);
    }
}
