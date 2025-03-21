import bagel.*;

/*
 * I'll be using the full solution given by Project 1
 */

/**
 * The HoldNote class represents a special type of Note in the game.
 * When activated, it moves down the screen at a certain speed.
 * It can be deactivated by pressing a key when it is within a certain distance from the target height.
 * 
 * Attributes:
 * - HEIGHT_OFFSET: The offset for the height of the HoldNote.
 * - image: The image of the HoldNote.
 * - appearanceFrame: The frame when the HoldNote appears.
 * - speed: The speed of the HoldNote.
 * - y: The y-coordinate of the HoldNote.
 * - active: A boolean indicating whether the HoldNote is active.
 * - holdStarted: A boolean indicating whether the hold of the HoldNote has started.
 * - completed: A boolean indicating whether the HoldNote is completed.
 */
public class HoldNote {
    private static final int HEIGHT_OFFSET = 82;
    private final Image image;
    private final int appearanceFrame;
    private static int speed = 2;
    private int y = 24;
    private boolean active = false;
    private boolean holdStarted = false;
    private boolean completed = false;
    /**
     * Constructs a HoldNote with a given direction and appearance frame.
     *
     * @param dir the direction of the HoldNote
     * @param appearanceFrame the frame when the HoldNote appears
     */
    public HoldNote(String dir, int appearanceFrame) {
        image = new Image("res/holdNote" + dir + ".png");
        this.appearanceFrame = appearanceFrame;
    }
    /**
     * Checks if the HoldNote is active.
     *
     * @return true if the HoldNote is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }
    /**
     * Checks if the HoldNote is completed.
     *
     * @return true if the HoldNote is completed, false otherwise
     */
    public boolean isCompleted() {
        return completed;
    }
    /**
     * Deactivates the HoldNote and marks it as completed.
     */
    public void deactivate() {
        active = false;
        completed = true;
    }
    /**
     * Starts the hold of the HoldNote.
     */
    public void startHold() {
        holdStarted = true;
    }
    /**
     * Updates the state of the HoldNote.
     * If the HoldNote is active, it moves down the screen.
     * If the current frame is greater than or equal to the appearance frame and the HoldNote is not completed, it is activated.
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
     * Draws the HoldNote on the screen at a given x-coordinate.
     * The HoldNote is only drawn if it is active.
     *
     * @param x the x-coordinate where the HoldNote is drawn
     */
    public void draw(int x) {
        if (active) {
            image.draw(x, y);
        }
    }
    /**
     * Checks the score for the HoldNote.
     * If the HoldNote is active and within a certain distance from the target height when a key is pressed, it is deactivated and a score is returned.
     * If the HoldNote has passed the target height, it is deactivated and a miss score is returned.
     *
     * @param input the current input (keyboard and mouse state)
     * @param accuracy the current accuracy
     * @param targetHeight the target height for the HoldNote
     * @param relevantKey the key that deactivates the HoldNote
     * @return the score for the HoldNote
     */
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive() && !holdStarted) {
            int score = accuracy.evaluateScore(getBottomHeight(), targetHeight, input.wasPressed(relevantKey));

            if (score == Accuracy.MISS_SCORE) {
                deactivate();
                return score;
            } else if (score != Accuracy.NOT_SCORED) {
                startHold();
                return score;
            }
        } else if (isActive() && holdStarted) {

            int score = accuracy.evaluateScore(getTopHeight(), targetHeight, input.wasReleased(relevantKey));

            if (score != Accuracy.NOT_SCORED) {
                deactivate();
                return score;
            } else if (input.wasReleased(relevantKey)) {
                deactivate();
                accuracy.setAccuracy(Accuracy.MISS);
                return Accuracy.MISS_SCORE;
            }
        }

        return 0;
    }
    /**
     * gets the location of the start of the note
     */
    private int getBottomHeight() {
        return y + HEIGHT_OFFSET;
    }

    /**
     * gets the location of the end of the note
     */
    private int getTopHeight() {
        return y - HEIGHT_OFFSET;
    }
    /**
     * Increases the speed of the note by 1.
     */
    public static void increaseSpeed() {
        speed++;
    }
    /**
     * Decreases the speed of the note by 1.
     */
    public static void decreaseSpeed() {
        if (speed > 1) {
            speed--;
        }
    }
}
