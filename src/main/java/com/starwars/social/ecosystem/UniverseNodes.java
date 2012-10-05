package com.starwars.social.ecosystem;

import com.starwars.social.navigation.UniverseExplorer;
import org.neo4j.graphdb.Node;

/**
 * User: andy
 * Date: 9/30/12
 */

public class UniverseNodes {

  /**
   * Method to create new node
   * - First checks if NODE already exists
   * - If not, creates the new node and adds a new entry in the PERSONS index
   */
  public static Node createPersonNode(Universe universe, String name) {
    if (UniverseExplorer.lookupPerson(universe, name) != null) {
      System.out.println("Found Node [" + name + "] with id [" + UniverseExplorer.lookupPerson(universe, name).getId() + "]");
      return UniverseExplorer.lookupPerson(universe, name);

    } else {
      System.out.println("Creating Node [" + name + "]");
      Node person = universe.getGraphDb().createNode();
      person.setProperty(UniversalConstants.PERSON_NAME, name);
      universe.getGraphDb().index().forNodes("Persons").putIfAbsent(person, UniversalConstants.PERSON_NAME, name);
      return person;
    }
  }

  public static Node createAllegianceNode(Universe universe, String allegiance) {
    Node allegianceNode = universe.getGraphDb().createNode();
    allegianceNode.setProperty("name", allegiance);
    universe.getGraphDb().index().forNodes("Allegiances").putIfAbsent(allegianceNode, "allegiance", allegiance);
    return allegianceNode;
  }
}
