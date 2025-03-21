import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;
import java.util.Random;
/**
 * The Enemy class represents an enemy in the game.
 * Enemies move in a straight line and are removed if hit by an arrow.
 * 
 * Attributes:
 * - MIN_X, MAX_X, MIN_Y, MAX_Y: The minimum and maximum x and y coordinates for the enemy's position.
 * - speed: The speed of the enemy.
 * - image: The image of the enemy.
 * - position: The current position of the enemy.
 * - direction: The direction of the enemy.
 * - COLLISION: The collision radius of the enemy.
 * - isRemoved: A boolean indicating whether the enemy has been marked for removal.
 */
public class Enemy {
    private static final int MIN_X = 100;
    private static final int MAX_X = 900;
    private static final int MIN_Y = 100;
    private static final int MAX_Y = 500;
    private static int speed = 1;
    private final Image image = new Image("res/enemy.png");
    private Point position;
    private Vector2 direction;
    private static final int COLLISION = 104;
    private boolean isRemoved = false;
    /**
     * Constructs an Enemy with a random position and direction.
     */
    public Enemy() {
        Random random = new Random();
        int x = random.nextInt(MAX_X - MIN_X + 1) + MIN_X;
        int y = random.nextInt(MAX_Y - MIN_Y + 1) + MIN_Y;
        position = new Point(x, y);
        direction = new Vector2(random.nextBoolean() ? speed : -speed, 0);
    }
    /**
     * Updates the position of the enemy and checks for collisions with the window edges.
     */
    public void update() {
        position = position.asVector().add(direction).asPoint();
        if (position.x <= MIN_X || position.x >= MAX_X) {
            direction = direction.mul(-1);
        }
        image.draw(position.x, position.y);
    }
    /**
     * Checks if the enemy collides with a note.
     *
     * @param note the note to check collision with
     * @return true if the enemy collides with the note, false otherwise
     */
    public boolean collidesWith(Note note) {
        Point notePosition = note.getPosition(note.getX());
        return position.distanceTo(notePosition) <= COLLISION;
    }
    /**
     * Marks the enemy for removal.
     */
    public void remove() {
        isRemoved = true;
    }
    /**
     * Checks if the enemy is marked for removal.
     *
     * @return true if the enemy is marked for removal, false otherwise
     */
    public boolean isRemoved() {
        return isRemoved;
    }
    /**
     * Gets the current position of the enemy.
     *
     * @return the current position of the enemy
     */
    public Point getPosition() {
        return this.position;
    }
    /**
     * Increases the speed of the enemy by 1.
     */
    public static void increaseSpeed() {
        speed++;
    }
    /**
     * Decreases the speed of the enemy by 1.
     */
    public static void decreaseSpeed() {
        if (speed > 1) {
            speed--;
        }
    }

}