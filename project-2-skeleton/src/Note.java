import bagel.*;
import bagel.util.Point;

/*
 * I'll be using the full solution given by Project 1
 */

/**
 * The Note class represents a note in the game.
 * When activated, it moves down the screen at a certain speed.
 * It can be deactivated by pressing a key when it is within a certain distance from the target height.
 * 
 * Attributes:
 * - image: The image of the Note.
 * - appearanceFrame: The frame when the Note appears.
 * - speed: The speed of the Note.
 * - y: The y-coordinate of the Note.
 * - active: A boolean indicating whether the Note is active.
 * - completed: A boolean indicating whether the Note is completed.
 * - x: The x-coordinate of the Note.
 */
public class Note {
    private final Image image;
    private final int appearanceFrame;
    private static int speed = 2;
    protected int y = 100;
    private boolean active = false;
    private boolean completed = false;
    private int x;
    /**
     * Constructs a Note with a given direction and appearance frame.
     *
     * @param dir the direction of the Note
     * @param appearanceFrame the frame when the Note appears
     */
    public Note(String dir, int appearanceFrame) {
        image = new Image("res/note" + dir + ".png");
        this.appearanceFrame = appearanceFrame;
    }
    /**
     * Checks if the Note is active.
     *
     * @return true if the Note is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }
    /**
     * Checks if the Note is completed.
     *
     * @return true if the Note is completed, false otherwise
     */
    public boolean isCompleted() {
        return completed;
    }
    /**
     * Deactivates the Note and marks it as completed.
     */
    public void deactivate() {
        active = false;
        completed = true;
    }
    /**
     * Updates the state of the Note.
     * If the Note is active, it moves down the screen.
     * If the current frame is greater than or equal to the appearance frame and the Note is not completed, it is activated.
     */
    public void update() {
        if (active) {
            y += speed;
        }

        if (ShadowDance.getCurrFrame() >= appearanceFrame && !completed) {
            active = true;
        }
    }
    /**
     * Draws the Note on the screen at a given x-coordinate.
     * The Note is only drawn if it is active.
     *
     * @param x the x-coordinate where the Note is drawn
     */
    public void draw(int x) {
        this.x = x;
        if (active) {
            image.draw(x, y);
        }
    }
    /**
     * Checks the score for the Note.
     * If the Note is active and within a certain distance from the target height when a key is pressed, it is deactivated and a score is returned.
     * If the Note has passed the target height, it is deactivated and a miss score is returned.
     *
     * @param input the current input (keyboard and mouse state)
     * @param accuracy the current accuracy
     * @param targetHeight the target height for the Note
     * @param relevantKey the key that deactivates the Note
     * @return the score for the Note
     */
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            int score = accuracy.evaluateScore(y, targetHeight, input.wasPressed(relevantKey));
            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score;
            }

        }

        return 0;
    }
    /**
     * Increases the speed of the note by 1.
     */
    public static void increaseSpeed() {
        speed++;
    }
    /**
     * Decrease the speed of the note by 1.
     */
    public static void decreaseSpeed() {
        if (speed > 1) {
            speed--;
        }
    }
    /**
     * Gets the current position of the Note.
     *
     * @param x the x-coordinate of the Note
     * @return the current position of the Note
     */
    public Point getPosition(int x) {
        return new Point(x, y + image.getHeight() / 2.0);
    }
    /**
     * Gets the x-coordinate of the Note.
     *
     * @return the x-coordinate of the Note
     */
    public int getX() {
        return this.x;
    }
}
