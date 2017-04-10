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
    setupDictionary();
    stringEntryMap = new HashMap<String, Entry>();
    HashMap<Language, String> h = new HashMap<Language, String>();
    h.put(Language.SPANISH, "Espalda");
    h.put(Language.ENGLISH, "Back");
    h.put(Language.PORTUGESE, "Costas");
    Entry e = new Entry(h);
    this.addEntry("Back", e);

    HashMap<Language, String> s = new HashMap<Language, String>();
    s = new HashMap<Language, String>();
    s.put(Language.SPANISH, "Nombre de usuario");
    s.put(Language.ENGLISH, "Username");
    s.put(Language.PORTUGESE, "Nome de usuário");
    Entry g = new Entry(s);
    this.addEntry("Username", g);

    HashMap<Language, String> a = new HashMap<Language, String>();
    a = new HashMap<Language, String>();
    a.put(Language.SPANISH, "Contraseña");
    a.put(Language.ENGLISH, "Password");
    a.put(Language.PORTUGESE, "Palavra-passe");
    Entry password = new Entry(a);
    this.addEntry("Password", password);

    HashMap<Language, String> b = new HashMap<Language, String>();
    b = new HashMap<Language, String>();
    b.put(Language.SPANISH, "Mapa");
    b.put(Language.ENGLISH, "Map");
    b.put(Language.PORTUGESE, "Mapa");
    Entry map = new Entry(b);
    this.addEntry("Map", map);

    HashMap<Language, String> c = new HashMap<Language, String>();
    c = new HashMap<Language, String>();
    c.put(Language.SPANISH, "iniciar sesión");
    c.put(Language.ENGLISH, "Login");
    c.put(Language.PORTUGESE, "iniciar sessão");
    Entry login = new Entry(c);
    this.addEntry("Login", login);

    HashMap<Language, String> d = new HashMap<Language, String>();
    d = new HashMap<Language, String>();
    d.put(Language.SPANISH, "Buscar");
    d.put(Language.ENGLISH, "Search");
    d.put(Language.PORTUGESE, "Procurar");
    Entry search = new Entry(d);
    this.addEntry("Search", search);

    HashMap<Language, String> e1 = new HashMap<Language, String>();
    e1 = new HashMap<Language, String>();
    e1.put(Language.SPANISH, "Piso");
    e1.put(Language.ENGLISH, "Floor");
    e1.put(Language.PORTUGESE, "Chão");
    Entry floor = new Entry(e1);
    this.addEntry("Floor", floor);

    HashMap<Language, String> f = new HashMap<Language, String>();
    f = new HashMap<Language, String>();
    f.put(Language.SPANISH, "Comienzo");
    f.put(Language.ENGLISH, "Start");
    f.put(Language.PORTUGESE, "Começar");
    Entry start = new Entry(f);
    this.addEntry("Start", start);

    HashMap<Language, String> g1 = new HashMap<Language, String>();
    g1 = new HashMap<Language, String>();
    g1.put(Language.SPANISH, "Fin");
    g1.put(Language.ENGLISH, "End");
    g1.put(Language.PORTUGESE, "Fim");
    Entry end = new Entry(g1);
    this.addEntry("End", end);

    HashMap<Language, String> i = new HashMap<Language, String>();
    i = new HashMap<Language, String>();
    i.put(Language.SPANISH, "Zoom");
    i.put(Language.ENGLISH, "Zoom");
    i.put(Language.PORTUGESE, "Zoom");
    Entry zoom = new Entry(i);
    this.addEntry("Zoom", zoom);

    HashMap<Language, String> j = new HashMap<Language, String>();
    j = new HashMap<Language, String>();
    j.put(Language.SPANISH, "Seleccionado");
    j.put(Language.ENGLISH, "Selected");
    j.put(Language.PORTUGESE, "Seleccionado");
    Entry selected = new Entry(d);
    this.addEntry("Selected", selected);

    HashMap<Language, String> k = new HashMap<Language, String>();
    k = new HashMap<Language, String>();
    k.put(Language.SPANISH, "Ir a");
    k.put(Language.ENGLISH, "Go");
    k.put(Language.PORTUGESE, "Go");
    Entry go = new Entry(k);
    this.addEntry("Go", go);

    HashMap<Language, String> l = new HashMap<Language, String>();
    l = new HashMap<Language, String>();
    l.put(Language.SPANISH, "Finalizar");
    l.put(Language.ENGLISH, "Done");
    l.put(Language.PORTUGESE, "Concluído");
    Entry done = new Entry(l);
    this.addEntry("Done", done);

    HashMap<Language, String> m = new HashMap<Language, String>();
    m = new HashMap<Language, String>();
    m.put(Language.SPANISH, "Mostrar en el mapa");
    m.put(Language.ENGLISH, "Show on Map");
    m.put(Language.PORTUGESE, "Mostrar no mapa");
    Entry showOnMap = new Entry(m);
    this.addEntry("Show on Map", showOnMap);

    HashMap<Language, String> n = new HashMap<Language, String>();
    n = new HashMap<Language, String>();
    n.put(Language.SPANISH, "Ver información");
    n.put(Language.ENGLISH, "View Info");
    n.put(Language.PORTUGESE, "Ver informações");
    Entry viewInfo = new Entry(n);
    this.addEntry("View Info", viewInfo);

    HashMap<Language, String> o = new HashMap<Language, String>();
    o = new HashMap<Language, String>();
    o.put(Language.SPANISH, "Cerrar sesión");
    o.put(Language.ENGLISH, "Log off");
    o.put(Language.PORTUGESE, "Terminar sessão");
    Entry logOff = new Entry(o);
    this.addEntry("Log off", logOff);

    HashMap<Language, String> p = new HashMap<Language, String>();
    p = new HashMap<Language, String>();
    p.put(Language.SPANISH, "Editar Mapa");
    p.put(Language.ENGLISH, "Edit Map");
    p.put(Language.PORTUGESE, "Editar Mapa");
    Entry editMap = new Entry(p);
    this.addEntry("Edit Map", editMap);

    HashMap<Language, String> q = new HashMap<Language, String>();
    q = new HashMap<Language, String>();
    q.put(Language.SPANISH, "Crear");
    q.put(Language.ENGLISH, "Create");
    q.put(Language.PORTUGESE, "Criar");
    Entry create = new Entry(q);
    this.addEntry("Create", create);

    HashMap<Language, String> r = new HashMap<Language, String>();
    r = new HashMap<Language, String>();
    r.put(Language.SPANISH, "Eliminar");
    r.put(Language.ENGLISH, "Delete");
    r.put(Language.PORTUGESE, "Eliminar");
    Entry delete = new Entry(r);
    this.addEntry("Delete", delete);

    HashMap<Language, String> t = new HashMap<Language, String>();
    t = new HashMap<Language, String>();
    t.put(Language.SPANISH, "Guardar");
    t.put(Language.ENGLISH, "Save");
    t.put(Language.PORTUGESE, "Guardar");
    Entry save = new Entry(q);
    this.addEntry("Save", save);

    HashMap<Language, String> u = new HashMap<Language, String>();
    u = new HashMap<Language, String>();
    u.put(Language.SPANISH, "Cancelar");
    u.put(Language.ENGLISH, "Cancel");
    u.put(Language.PORTUGESE, "Cancelar");
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
