import bagel.*;
/**
 * The DoubleScoreNote class represents a special type of Note in the game.
 * When activated, it doubles the current score multiplier.
 * It moves down the screen at a certain speed and can be deactivated by pressing a key when it is within a certain distance from the target height.
 * 
 * Attributes:
 * - image: The image of the DoubleScoreNote.
 * - appearanceFrame: The frame when the DoubleScoreNote appears.
 * - speed: The speed of the DoubleScoreNote.
 * - y: The y-coordinate of the DoubleScoreNote.
 * - active: A boolean indicating whether the DoubleScoreNote is active.
 * - completed: A boolean indicating whether the DoubleScoreNote is completed.
 * - ACTIVATION_RADIUS: The radius within which the DoubleScoreNote can be deactivated.
 * - EFFECT_DURATION: The duration of the double score effect.
 * - effectCounter: The counter for the effect duration.
 */
public class DoubleScoreNote extends Note{
    private final Image image = new Image("res/note2x.png");
    private final int appearanceFrame;
    private static int speed = 2;
    protected int y = 100;
    private boolean active = false;
    private boolean completed = false;
    private static final int ACTIVATION_RADIUS = 50;
    private static final int EFFECT_DURATION = 480;
    private int effectCounter = 0;
    /**
     * Constructs a DoubleScoreNote with a given direction and appearance frame.
     *
     * @param dir the direction of the DoubleScoreNote
     * @param appearanceFrame the frame when the DoubleScoreNote appears
     */
    public DoubleScoreNote(String dir, int appearanceFrame) {
        super(dir, appearanceFrame);
        this.appearanceFrame = appearanceFrame;
    }
    /**
     * Checks if the DoubleScoreNote is active.
     *
     * @return true if the DoubleScoreNote is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }
    /**
     * Checks if the DoubleScoreNote is completed.
     *
     * @return true if the DoubleScoreNote is completed, false otherwise
     */
    public boolean isCompleted() {
        return completed;
    }
    /**
     * Deactivates the DoubleScoreNote and marks it as completed.
     */
    public void deactivate() {
        active = false;
        completed = true;
    }
    /**
     * Updates the state of the DoubleScoreNote.
     * If the DoubleScoreNote is active, it moves down the screen.
     * If the current frame is greater than or equal to the appearance frame and the DoubleScoreNote is not completed, it is activated.
     */
    public void update() {
        if (active) {
            y += speed;
        }

        if (ShadowDance.getCurrFrame() >= appearanceFrame && !completed) {
            active = true;
        }

        if (effectCounter > 0) {
            effectCounter--;
            if (effectCounter == 0) {
                // Reset score multiplier to normal when effect duration elapses
                ShadowDance.setScoreMultiplier(1);
            }
        }
    }
    /**
     * Draws the DoubleScoreNote on the screen at a given x-coordinate.
     * The DoubleScoreNote is only drawn if it is active.
     *
     * @param x the x-coordinate where the DoubleScoreNote is drawn
     */
    public void draw(int x) {
        if (active) {
            image.draw(x, y);
        }
    }
    /**
     * Checks the score for the DoubleScoreNote.
     * If the DoubleScoreNote is active and within a certain distance from the target height when a key is pressed, it is deactivated and the score multiplier is doubled.
     * If the DoubleScoreNote has passed the target height, it is deactivated and a miss score is returned.
     *
     * @param input the current input (keyboard and mouse state)
     * @param accuracy the current accuracy
     * @param targetHeight the target height for the DoubleScoreNote
     * @param relevantKey the key that deactivates the DoubleScoreNote
     * @return the score for the DoubleScoreNote
     */
    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            int distance = Math.abs(y - targetHeight);
            if (distance <= ACTIVATION_RADIUS && input.wasPressed(relevantKey)) {
                deactivate();
                ShadowDance.setScoreMultiplier(2);
                effectCounter = EFFECT_DURATION;
                return 0;
            }

            if (y >= targetHeight) {
                deactivate();
                return Accuracy.NOT_SCORED;
            }
        }

        return Accuracy.NOT_SCORED;
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