import bagel.*;

/*
 * I'll be using the full solution given by Project 1
 */

/**
 * The Accuracy class is responsible for managing the scoring system of the game.
 * It defines different score values and labels for different levels of accuracy.
 * It also handles the rendering of the accuracy label on the screen.
 * 
 * Attributes:
 * - PERFECT_SCORE: The score for a perfect hit.
 * - GOOD_SCORE: The score for a good hit.
 * - BAD_SCORE: The score for a bad hit.
 * - MISS_SCORE: The score for a miss.
 * - NOT_SCORED: The score for a not scored hit.
 * - PERFECT, GOOD, BAD, MISS: Labels for different levels of accuracy.
 * - PERFECT_RADIUS, GOOD_RADIUS, BAD_RADIUS, MISS_RADIUS: The radius for different levels of accuracy.
 * - ACCURACY_FONT: The font used for the accuracy label.
 * - RENDER_FRAMES: The number of frames the accuracy label is rendered for.
 * - currAccuracy: The current accuracy label.
 * - frameCount: The current frame count.
 */
public class Accuracy {
    public static final int PERFECT_SCORE = 10;
    public static final int GOOD_SCORE = 5;
    public static final int BAD_SCORE = -1;
    public static final int MISS_SCORE = -5;
    public static final int NOT_SCORED = 0;
    public static final String PERFECT = "PERFECT";
    public static final String GOOD = "GOOD";
    public static final String BAD = "BAD";
    public static final String MISS = "MISS";
    private static final int PERFECT_RADIUS = 15;
    private static final int GOOD_RADIUS = 50;
    private static final int BAD_RADIUS = 100;
    private static final int MISS_RADIUS = 200;
    private static final Font ACCURACY_FONT = new Font(ShadowDance.FONT_FILE, 40);
    private static final int RENDER_FRAMES = 30;
    private String currAccuracy = null;
    private int frameCount = 0;
    /**
     * Sets the current accuracy label.
     *
     * @param accuracy the new accuracy label
     */
    public void setAccuracy(String accuracy) {
        currAccuracy = accuracy;
        frameCount = 0;
    }
    /**
     * Evaluates the score based on the height of the note press, the target height, and whether the note was triggered.
     * The score and accuracy label are determined by the distance between the height and the target height.
     *
     * @param height the height of the note press
     * @param targetHeight the target height of the note press
     * @param triggered whether the note was triggered
     * @return the score for the note press
     */
    public int evaluateScore(int height, int targetHeight, boolean triggered) {
        int distance = Math.abs(height - targetHeight);

        if (triggered) {
            if (distance <= PERFECT_RADIUS) {
                setAccuracy(PERFECT);
                return PERFECT_SCORE;
            } else if (distance <= GOOD_RADIUS) {
                setAccuracy(GOOD);
                return GOOD_SCORE;
            } else if (distance <= BAD_RADIUS) {
                setAccuracy(BAD);
                return BAD_SCORE;
            } else if (distance <= MISS_RADIUS) {
                setAccuracy(MISS);
                return MISS_SCORE;
            }

        } else if (height >= (Window.getHeight())) {
            setAccuracy(MISS);
            return MISS_SCORE;
        }

        return NOT_SCORED;

    }

    /**
     * Updates the frame count and renders the current accuracy label on the screen.
     * The accuracy label is rendered for a certain number of frames after it is set.
     */
    public void update() {
        frameCount++;
        if (currAccuracy != null && frameCount < RENDER_FRAMES) {
            ACCURACY_FONT.drawString(currAccuracy,
                    Window.getWidth()/2 - ACCURACY_FONT.getWidth(currAccuracy)/2,
                    Window.getHeight()/2);
        }
    }
}
