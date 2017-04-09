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
    stringEntryMap = new HashMap<String, Entry>();
    HashMap<String, String> h = new HashMap<String, String>();
    h.put("SPANISH", "Espalda");
    h.put("ENGLISH", "Back");
    h.put("PORTUGUESE", "Costas");
    Entry e = new Entry(h);
    this.addEntry("Back", e);

    HashMap<String, String> s = new HashMap<String, String>();
    s = new HashMap<String, String>();
    s.put("SPANISH", "Nombre de usuario");
    s.put("ENGLISH", "Username");
    s.put("PORTUGUESE", "Nome de usuário");
    Entry g = new Entry(s);
    this.addEntry("Username", g);

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
    public String getString(String key, String language){
      Entry info = stringEntryMap.get(key);
      if (info == null) {
        return "";
      }
      return info.getString(language);
    }
}
