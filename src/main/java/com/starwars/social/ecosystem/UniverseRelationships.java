package com.starwars.social.ecosystem;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Node;

/**
 * User: andy
 * Date: 9/30/12
 */

public class UniverseRelationships {

  public static void chooseSides(Node person, Node allegiance) {
    person.createRelationshipTo(allegiance, RelationshipTypes.INTERESTED_IN);
  }

  public static void makeFriends(Universe universe, Node person1, Node person2) {
    System.out.println("Creating unique relationship between " + person1.getProperty("name") + " and " + person2.getProperty("name"));
    ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
    String query = "START left=node(" + person1.getId() + "), right=node(" + person2.getId() + ") CREATE UNIQUE left-[r:" + RelationshipTypes.FRIENDS_WITH + "]->right RETURN left, r, right";
    ExecutionResult result = engine.execute(query);
    System.out.println("Unique Relationship: \n" + result);
  }
}
