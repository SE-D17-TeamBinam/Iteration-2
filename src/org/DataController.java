package org;

import java.util.ArrayList;
import java.util.Dictionary;
//import Statements
/**
 * @author ajnagal and aramirez2
 *
 * @since 1.2
*/

/**
 * The purpose of this class is to take in nodes and calculate the shortest path between
 * the start and destination.
 *
 */
public class DataController {
  ListPoints[] floors; //array of ListNodes that make up a whole floor?
  public static boolean ttsInited = false;

  //Constructor
  public DataController(ListPoints[] floors){
  this.floors = floors;
  }

//Methods

  /**
   * synthesize any string to spoken language
   * @param toSpeak: The string to synthesize
   * @author Tom
   */
  public static void textToSpeech(String toSpeak){
    if(!ttsInited) {
      voce.SpeechInterface.init("../../lib", true, false, "", "");
    }
    voce.SpeechInterface.synthesize(toSpeak);
  }

  /**
   * Shut down the TTS library
   */
  public static void tearDownTTS(){
    if(!ttsInited){
      return;
    }
    voce.SpeechInterface.destroy();
    ttsInited = false;
  }

  public Dictionary getInfo(Point lol){
    return null;
  }

}
