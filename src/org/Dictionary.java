package org;

import java.util.HashMap;
import java.util.Map;
import UIControllers.CentralUIController;
/**
 * Created by Brandon on 4/4/2017.
 */
public class Dictionary {
  HashMap<String, Entry> stringEntryMap;

  /**
   * Creates a new org.Dictionary object.
   *
   * @param : EntryMap stores a map of possible strings in the application and entry
   * objects that will pair the strings with the given language.
   */
  public Dictionary() {
    /* dictionary definition */
    setupDictionary();
  }

  /**
   * Adds a new HashMap to the org.Dictionary object.
   * @param string: Contains the string of the new HashMap.
   * @param entry: Contains the entry of the new HashMap.
   */
  public void addEntry(String string, Entry entry){
    this.stringEntryMap.put(string, entry);
  }

  /**
   * Gets a string from the given key. Returns an empty string if the key does not exist.
   * @param key: The key given to fetch the corresponding String.
   * @return: Returns the String associated with the key.
   */
  public String getString(String key, Language language){
    Entry info = stringEntryMap.get(key);
    if (info == null) {
      return "";
    }
    return info.getString(language);
  }

  public void setupDictionary () {
    stringEntryMap = new HashMap<String, Entry>();
    HashMap<Language, String> h = new HashMap<Language, String>();
    h.put(Language.SPANISH, "Espalda");
    h.put(Language.ENGLISH, "Back");
    h.put(Language.PORTUGESE, "Costas");
    Entry e = new Entry(h);
    this.addEntry("Back", e);


    HashMap<Language, String> s = new HashMap<Language, String>();
    s.put(Language.SPANISH, "Nombre de usuario");
    s.put(Language.ENGLISH, "Username");
    s.put(Language.PORTUGESE, "Nome de usuário");
    Entry g = new Entry(s);
    this.addEntry("Username", g);
  }
}
