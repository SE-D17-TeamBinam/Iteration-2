package CredentialManager;

/**
 * Created by Tom on 4/3/2017.
 */
public class CredentialManager {

  private String adminName = "admin";
  private String adminToken = "admin";


  public CredentialManager(){

  }

  public CredentialManager(String adminName, String adminToken){
    this.adminName = adminName;
    this.adminToken = adminToken;
  }


  /**
   * Check if the hash is the same as the verified user
   * TODO(tom): this is vulnerable to a timing attack
   * TODO(haofan): return exception if failed
   * @param name: the name the challenger is presenting
   * @param token: the token the challenger is presenting
   * @return boolean if the challenger is authed
   */
  public boolean userIsAdmin(String name, String token) {
    if (name.equals(this.adminName) && token.equals(this.adminToken)) {
      return true;
    } else {
      return false;
    }
  }

}
