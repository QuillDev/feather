package moe.quill.feather.old.scoreboard;

import net.kyori.adventure.text.Component;

public class BoardLine {

    private final MHScoreboard scoreboard;
    private final Component text;
    private final String entryName;
    private final int lineIndex;

    public BoardLine(MHScoreboard scoreboard, String entryName, Component component, int lineIndex) {
        this.entryName = entryName;
        this.text = component;
        this.lineIndex = lineIndex;
        this.scoreboard = scoreboard;
    }

    /**
     * Set the text of this board line
     *
     * @param component to use for setting the text
     */
    public void setText(Component component) {
        scoreboard.setLine(lineIndex, component);
    }

    public Component getText() {
        return text;
    }

    public String getEntryName() {
        return entryName;
    }

    public int getLineIndex() {
        return lineIndex;
    }
}
