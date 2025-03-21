import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import bagel.*;
/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2023
 * Please enter your name below
 * Ethan Tien Yu Au & 1367157
 */

/*
 * I'll be using the full solution given by Project 1
 */
/**
 * The ShadowDance class represents the main game.
 * It manages the game state, including the lanes, notes, enemies, and the guardian.
 * It also handles user input and updates the game state accordingly.
 * 
 * Attributes:
 * - WINDOW_WIDTH, WINDOW_HEIGHT: The width and height of the game window.
 * - GAME_TITLE: The title of the game.
 * - BACKGROUND_IMAGE: The background image of the game.
 * - currentLevelCsv: The CSV file path of the current level.
 * - CSV_FILE_1, CSV_FILE_2, CSV_FILE_3: The CSV file paths of the levels.
 * - FONT_FILE: The font file path.
 * - TITLE_X, TITLE_Y, INS_X_OFFSET, INS_Y_OFFSET, LEVEL_SPACING, SCORE_LOCATION: Constants for positioning elements on the screen.
 * - TITLE_FONT, INSTRUCTION_FONT, SCORE_FONT: The fonts used in the game.
 * - LEVEL_SELECT, LEVEL_NUMBER, CLEAR_MESSAGE, TRY_AGAIN_MESSAGE, INSTRUCTION_END_SCREEN: Strings used in the game.
 * - CLEAR_SCORE_1, CLEAR_SCORE_2, CLEAR_SCORE_3: The scores needed to clear each level.
 * - WIN_LOSS_MESSAGE_Y, INSTRUCTION_END_Y: The y-coordinates for positioning elements on the screen.
 * - accuracy: The Accuracy object for the game.
 * - lanes, specialLanes: The lanes in the game.
 * - enemies: The enemies in the game.
 * - guardian: The Guardian object for the game.
 * - ENEMY_CREATION_FRAME: The frame when a new enemy is created.
 * - score: The current score.
 * - scoreMultiplier: The current score multiplier.
 * - currFrame: The current frame.
 * - started, finished, paused: Booleans indicating the game state.
 */
public class ShadowDance extends AbstractGame  {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");
    private String currentLevelCsv;
    private final static String CSV_FILE_1 = "res/test1.csv";
    private final static String CSV_FILE_2 = "res/test2.csv";
    private final static String CSV_FILE_3 = "res/test3.csv";
    public final static String FONT_FILE = "res/FSO8BITR.TTF";
    private final static int TITLE_X = 220;
    private final static int TITLE_Y = 250;
    private final static int INS_X_OFFSET = 100;
    private final static int INS_Y_OFFSET = 190;
    private final static int LEVEL_SPACING = 70;
    private final static int SCORE_LOCATION = 35;
    private final Font TITLE_FONT = new Font(FONT_FILE, 64);
    private final Font INSTRUCTION_FONT = new Font(FONT_FILE, 24);
    private final Font SCORE_FONT = new Font(FONT_FILE, 30);
    private static final String LEVEL_SELECT = "SELECT LEVELS WITH\nNUMBER KEYS";
    private static final String LEVEL_NUMBER = "     1     2     3     ";
    private static final int CLEAR_SCORE_1 = 150;
    private static final int CLEAR_SCORE_2 = 400;
    private static final int CLEAR_SCORE_3 = 350;
    private static final String CLEAR_MESSAGE = "CLEAR!";
    private static final String TRY_AGAIN_MESSAGE = "TRY AGAIN";
    private static final String INSTRUCTION_END_SCREEN = "PRESS SPACE TO RETURN TO LEVEL SELECTION";
    private static final int WIN_LOSS_MESSAGE_Y = 300;
    private static final int INSTRUCTION_END_Y = 500;
    private final Accuracy accuracy = new Accuracy();
    private final ArrayList<Lane> lanes = new ArrayList<>();
    private final ArrayList<Lane> specialLanes = new ArrayList<>();
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private Guardian guardian = new Guardian();
    private static final int ENEMY_CREATION_FRAME = 600;
    private int score = 0;
    private static double scoreMultiplier = 1.0;
    private static int currFrame = 0;
    private boolean started = false;
    private boolean finished = false;
    private boolean paused = false;
    /**
     * Constructs a ShadowDance game with a window of a given width and height and a given title.
     */
    public ShadowDance(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
    }
    /**
     * The entry point for the program.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.run();
    }

    private void initializeGame(String csvFilePath) {
        // Read the CSV
        readCsv(csvFilePath);

        started = true;
        finished = false;
        paused = false;
    }
    /**
     * Initializes the game with a given CSV file path.
     *
     * @param csvFilePath the path to the CSV file
     */
    private void readCsv(String csvFilePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String textRead;
            while ((textRead = br.readLine()) != null) {
                String[] splitText = textRead.split(",");

                if (splitText[0].equals("Lane")) {
                    // reading lanes
                    String laneType = splitText[1];
                    int pos = Integer.parseInt(splitText[2]);
                    Lane lane = new Lane(laneType, pos);

                    if (laneType.equals("Special")) {
                        specialLanes.add(lane);
                    } else {
                        lanes.add(lane);
                    }
                } else {
                    // reading notes
                    String dir = splitText[0];
                    Lane lane = null;
                    // Use enhanced for loop to iterate over ArrayList
                    for (Lane l : lanes) {
                        if (l.getType().equals(dir)) {
                            lane = l;
                        }
                    }

                    for (Lane l : specialLanes) {
                        if (l.getType().equals(dir)) {
                            lane = l;
                        }
                    }

                    if (lane != null) {
                        switch (splitText[1]) {
                            case "Normal":
                                Note note = new Note(dir, Integer.parseInt(splitText[2]));
                                lane.addNote(note);
                                break;
                            case "Hold":
                                HoldNote holdNote = new HoldNote(dir, Integer.parseInt(splitText[2]));
                                lane.addHoldNote(holdNote);
                                break;
                            case "SpeedUp":
                                SpeedUpNote speedUpNote = new SpeedUpNote("SpeedUp", Integer.parseInt(splitText[2]));
                                lane.addSpeedUpNote(speedUpNote);
                                break;
                            case "SlowDown":
                                SlowDownNote slowDownNote = new SlowDownNote("SlowDown", Integer.parseInt(splitText[2]));
                                lane.addSlowDownNote(slowDownNote);
                                break;
                            case "DoubleScore":
                                DoubleScoreNote doubleScoreNote = new DoubleScoreNote("2x", Integer.parseInt(splitText[2]));
                                lane.addDoubleScoreNote(doubleScoreNote);
                                break;
                            case "Bomb":
                                BombNote bombNote = new BombNote("Bomb", Integer.parseInt(splitText[2]));
                                lane.addBombNote(bombNote);
                                break;
                        }
                    }

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

    }
    /**
     * Sets the score multiplier to a given value.
     *
     * @param newMultiplier the new score multiplier
     */
    public static void setScoreMultiplier(double newMultiplier) {
        scoreMultiplier = newMultiplier;
    }
    /**
     * Gets the current score multiplier.
     *
     * @return the current score multiplier
     */
    public static double getScoreMultiplier() {
        return scoreMultiplier;
    }
    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {

        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }

        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

        if (!started) {
            // starting screen
            TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
            INSTRUCTION_FONT.drawString(LEVEL_SELECT, TITLE_X + INS_X_OFFSET,
                    TITLE_Y + INS_Y_OFFSET);
            INSTRUCTION_FONT.drawString(LEVEL_NUMBER, TITLE_X + INS_X_OFFSET,
                    TITLE_Y + INS_Y_OFFSET + LEVEL_SPACING);

            // Handle keypress for levels 1, 2, and 3
            if (input.wasPressed(Keys.NUM_1)) {
                // Start level 1
                currentLevelCsv = CSV_FILE_1;
                initializeGame(CSV_FILE_1);
                started = true;
            } else if (input.wasPressed(Keys.NUM_2)) {
                // Start level 2
                currentLevelCsv = CSV_FILE_2;
                initializeGame(CSV_FILE_2);
                started = true;
            } else if (input.wasPressed(Keys.NUM_3)) {
                // Start level 3
                currentLevelCsv = CSV_FILE_3;
                initializeGame(CSV_FILE_3);
                started = true;
            }
        } else if (finished) {
            // end screen
            int targetScore = 0;

            if (currentLevelCsv.equals(CSV_FILE_1)) {
                targetScore = CLEAR_SCORE_1;
            } else if (currentLevelCsv.equals(CSV_FILE_2)) {
                targetScore = CLEAR_SCORE_2;
            } else if (currentLevelCsv.equals(CSV_FILE_3)) {
                targetScore = CLEAR_SCORE_3;
            }

            if (score >= targetScore) {
                TITLE_FONT.drawString(CLEAR_MESSAGE,
                        WINDOW_WIDTH/2 - TITLE_FONT.getWidth(CLEAR_MESSAGE)/2,
                        WIN_LOSS_MESSAGE_Y);
                INSTRUCTION_FONT.drawString(INSTRUCTION_END_SCREEN,
                        (WINDOW_WIDTH - INSTRUCTION_FONT.getWidth(INSTRUCTION_END_SCREEN)) / 2.0,
                        INSTRUCTION_END_Y);
            } else {
                TITLE_FONT.drawString(TRY_AGAIN_MESSAGE,
                        WINDOW_WIDTH/2 - TITLE_FONT.getWidth(TRY_AGAIN_MESSAGE)/2,
                        WIN_LOSS_MESSAGE_Y);
                INSTRUCTION_FONT.drawString(INSTRUCTION_END_SCREEN,
                        (WINDOW_WIDTH - INSTRUCTION_FONT.getWidth(INSTRUCTION_END_SCREEN)) / 2.0,
                        INSTRUCTION_END_Y);
            }
            if (input.wasPressed(Keys.SPACE)) {
                // Reset the game to the starting screen
                started = false;
                finished = false;
                score = 0;
                currFrame = 0;
                 // Clear the lanes and specialLanes ArrayLists
                lanes.clear();
                specialLanes.clear();
            }
        } else {
            // gameplay

            SCORE_FONT.drawString("Score " + score, SCORE_LOCATION, SCORE_LOCATION);

            if (paused) {
                if (input.wasPressed(Keys.TAB)) {
                    paused = false;
                }
                
                for (Lane lane : lanes) {
                    lane.draw();
                }

                for (Lane lane : specialLanes) {
                    lane.draw();
                }

            } else {
                currFrame++;
                for (Lane lane : lanes) {
                    score += lane.update(input, accuracy) * ShadowDance.getScoreMultiplier();
                }

                for (Lane lane : specialLanes) {
                    score += lane.update(input, accuracy) * ShadowDance.getScoreMultiplier();
                }

                if (currentLevelCsv.equals(CSV_FILE_3)) {
                    guardian.update(input, enemies);
                    if (currFrame % ENEMY_CREATION_FRAME == 0) {
                        Enemy enemy = new Enemy();
                        enemies.add(enemy);
                    }

                    for (Enemy enemy : enemies) {
                        enemy.update();
                        for (Lane lane : lanes) {
                            Iterator<Note> noteIterator = lane.getNotes().iterator();
                            while (noteIterator.hasNext()) {
                                Note note = noteIterator.next();
                                if (note.isActive() && enemy.collidesWith(note)) {
                                    noteIterator.remove();
                                }
                            }
                        }
                    }
                }
                enemies.removeIf(Enemy::isRemoved);
                accuracy.update();
                finished = checkFinished();
                if (input.wasPressed(Keys.TAB)) {
                    paused = true;
                }
            }
        }

    }
    /**
     * Gets the current frame.
     *
     * @return the current frame
     */
    public static int getCurrFrame() {
        return currFrame;
    }
    /**
     * Checks if all the lanes are finished.
     *
     * @return true if all the lanes are finished, false otherwise
     */
    private boolean checkFinished() {
        for (Lane lane : lanes) {
            if (!lane.isFinished()) {
                return false;
            }
        }
        for (Lane lane : specialLanes) {
            if (!lane.isFinished()) {
                return false;
            }
        }
        return true;
    }
}
