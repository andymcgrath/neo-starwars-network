package com.starwars.social.ecosystem;

import com.starwars.social.navigation.UniverseExplorer;
import org.neo4j.graphdb.Node;

/**
 * User: andy
 * Date: 9/30/12
 */

public class UniverseNodes {

  /**
   * Method to create new Person node
   * - First checks if NODE already exists
   * - If not, creates the new node and adds a new entry in the PERSONS index
   */
  public static Node createPersonNode(Universe universe, String name) {
    if (UniverseExplorer.lookupCharacter(universe, name) != null) {
      System.out.println("Found Node [" + name + "] with id [" + UniverseExplorer.lookupCharacter(universe, name).getId() + "]");
      return UniverseExplorer.lookupCharacter(universe, name);

    } else {
      System.out.println("Creating Node [" + name + "]");
      Node character = universe.getGraphDb().createNode();
      character.setProperty(UniversalConstants.NAME, name);
      universe.getGraphDb().index().forNodes("Characters").putIfAbsent(character, UniversalConstants.NAME, name);
      return character;
    }
  }

  /**
   * Method to create new Droid node
   * - First checks if NODE already exists
   * - If not, creates the new node and adds a new entry in the PERSONS index
   */
  public static Node createDroidNode(Universe universe, String name) {
    if (UniverseExplorer.lookupCharacter(universe, name) != null) {
      System.out.println("Found Node [" + name + "] with id [" + UniverseExplorer.lookupCharacter(universe, name).getId() + "]");
      return UniverseExplorer.lookupCharacter(universe, name);

    } else {
      System.out.println("Creating Node [" + name + "]");
      Node character = universe.getGraphDb().createNode();
      character.setProperty(UniversalConstants.NAME, name);
      character.setProperty(UniversalConstants.DROID, true);
      universe.getGraphDb().index().forNodes("Characters").putIfAbsent(character, UniversalConstants.NAME, name);
      return character;
    }
  }

  /**
   * Method to create new Allegiance node
   * - First checks if NODE already exists
   * - If not, creates the new node and adds a new entry in the Allegiances index
   */
  public static Node createAllegianceNode(Universe universe, String allegiance) {
    if (UniverseExplorer.lookupAllegiance(universe, allegiance) != null) {
      System.out.println("Found Node [" + allegiance + "] with id [" + UniverseExplorer.lookupAllegiance(universe, allegiance).getId() + "]");
      return UniverseExplorer.lookupAllegiance(universe, allegiance);

    } else {
      Node allegianceNode = universe.getGraphDb().createNode();
      allegianceNode.setProperty(UniversalConstants.NAME, allegiance);
      universe.getGraphDb().index().forNodes("Allegiances").putIfAbsent(allegianceNode, UniversalConstants.NAME, allegiance);
      return allegianceNode;
    }
  }

  /**
   * Method to create new System node
   * - First checks if NODE already exists
   * - If not, creates the new node and adds a new entry in the Systems index
   * - This nodes also carries a second property of "Distance From Core", core being the Galactic Core
   */
  public static Node createSystemNode(Universe universe, String system, Integer distanceFromCore) {
    if (UniverseExplorer.lookupSystem(universe, system) != null) {
      System.out.println("Found Node [" + system + "] with id [" + UniverseExplorer.lookupSystem(universe, system).getId() + "]");
      return UniverseExplorer.lookupSystem(universe, system);

    } else {
      Node systemNode = universe.getGraphDb().createNode();
      systemNode.setProperty(UniversalConstants.NAME, system);
      systemNode.setProperty(UniversalConstants.DISTANCE, distanceFromCore);
      universe.getGraphDb().index().forNodes("Systems").putIfAbsent(systemNode, UniversalConstants.NAME, system);
      return systemNode;

    }
  }
}
