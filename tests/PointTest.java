/**
 * Created by Alberto on 4/3/2017.
 */
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import org.Point;
import org.junit.jupiter.api.Test;

public class PointTest {
  //List of node-Empty
  private ArrayList<Point> neigh = new ArrayList<Point>();
  private Point point1 = new Point( 42, 50, "A56",1, neigh, 4);
  // Point 2
  private Point point2 = new Point( 0, -5, "Basement", 0,
      null, -1);
  // Point 2
  private Point point3 = new Point( 12, 30, "5th FLoor",1,
      null, 5);
  ;
  @Test
  public void isHueristicCorrect(){
    //One test since Nodes that enter this fucntion will have numbers
    assertEquals(97, point1.Heuristic(point2));
  }
  @Test
  public void isHueristicCorrect2(){
    //One test since Nodes that enter this fucntion will have numbers
    assertEquals(50, point1.Heuristic(point3));
  }
  @Test
  public void isDistanceCorrect(){
    //One test since Nodes that enter this fucntion will have numbers
    assertEquals(69, point1.Distance(point2));
  }
  @Test
  public void isDistanceCorrect2(){
    //One test since Nodes that enter this fucntion will have numbers
    assertEquals(36, point1.Distance(point3));
  }

}
