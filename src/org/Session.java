package org;

import CredentialManager.CredentialManager;

/**
 * Created by Tom on 4/2/2017.
 */
public class Session {

  //public ListNodes directionPath;
  public String authToken;
  //public Time sessionStart;
  public int zoomLevel;
  public Language currLang = Language.ENGLISH;
  public CredentialManager credentialManager = new CredentialManager();
  public Dictionary dictionary = new Dictionary();

  public void setLanguage(Language lang) {
    this.currLang = lang;
  }

  public Language getLanguage() {
    return this.currLang;
  }

  public Session() {
  }
}
