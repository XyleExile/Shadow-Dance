import bagel.*;
/**
 * The BombNote class represents a special type of Note in the game.
 * When activated, it moves down the screen at a certain speed.
 * It can be deactivated by pressing a key when it is within a certain distance from the target height.
 * 
 * Attributes:
 * - image: The image of the BombNote.
 * - appearanceFrame: The frame when the BombNote appears.
 * - speed: The speed of the BombNote.
 * - y: The y-coordinate of the BombNote.
 * - active: A boolean indicating whether the BombNote is active.
 * - completed: A boolean indicating whether the BombNote is completed.
 * - ACTIVATION_RADIUS: The radius within which the BombNote can be deactivated.
 */
public class BombNote extends Note {
    private final Image image = new Image("res/noteBomb.png");
    private final int appearanceFrame;
    private static int speed = 2;
    protected int y = 100;
    private boolean active = false;
    private boolean completed = false;
    private static final int ACTIVATION_RADIUS = 50;
    /**
     * Constructs a BombNote with a given direction and appearance frame.
     *
     * @param dir the direction of the BombNote
     * @param appearanceFrame the frame when the BombNote appears
     */
    public BombNote(String dir, int appearanceFrame) {
        super(dir, appearanceFrame);
        this.appearanceFrame = appearanceFrame;
    }
    /**
     * Checks if the BombNote is active.
     *
     * @return true if the BombNote is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }
    /**
     * Checks if the BombNote is completed.
     *
     * @return true if the BombNote is completed, false otherwise
     */
    public boolean isCompleted() {
        return completed;
    }
    /**
     * Deactivates the BombNote and marks it as completed.
     */
    public void deactivate() {
        active = false;
        completed = true;
    }
    /**
     * Updates the state of the BombNote.
     * If the BombNote is active, it moves down the screen.
     * If the current frame is greater than or equal to the appearance frame and the BombNote is not completed, it is activated.
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
     * Draws the BombNote on the screen at a given x-coordinate.
     * The BombNote is only drawn if it is active.
     *
     * @param x the x-coordinate where the BombNote is drawn
     */
    public void draw(int x) {
        if (active) {
            image.draw(x, y);
        }
    }
    /**
     * Checks the score for the BombNote.
     * If the BombNote is active and within a certain distance from the target height when a key is pressed, it is deactivated and a score is returned.
     * If the BombNote has passed the target height, it is deactivated and a miss score is returned.
     *
     * @param input the current input (keyboard and mouse state)
     * @param accuracy the current accuracy
     * @param targetHeight the target height for the BombNote
     * @param relevantKey the key that deactivates the BombNote
     * @return the score for the BombNote
     */
    @Override
    public int checkScore(Input input, Accuracy accuracy, int targetHeight, Keys relevantKey) {
        if (isActive()) {
            // Calculate the distance between the y-coordinate of the stationary note and the y-coordinate of the special note
            int distance = Math.abs(y - targetHeight);

            // If the distance is less than or equal to 50 and the relevant key is pressed, deactivate the note and return the score
            if (distance <= ACTIVATION_RADIUS && input.wasPressed(relevantKey)) {
                deactivate();
                // code for removing lane but key is not registering right now
                return 0;
            }

            // If the note has passed the target height, deactivate the note and return a miss score
            if (y >= targetHeight) {
                deactivate();
                return Accuracy.NOT_SCORED;
            }
        }

        return Accuracy.NOT_SCORED;
    }
    /**
     * Increases the speed of the Bomb by 1.
     */
    public static void increaseSpeed() {
        speed++;
    }
    /**
     * Decreases the speed of the Bomb by 1.
     */
    public static void decreaseSpeed() {
        if (speed > 1) {
            speed--;
        }
    }

}