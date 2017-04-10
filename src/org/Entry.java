package org;

import java.util.HashMap;

/**
 * Created by Brandon on 4/4/2017.
 */
public class Entry {

    public HashMap<Language, String> languageStringMap;

    public Entry(HashMap<Language, String> languageStringMap){
        this.languageStringMap = languageStringMap;
    }

    /**
     * Adds a String for a org.Language to the languageStringMap for the org.Entry object.
     *
     * @param language: The given language that the String is interpreted in.
     * @param string: The String that is displayed in the given org.Language.
     */
    public void addString(Language language,String string){
        this.languageStringMap.put(language, string);
    }

    /**
     * Gets the String given a org.Language to return in.
     *
     * @param language: Specifies the org.Language that the String should be interpreted in.
     * @return: Returns the String in the specified org.Language.
     */
    public String getString(Language language){
        if(this.languageStringMap.containsKey(language)){
            return this.languageStringMap.get(language);
        } else {
            System.out.println("org.Language not found.");
            return "";
        }
    }
}
