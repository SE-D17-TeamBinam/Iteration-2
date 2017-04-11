import java.util.HashMap;
import org.CentralController;
import org.Dictionary;
import org.Entry;
import org.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Created by Brandon on 4/4/2017.
 */
/**
public class DictionaryTest {

  public Dictionary dictionary;
  public Entry entry1;
  public Entry entry2;


  /**@/**BeforeEach
  public void setUp() {

    CentralController controller = new CentralController();

    dictionary = new Dictionary(new HashMap<String, Entry>());
    entry1 = new Entry(new HashMap<Language, String>());
    entry2 = new Entry(new HashMap<Language, String>());

    //controller.currSession.currLang = Language.ENGLISH;

    entry1.addString(Language.SPANISH, "Spanish word for n.");
    entry1.addString(Language.ENGLISH, "English word for n.");
    entry2.addString(Language.SPANISH, "Spanish word for x.");
    entry2.addString(Language.ENGLISH, "English word for x.");
    dictionary.addEntry("n", entry1);
    dictionary.addEntry("x", entry2);
  }

  @Test
  /**
   * Tests the String returned when given a language and a certain key.
   */
  /**
  public void canGetStringTest() {
    assertEquals("English word for n.",dictionary.getString("n"));
  }

  @Test
  /**
   * Tests the String returned when given a language and a certain key.
   */
 /** public void canGetStringTest2() {
    assertEquals("English word for x.", dictionary.getString("x"));
  }

  @Test
  /**
   * Tests the String returned when a given key does not exist in the org.Dictionary's HashMap.
   */
 /** public void keyDoesNotExist() {
    assertEquals("", dictionary.getString("This key doesn't exist."));
  }

}
*/