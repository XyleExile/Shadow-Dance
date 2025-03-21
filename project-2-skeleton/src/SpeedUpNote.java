import bagel.*;
/**
 * The SpeedUpNote class represents a special type of Note in the game.
 * When activated, it moves down the screen at a certain speed.
 * It can be deactivated by pressing a key when it is within a certain distance from the target height.
 * When deactivated, it increases the speed of all notes.
 * 
 * Attributes:
 * - image: The image of the SpeedUpNote.
 * - appearanceFrame: The frame when the SpeedUpNote appears.
 * - speed: The speed of the SpeedUpNote.
 * - y: The y-coordinate of the SpeedUpNote.
 * - active: A boolean indicating whether the SpeedUpNote is active.
 * - completed: A boolean indicating whether the SpeedUpNote is completed.
 * - ACTIVATION_RADIUS: The radius within which the SpeedUpNote can be deactivated.
 */
public class SpeedUpNote extends Note{
    private final Image image = new Image("res/noteSpeedUp.png");
    private final int appearanceFrame;
    private static int speed = 2;
    protected int y = 100;
    private boolean active = false;
    private boolean completed = false;
    private static final int ACTIVATION_RADIUS = 50;
    /**
     * Constructs a SpeedUpNote with a given direction and appearance frame.
     *
     * @param dir the direction of the SpeedUpNote
     * @param appearanceFrame the frame when the SpeedUpNote appears
     */
    public SpeedUpNote(String dir, int appearanceFrame) {
        super(dir, appearanceFrame);
        this.appearanceFrame = appearanceFrame;
    }
    /**
     * Checks if the SpeedUpNote is active.
     *
     * @return true if the SpeedUpNote is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }
    /**
     * Checks if the SpeedUpNote is completed.
     *
     * @return true if the SpeedUpNote is completed, false otherwise
     */
    public boolean isCompleted() {
        return completed;
    }
    /**
     * Deactivates the SpeedUpNote and marks it as completed.
     */
    public void deactivate() {
        active = false;
        completed = true;
    }
    /**
     * Updates the state of the SpeedUpNote.
     * If the SpeedUpNote is active, it moves down the screen.
     * If the current frame is greater than or equal to the appearance frame and the SpeedUpNote is not completed, it is activated.
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
     * Draws the SpeedUpNote on the screen at a given x-coordinate.
     * The SpeedUpNote is only drawn if it is active.
     *
     * @param x the x-coordinate where the SpeedUpNote is drawn
     */
    public void draw(int x) {
        if (active) {
            image.draw(x, y);
        }
    }
    /**
     * Checks the score for the SpeedUpNote.
     * If the SpeedUpNote is active and within a certain distance from the target height when a key is pressed, it is deactivated, the speed of all notes is increased, and a score is returned.
     * If the SpeedUpNote has passed the target height, it is deactivated and a miss score is returned.
     *
     * @param input the current input (keyboard and mouse state)
     * @param accuracy the current accuracy
     * @param targetHeight the target height for the SpeedUpNote
     * @param relevantKey the key that deactivates the SpeedUpNote
     * @return the score for the SpeedUpNote
     */
    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            
            int distance = Math.abs(y - targetHeight);

            if (distance <= ACTIVATION_RADIUS && input.wasPressed(relevantKey)) {
                deactivate();
                Note.increaseSpeed();
                HoldNote.increaseSpeed();
                SpeedUpNote.increaseSpeed();
                SlowDownNote.increaseSpeed();
                DoubleScoreNote.increaseSpeed();
                BombNote.increaseSpeed();
                Enemy.increaseSpeed();
                Arrow.increaseSpeed();
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
     * Decreases the speed of the note by 1.
     */
    public static void decreaseSpeed() {
         // Ensure speed doesn't go below 1
        if (speed > 1) {
            speed--;
        }
    }

}