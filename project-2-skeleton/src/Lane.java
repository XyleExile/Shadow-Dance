import java.util.ArrayList;
import java.util.List;

import bagel.*;

/*
 * I'll be using the full solution given by Project 1
 */

/**
 * The Lane class represents the lanes in the game where notes fall down.
 * It manages different types of notes and their updates.
 * 
 * Attributes:
 * - type: The type of the Lane.
 * - image: The image of the Lane.
 * - notes, holdNotes, speedUpNotes, slowDownNotes, doubleScoreNotes, bombNotes: Lists of different types of notes in the Lane.
 * - relevantKey: The key associated with the Lane.
 * - location: The location of the Lane.
 * - currNoteIndex, currHoldNoteIndex, currSpeedUpIndex, currSlowDownIndex, currDoubleScoreIndex, currBombIndex: Indices of the current notes of each type.
 */
public class Lane {
    private static final int HEIGHT = 384;
    private static final int TARGET_HEIGHT = 657;
    private final String type;
    private final Image image;
    private List<Note> notes = new ArrayList<>();
    private List<HoldNote> holdNotes = new ArrayList<>();
    private List<SpeedUpNote> speedUpNotes = new ArrayList<>();
    private List<SlowDownNote> slowDownNotes = new ArrayList<>();
    private List<DoubleScoreNote> doubleScoreNotes = new ArrayList<>();
    private List<BombNote> bombNotes = new ArrayList<>();
    private Keys relevantKey;
    private final int location;
    private int currNoteIndex = 0;
    private int currHoldNoteIndex = 0;
    private int currSpeedUpIndex = 0;
    private int currSlowDownIndex = 0;
    private int currDoubleScoreIndex = 0;
    private int currBombIndex = 0;
    /**
     * Constructs a Lane with a given direction and location.
     *
     * @param dir the direction of the Lane
     * @param location the location of the Lane
     */
    public Lane(String dir, int location) {
        this.type = dir;
        this.location = location;
        image = new Image("res/lane" + dir + ".png");
        switch (dir) {
            case "Left":
                relevantKey = Keys.LEFT;
                break;
            case "Right":
                relevantKey = Keys.RIGHT;
                break;
            case "Up":
                relevantKey = Keys.UP;
                break;
            case "Down":
                relevantKey = Keys.DOWN;
                break;
            case "Special":
                relevantKey = Keys.SPACE;
                break;
        }
    }
    /**
     * Gets the type of the Lane.
     *
     * @return the type of the Lane
     */
    public String getType() {
        return type;
    }
    /**
     * Updates all the notes in the Lane.
     * It also checks the score for each type of note.
     *
     * @param input the current input (keyboard and mouse state)
     * @param accuracy the current accuracy
     * @return the score for the current frame
     */
    public int update(Input input, Accuracy accuracy) {
        draw();

        for (int i = currNoteIndex; i < notes.size(); i++) {
            notes.get(i).update();
        }

        for (int j = currHoldNoteIndex; j < holdNotes.size(); j++) {
            holdNotes.get(j).update();
        }

        for (int k = currSpeedUpIndex; k < speedUpNotes.size(); k++) {
            speedUpNotes.get(k).update();
        }

        for (int l = currSlowDownIndex; l < slowDownNotes.size(); l++) {
            slowDownNotes.get(l).update();
        }

        for (int m = currDoubleScoreIndex; m < doubleScoreNotes.size(); m++) {
            doubleScoreNotes.get(m).update();
        }

        for (int n = currBombIndex; n < bombNotes.size(); n++) {
            bombNotes.get(n).update();
        }

        if (currNoteIndex < notes.size()) {
            int score = notes.get(currNoteIndex).checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (notes.get(currNoteIndex).isCompleted()) {
                currNoteIndex++;
                return score;
            }
        }

        if (currHoldNoteIndex < holdNotes.size()) {
            int score = holdNotes.get(currHoldNoteIndex).checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (holdNotes.get(currHoldNoteIndex).isCompleted()) {
                currHoldNoteIndex++;
            }
            return score;
        }

        if (currSpeedUpIndex < speedUpNotes.size()) {
            int score = speedUpNotes.get(currSpeedUpIndex).checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (speedUpNotes.get(currSpeedUpIndex).isCompleted()) {
                currSpeedUpIndex++;
                return score;
            }
        }

        if (currSlowDownIndex < slowDownNotes.size()) {
            int score = slowDownNotes.get(currSlowDownIndex).checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (slowDownNotes.get(currSlowDownIndex).isCompleted()) {
                currSlowDownIndex++;
                return score;
            }
        }

        if (currDoubleScoreIndex < doubleScoreNotes.size()) {
            int score = doubleScoreNotes.get(currDoubleScoreIndex).checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (doubleScoreNotes.get(currDoubleScoreIndex).isCompleted()) {
                currDoubleScoreIndex++;
                return score;
            }
        }

        if (currBombIndex < bombNotes.size()) {
            int score = bombNotes.get(currBombIndex).checkScore(input, accuracy, TARGET_HEIGHT, relevantKey);
            if (bombNotes.get(currBombIndex).isCompleted()) {
                currBombIndex++;
            }
            return score;
        }

        return Accuracy.NOT_SCORED;
    }
    /**
     * Adds a Note to the Lane.
     *
     * @param n the Note to be added
     */
    public void addNote(Note n) {
        notes.add(n);
    }
    /**
     * Adds a HoldNote to the Lane.
     *
     * @param hn the HoldNote to be added
     */
    public void addHoldNote(HoldNote hn) {
        holdNotes.add(hn);
    }
    /**
     * Adds a SpeedUpNote to the Lane.
     *
     * @param su the SpeedUpNote to be added
     */
    public void addSpeedUpNote(SpeedUpNote su) {
        speedUpNotes.add(su);
    }
    /**
     * Adds a SlowDownNote to the Lane.
     *
     * @param sd the SlowDownNote to be added
     */
    public void addSlowDownNote(SlowDownNote sd) {
        slowDownNotes.add(sd);
    }
    /**
     * Adds a DoubleScoreNote to the Lane.
     *
     * @param ds the DoubleScoreNote to be added
     */
    public void addDoubleScoreNote(DoubleScoreNote ds) {
        doubleScoreNotes.add(ds);
    }
    /**
     * Adds a BombNote to the Lane.
     *
     * @param b the BombNote to be added
     */
    public void addBombNote(BombNote b) {
        bombNotes.add(b);
    }
    /**
     * Checks if all the notes in the Lane have been pressed or missed.
     *
     * @return true if all the notes have been pressed or missed, false otherwise
     */
    public boolean isFinished() {
        for (Note note : notes) {
            if (!note.isCompleted()) {
                return false;
            }
        }

        for (HoldNote holdNote : holdNotes) {
            if (!holdNote.isCompleted()) {
                return false;
            }
        }

        for (SpeedUpNote speedUp : speedUpNotes) {
            if (!speedUp.isCompleted()) {
                return false;
            }
        }

        for (SlowDownNote slowDown : slowDownNotes) {
            if (!slowDown.isCompleted()) {
                return false;
            }
        }

        for (DoubleScoreNote doubleScore : doubleScoreNotes) {
            if (!doubleScore.isCompleted()) {
                return false;
            }
        }

        for (BombNote bombNote : bombNotes) {
            if (!bombNote.isCompleted()) {
                return false;
            }
        }

        return true;
    }
    /**
     * draws the lane and the notes
     */
    public void draw() {
        image.draw(location, HEIGHT);

        for (int i = currNoteIndex; i < notes.size(); i++) {
            notes.get(i).draw(location);
        }

        for (int j = currHoldNoteIndex; j < holdNotes.size(); j++) {
            holdNotes.get(j).draw(location);
        }

        for (int k = currSpeedUpIndex; k < speedUpNotes.size(); k++) {
            speedUpNotes.get(k).draw(location);
        }

        for (int l = currSlowDownIndex; l < slowDownNotes.size(); l++) {
            slowDownNotes.get(l).draw(location);
        }

        for (int m = currDoubleScoreIndex; m < doubleScoreNotes.size(); m++) {
            doubleScoreNotes.get(m).draw(location);
        }

        for (int n = currBombIndex; n < bombNotes.size(); n++) {
            bombNotes.get(n).draw(location);
        }
    }
    /**
     * Gets the relevant key for the Lane.
     *
     * @return the relevant key for the Lane
     */
    public Keys getRelevantKey() {
        return this.relevantKey;
    }
    /**
     * Gets the list of Notes in the Lane.
     *
     * @return the list of Notes in the Lane
     */
    public List<Note> getNotes() {
        return this.notes;
    }

}
