package org;

import java.util.HashMap;
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

    HashMap<String, String> a = new HashMap<String, String>();
    a = new HashMap<String, String>();
    a.put("SPANISH", "Contraseña");
    a.put("ENGLISH", "Password");
    a.put("PORTUGUESE", "Palavra-passe");
    Entry password = new Entry(a);
    this.addEntry("Password", password);

    HashMap<String, String> b = new HashMap<String, String>();
    b = new HashMap<String, String>();
    b.put("SPANISH", "Mapa");
    b.put("ENGLISH", "Map");
    b.put("PORTUGUESE", "Mapa");
    Entry map = new Entry(b);
    this.addEntry("Map", map);

    HashMap<String, String> c = new HashMap<String, String>();
    c = new HashMap<String, String>();
    c.put("SPANISH", "iniciar sesión");
    c.put("ENGLISH", "Login");
    c.put("PORTUGUESE", "iniciar sessão");
    Entry login = new Entry(c);
    this.addEntry("Login", login);

    HashMap<String, String> d = new HashMap<String, String>();
    d = new HashMap<String, String>();
    d.put("SPANISH", "Buscar");
    d.put("ENGLISH", "Search");
    d.put("PORTUGUESE", "Procurar");
    Entry search = new Entry(d);
    this.addEntry("Search", search);

    HashMap<String, String> e1 = new HashMap<String, String>();
    e1 = new HashMap<String, String>();
    e1.put("SPANISH", "Piso");
    e1.put("ENGLISH", "Floor");
    e1.put("PORTUGUESE", "Chão");
    Entry floor = new Entry(e1);
    this.addEntry("Floor", floor);

    HashMap<String, String> f = new HashMap<String, String>();
    f = new HashMap<String, String>();
    f.put("SPANISH", "Comienzo");
    f.put("ENGLISH", "Start");
    f.put("PORTUGUESE", "Começar");
    Entry start = new Entry(f);
    this.addEntry("Start", start);

    HashMap<String, String> g1 = new HashMap<String, String>();
    g1 = new HashMap<String, String>();
    g1.put("SPANISH", "Fin");
    g1.put("ENGLISH", "End");
    g1.put("PORTUGUESE", "Fim");
    Entry end = new Entry(g1);
    this.addEntry("End", end);

    HashMap<String, String> i = new HashMap<String, String>();
    i = new HashMap<String, String>();
    i.put("SPANISH", "Zoom");
    i.put("ENGLISH", "Zoom");
    i.put("PORTUGUESE", "Zoom");
    Entry zoom = new Entry(i);
    this.addEntry("Zoom", zoom);

    HashMap<String, String> j = new HashMap<String, String>();
    j = new HashMap<String, String>();
    j.put("SPANISH", "Seleccionado");
    j.put("ENGLISH", "Selected");
    j.put("PORTUGUESE", "Seleccionado");
    Entry selected = new Entry(d);
    this.addEntry("Selected", selected);

    HashMap<String, String> k = new HashMap<String, String>();
    k = new HashMap<String, String>();
    k.put("SPANISH", "Ir a");
    k.put("ENGLISH", "Go");
    k.put("PORTUGUESE", "Go");
    Entry go = new Entry(k);
    this.addEntry("Go", go);

    HashMap<String, String> l = new HashMap<String, String>();
    l = new HashMap<String, String>();
    l.put("SPANISH", "Finalizar");
    l.put("ENGLISH", "Done");
    l.put("PORTUGUESE", "Concluído");
    Entry done = new Entry(l);
    this.addEntry("Done", done);

    HashMap<String, String> m = new HashMap<String, String>();
    m = new HashMap<String, String>();
    m.put("SPANISH", "Mostrar en el mapa");
    m.put("ENGLISH", "Show on Map");
    m.put("PORTUGUESE", "Mostrar no mapa");
    Entry showOnMap = new Entry(m);
    this.addEntry("Show on Map", showOnMap);

    HashMap<String, String> n = new HashMap<String, String>();
    n = new HashMap<String, String>();
    n.put("SPANISH", "Ver información");
    n.put("ENGLISH", "View Info");
    n.put("PORTUGUESE", "Ver informações");
    Entry viewInfo = new Entry(n);
    this.addEntry("View Info", viewInfo);

    HashMap<String, String> o = new HashMap<String, String>();
    o = new HashMap<String, String>();
    o.put("SPANISH", "Cerrar sesión");
    o.put("ENGLISH", "Log off");
    o.put("PORTUGUESE", "Terminar sessão");
    Entry logOff = new Entry(o);
    this.addEntry("Log off", logOff);

    HashMap<String, String> p = new HashMap<String, String>();
    p = new HashMap<String, String>();
    p.put("SPANISH", "Editar Mapa");
    p.put("ENGLISH", "Edit Map");
    p.put("PORTUGUESE", "Editar Mapa");
    Entry editMap = new Entry(p);
    this.addEntry("Edit Map", editMap);

    HashMap<String, String> q = new HashMap<String, String>();
    q = new HashMap<String, String>();
    q.put("SPANISH", "Crear");
    q.put("ENGLISH", "Create");
    q.put("PORTUGUESE", "Criar");
    Entry create = new Entry(q);
    this.addEntry("Create", create);

    HashMap<String, String> r = new HashMap<String, String>();
    r = new HashMap<String, String>();
    r.put("SPANISH", "Eliminar");
    r.put("ENGLISH", "Delete");
    r.put("PORTUGUESE", "Eliminar");
    Entry delete = new Entry(r);
    this.addEntry("Delete", delete);

    HashMap<String, String> t = new HashMap<String, String>();
    t = new HashMap<String, String>();
    t.put("SPANISH", "Guardar");
    t.put("ENGLISH", "Save");
    t.put("PORTUGUESE", "Guardar");
    Entry save = new Entry(q);
    this.addEntry("Save", save);

    HashMap<String, String> u = new HashMap<String, String>();
    u = new HashMap<String, String>();
    u.put("SPANISH", "Cancelar");
    u.put("ENGLISH", "Cancel");
    u.put("PORTUGUESE", "Cancelar");
    Entry cancel = new Entry(u);
    this.addEntry("Cancelar", cancel);










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
