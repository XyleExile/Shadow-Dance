import bagel.*;
/**
 * The SlowDownNote class represents a special type of Note in the game.
 * When activated, it moves down the screen at a certain speed.
 * It can be deactivated by pressing a key when it is within a certain distance from the target height.
 * When deactivated, it decreases the speed of all notes.
 * 
 * Attributes:
 * - image: The image of the SlowDownNote.
 * - appearanceFrame: The frame when the SlowDownNote appears.
 * - speed: The speed of the SlowDownNote.
 * - y: The y-coordinate of the SlowDownNote.
 * - active: A boolean indicating whether the SlowDownNote is active.
 * - completed: A boolean indicating whether the SlowDownNote is completed.
 * - ACTIVATION_RADIUS: The radius within which the SlowDownNote can be deactivated.
 */
public class SlowDownNote extends Note{
    private final Image image = new Image("res/noteSlowDown.png");
    private final int appearanceFrame;
    private static int speed = 2;
    protected int y = 100;
    private boolean active = false;
    private boolean completed = false;
    private static final int ACTIVATION_RADIUS = 50;
    /**
     * Constructs a SlowDownNote with a given direction and appearance frame.
     *
     * @param dir the direction of the SlowDownNote
     * @param appearanceFrame the frame when the SlowDownNote appears
     */
    public SlowDownNote(String dir, int appearanceFrame) {
        super(dir, appearanceFrame);
        this.appearanceFrame = appearanceFrame;
    }
    /**
     * Checks if the SlowDownNote is active.
     *
     * @return true if the SlowDownNote is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }
    /**
     * Checks if the SlowDownNote is completed.
     *
     * @return true if the SlowDownNote is completed, false otherwise
     */
    public boolean isCompleted() {
        return completed;
    }
    /**
     * Deactivates the SlowDownNote and marks it as completed.
     */
    public void deactivate() {
        active = false;
        completed = true;
    }
    /**
     * Updates the state of the SlowDownNote.
     * If the SlowDownNote is active, it moves down the screen.
     * If the current frame is greater than or equal to the appearance frame and the SlowDownNote is not completed, it is activated.
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
     * Draws the SlowDownNote on the screen at a given x-coordinate.
     * The SlowDownNote is only drawn if it is active.
     *
     * @param x the x-coordinate where the SlowDownNote is drawn
     */
    public void draw(int x) {
        if (active) {
            image.draw(x, y);
        }
    }
    /**
     * Checks the score for the SlowDownNote.
     * If the SlowDownNote is active and within a certain distance from the target height when a key is pressed, it is deactivated, the speed of all notes is decreased, and a score is returned.
     * If the SlowDownNote has passed the target height, it is deactivated and a miss score is returned.
     *
     * @param input the current input (keyboard and mouse state)
     * @param accuracy the current accuracy
     * @param targetHeight the target height for the SlowDownNote
     * @param relevantKey the key that deactivates the SlowDownNote
     * @return the score for the SlowDownNote
     */
    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {

            int distance = Math.abs(y - targetHeight);

            if (distance <= ACTIVATION_RADIUS && input.wasPressed(relevantKey)) {
                deactivate();
                Note.decreaseSpeed();
                HoldNote.decreaseSpeed();
                SpeedUpNote.decreaseSpeed();
                SlowDownNote.decreaseSpeed();
                DoubleScoreNote.decreaseSpeed();
                BombNote.decreaseSpeed();
                Enemy.decreaseSpeed();
                Arrow.decreaseSpeed();
                return 15;
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
     * Decrease the speed of the note by 1.
     */
    public static void decreaseSpeed() {
        if (speed > 1) {
            speed--;
        }
    }

}