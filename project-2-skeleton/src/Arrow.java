import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;
/**
 * The Arrow class represents an arrow shot by the Guardian.
 * Arrows move in a straight line and stop updating when they hit an enemy or the window edges.
 * 
 * Attributes:
 * - image: The image of the arrow.
 * - position: The current position of the arrow.
 * - direction: The direction of the arrow.
 * - speed: The speed of the arrow.
 * - isHit: A boolean indicating whether the arrow has hit an enemy or the window edges.
 * - WINDOW_WIDTH: The width of the window.
 * - WINDOW_HEIGHT: The height of the window.
 */
public class Arrow {
    private final Image image = new Image("res/arrow.png");
    private Point position;
    private Vector2 direction;
    private static int speed = 6;
    private boolean isHit = false;
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    /**
     * Constructs an Arrow with a given direction.
     *
     * @param direction the direction of the arrow
     */
    public Arrow(Vector2 direction) {
        this.direction = direction;
        this.position = new Point(Guardian.getPosition().x, Guardian.getPosition().y);
    }
    /**
     * Updates the position of the arrow and checks for collisions with the window edges.
     * If the arrow hits the window edges, it is marked as hit.
     */
    public void update() {
        position = position.asVector().add(direction.mul(speed)).asPoint();
        if (position.x <= 0 || position.x >= WINDOW_WIDTH || position.y <= 0 || position.y >= WINDOW_HEIGHT) {
            isHit = true;
        }
        image.draw(position.x, position.y, new DrawOptions().setRotation(Math.atan2(direction.y, direction.x)));
    }
    /**
     * Checks if the arrow has hit an enemy or the window edges.
     *
     * @return true if the arrow has hit, false otherwise
     */
    public boolean isHit() {
        return isHit;
    }
    /**
     * Marks the arrow as hit.
     */
    public void hit() {
        this.isHit = true;
    }
    /**
     * Gets the current position of the arrow.
     *
     * @return the current position of the arrow
     */
    public Point getPosition() {
        return this.position;
    }
    /**
     * Increases the speed of the arrow by 1.
     * This is a static method and affects the speed of all arrows.
     */
    public static void increaseSpeed() {
        speed++;
    }
    /**
     * Decreases the speed of the arrow by 1 if the current speed is greater than 1.
     * This is a static method and affects the speed of all arrows.
     */
    public static void decreaseSpeed() {
        if (speed > 1) {
            speed--;
        }
    }
}