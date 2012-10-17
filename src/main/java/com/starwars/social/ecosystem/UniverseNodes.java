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
   * - If not, creates the new node and adds the name property
   */
  public static Node createPersonNode(Universe universe, String name) {
    if (UniverseExplorer.lookupNode(universe, name) != null) {
      System.out.println("Found Node [" + name + "] with id [" + UniverseExplorer.lookupNode(universe, name).getId() + "]");
      return UniverseExplorer.lookupNode(universe, name);

    } else {
      System.out.println("Creating Node [" + name + "]");
      Node character = universe.getGraphDb().createNode();
      character.setProperty(UniversalConstants.NAME, name);
      return character;
    }
  }

  /**
   * Method to create new Droid node
   * - First checks if NODE already exists
   * - If not, creates the new node and adds the name property
   */
  public static Node createDroidNode(Universe universe, String name) {
    if (UniverseExplorer.lookupNode(universe, name) != null) {
      System.out.println("Found Node [" + name + "] with id [" + UniverseExplorer.lookupNode(universe, name).getId() + "]");
      return UniverseExplorer.lookupNode(universe, name);

    } else {
      System.out.println("Creating Node [" + name + "]");
      Node character = universe.getGraphDb().createNode();
      character.setProperty(UniversalConstants.NAME, name);
      character.setProperty(UniversalConstants.DROID, true);
      return character;
    }
  }

  /**
   * Method to create new Allegiance node
   * - First checks if NODE already exists
   * - If not, creates the new node and adds the name property
   */
  public static Node createAllegianceNode(Universe universe, String allegiance) {
    if (UniverseExplorer.lookupNode(universe, allegiance) != null) {
      System.out.println("Found Node [" + allegiance + "] with id [" + UniverseExplorer.lookupNode(universe, allegiance).getId() + "]");
      return UniverseExplorer.lookupNode(universe, allegiance);

    } else {
      Node allegianceNode = universe.getGraphDb().createNode();
      allegianceNode.setProperty(UniversalConstants.NAME, allegiance);
      return allegianceNode;
    }
  }

  /**
   * Method to create new System node
   * - First checks if NODE already exists
   * - If not, creates the new node and adds the name property
   * - This nodes also carries a second property of "Distance From Core", core being the Galactic Core
   */
  public static Node createSystemNode(Universe universe, String system, Integer distanceFromCore) {
    if (UniverseExplorer.lookupNode(universe, system) != null) {
      System.out.println("Found Node [" + system + "] with id [" + UniverseExplorer.lookupNode(universe, system).getId() + "]");
      return UniverseExplorer.lookupNode(universe, system);

    } else {
      Node systemNode = universe.getGraphDb().createNode();
      systemNode.setProperty(UniversalConstants.NAME, system);
      systemNode.setProperty(UniversalConstants.DISTANCE, distanceFromCore);
      return systemNode;
    }
  }
}
