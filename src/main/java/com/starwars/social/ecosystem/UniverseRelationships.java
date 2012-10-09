package com.starwars.social.ecosystem;

import com.starwars.social.navigation.UniverseExplorer;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.Node;

/**
 * User: andy
 * Date: 9/30/12
 */

public class UniverseRelationships {

  public static void chooseSides(Universe universe, Node person, Node allegiance) {

    if (UniverseExplorer.lookupAllegianceRelationship(universe, person, allegiance).iterator().hasNext()) {
      System.out.println("\nFound Existing Relationship [" + allegiance.getProperty("name") + "] with  [" + person.getProperty("name") + "]\n");

    } else {
      System.out.println("Creating unique allegiance between " + person.getProperty("name") + " and " + allegiance.getProperty("name"));

      ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
      String query = "START left=node(" + person.getId() + "), right=node(" + allegiance.getId() + ") CREATE UNIQUE left-[r:" + RelationshipTypes.DEVOTED_TO + "]->right RETURN left, r, right";
      engine.execute(query);
    }
  }

  public static void makeFriends(Universe universe, Node person, Node friend) {
    if (UniverseExplorer.lookupFriendship(universe, person, friend).iterator().hasNext()) {
      System.out.println("\nFound Existing Friendship between [" + friend.getProperty("name") + "] and [" + person.getProperty("name") + "]\n");

    } else {
      System.out.println("Creating unique friendship between " + person.getProperty("name") + " and " + friend.getProperty("name"));
      ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
      String query = "START left=node(" + person.getId() + "), right=node(" + friend.getId() + ") CREATE UNIQUE left-[r:" + RelationshipTypes.FRIENDS_WITH + "]->right RETURN left, r, right";
      engine.execute(query);
    }
  }

  public static void callsHome(Universe universe, Node person, Node system) {
    if (UniverseExplorer.lookupSystemRelatioinship(universe, person, system).iterator().hasNext()) {
      System.out.println("\nFound Existing Relationship [" + system.getProperty("name") + "] with [" + person.getProperty("name") + "]\n");

    } else {
      System.out.println("Creating unique geographic reference between " + person.getProperty("name") + " and " + system.getProperty("name"));
      ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
      String query = "START left=node(" + person.getId() + "), right=node(" + system.getId() + ") CREATE UNIQUE left-[r:" + RelationshipTypes.LIVED_ON + "]->right RETURN left, r, right";
      engine.execute(query);
    }
  }

  public static void makeAcquaintances(Universe universe, Node person, Node acquaintance) {
    if (UniverseExplorer.lookupAcquaintance(universe, person, acquaintance).iterator().hasNext()) {
      System.out.println("\nFound Existing Acquiantance between [" + acquaintance.getProperty("name") + "] and [" + person.getProperty("name") + "]\n");

    } else {
      System.out.println("Creating unique acquaintance between " + person.getProperty("name") + " and " + acquaintance.getProperty("name"));
      ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
      String query = "START left=node(" + person.getId() + "), right=node(" + acquaintance.getId() + ") CREATE UNIQUE left-[r:" + RelationshipTypes.KNOWS + "]-right RETURN left, r, right";
      engine.execute(query);
    }
  }

  public static void teaches(Universe universe, Node person, Node student) {
    if (UniverseExplorer.lookupTeacherRelationship(universe, person, student).iterator().hasNext()) {
      System.out.println("\nFound Existing Mentorship between [" + person.getProperty("name") + "] and [" + student.getProperty("name") + "]\n");

    } else {
      System.out.println("Creating unique mentorship between " + person.getProperty("name") + " and " + student.getProperty("name"));
      ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
      String query = "START left=node(" + person.getId() + "), right=node(" + student.getId() + ") CREATE UNIQUE left-[r:" + RelationshipTypes.TEACHES + "]->right RETURN left, r, right";
      engine.execute(query);
    }
  }
}
