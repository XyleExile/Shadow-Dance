import java.util.ArrayList;
import bagel.*;
import bagel.util.Point;
import bagel.util.Vector2;
/**
 * The Guardian class represents the main character of the game.
 * It is responsible for shooting arrows towards enemies.
 * 
 * Attributes:
 * - POSITION: The static position of the Guardian.
 * - image: The image of the Guardian.
 * - arrows: The list of arrows shot by the Guardian.
 *
 */
public class Guardian {
    private static final Point POSITION = new Point(800, 600);
    private final Image image = new Image("res/guardian.png");
    private ArrayList<Arrow> arrows = new ArrayList<>();

    /**
     * Updates the state of the Guardian and its arrows.
     * If the left shift key is pressed, a new arrow is created.
     * If an arrow hits an enemy, the enemy is removed.
     *
     * @param input the current input (keyboard and mouse state)
     * @param enemies the list of current enemies
     */
    public void update(Input input, ArrayList<Enemy> enemies) {
        if (input.wasPressed(Keys.LEFT_SHIFT)) {
            Vector2 direction;
            Enemy target = findNearestEnemy(enemies);
            if (target != null) {
                direction = target.getPosition().asVector().sub(POSITION.asVector()).normalised();
            } else {
                direction = new Vector2(0, -1);
            }
            arrows.add(new Arrow(direction));
        }
        for (Arrow arrow : arrows) {
            arrow.update();
            for (Enemy enemy : enemies) {
                if (arrow.getPosition().distanceTo(enemy.getPosition()) <= 62) {
                    enemy.remove();
                    arrow.hit();
                }
            }
        }
        arrows.removeIf(Arrow::isHit);
        image.draw(POSITION.x, POSITION.y);
    }
     /**
     * Finds the nearest enemy to the Guardian.
     *
     * @param enemies the list of current enemies
     * @return the nearest enemy, or null if there are no enemies
     */
    private Enemy findNearestEnemy(ArrayList<Enemy> enemies) {
        Enemy nearestEnemy = null;
        double nearestDistance = Double.MAX_VALUE;
        for (Enemy enemy : enemies) {
            double distance = POSITION.distanceTo(enemy.getPosition());
            if (distance < nearestDistance) {
                nearestEnemy = enemy;
                nearestDistance = distance;
            }
        }
        return nearestEnemy;
    }
    /**
     * Gets the current position of the Guardian.
     *
     * @return the current position of the Guardian
     */
    public static Point getPosition() {
        return POSITION;
    }

}
