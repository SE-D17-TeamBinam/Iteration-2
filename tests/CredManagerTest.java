import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import CredentialManager.CredentialManager;

/**
 * Created by Tom on 4/3/2017.
 */
public class CredManagerTest {


  //Setup
  private CredentialManager manager = new CredentialManager("admin1", "aBcDef");

  @Test
  public void isAdmin_normalCase(){
    assertEquals(true, manager.userIsAdmin("admin1", "aBcDef"));
  }

  @Test
  public void isAdmin_tooShort(){
    assertEquals(false, manager.userIsAdmin("admin1", "aa"));
  }

  @Test
  public void isAdmin_wrongCase(){
    assertEquals(false, manager.userIsAdmin("admin1", "AbCdEF"));
  }

  @Test
  public void isAdmin_tooLong(){
    assertEquals(false, manager.userIsAdmin("admin1", "aaaaaaaaaaaaaaaaaaaaaaaa"));
  }

}
