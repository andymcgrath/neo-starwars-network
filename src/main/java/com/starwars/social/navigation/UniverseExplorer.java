package com.starwars.social.navigation;

import com.starwars.social.ecosystem.RelationshipTypes;
import com.starwars.social.ecosystem.Universe;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Node;

import static com.starwars.social.ecosystem.UniversalConstants.NAME;

/**
 * User: andy
 * Date: 9/30/12
 */

public class UniverseExplorer {

  public static ExecutionResult getFriendsOfFriends(Universe universe, String name) {
    ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
    String query = "START p=node:node_auto_index(name='" + name + "') MATCH p-[:" + RelationshipTypes.FRIENDS_WITH + "]->()-[:" + RelationshipTypes.FRIENDS_WITH + "]->fof " + "RETURN p, fof.name";

    ExecutionResult result = engine.execute(query);
    System.out.println(result.toString());
    return result;
  }

  public static ExecutionResult getShortestPathBetweenTwoPeople(Universe universe, Node startNode, Node endNode) {
    ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
    String query = "START startNode=node(" + startNode.getId() + "), endNode=node(" + endNode.getId() + ") MATCH shortestPath = shortestPath(startNode-[*..10]->endNode) RETURN shortestPath";
    return engine.execute(query);
  }

  public static Node lookupPerson(Universe universe, String name) {
    return universe.getGraphDb().index().forNodes("Persons").get(NAME, name).getSingle();
  }

  public static Node lookupSystem(Universe universe, String name) {
    return universe.getGraphDb().index().forNodes("Systems").get(NAME, name).getSingle();
  }

  public static Node lookupAllegiance(Universe universe, String name) {
    return universe.getGraphDb().index().forNodes("Allegiances").get(NAME, name).getSingle();
  }

  public static ExecutionResult lookupFriendship(Universe universe, Node person, Node friend) {
    ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
    String query = "START p=node(" + person.getId() + "), s=node(" + friend.getId() + ") MATCH (p)-[r:" + RelationshipTypes.FRIENDS_WITH + "]->s RETURN s.name";
    return engine.execute(query);
  }

  public static ExecutionResult lookupAcquaintance(Universe universe, Node person, Node acquaintance) {
    ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
    String query = "START p=node(" + person.getId() + "), s=node(" + acquaintance.getId() + ") MATCH (p)-[r:" + RelationshipTypes.KNOWS + "]->s RETURN s.name";
    return engine.execute(query);
  }

  public static ExecutionResult lookupSystemRelatioinship(Universe universe, Node person, Node system) {
    ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
    String query = "START p=node(" + person.getId() + "), s=node(" + system.getId() + ") MATCH (p)-[r:" + RelationshipTypes.LIVED_ON + "]->s RETURN s.name";
    return engine.execute(query);
  }

  public static ExecutionResult lookupAllegianceRelationship(Universe universe, Node person, Node allegiance) {
    ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
    String query = "START p=node(" + person.getId() + "), a=node(" + allegiance.getId() + ") MATCH (p)-[r:" + RelationshipTypes.DEVOTED_TO + "]->(a) RETURN a.name";
    return engine.execute(query);
  }

  public static ExecutionResult lookupTeacherRelationship(Universe universe, Node person, Node student) {
    ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
    String query = "START p=node(" + person.getId() + "), s=node(" + student.getId() + ") MATCH (p)-[r:" + RelationshipTypes.TEACHES + "]->s RETURN s.name";
    return engine.execute(query);
  }
}
