package com.starwars.social;

import com.starwars.social.ecosystem.RelationshipTypes;
import com.starwars.social.ecosystem.Universe;
import com.starwars.social.ecosystem.UniverseNodes;
import com.starwars.social.navigation.UniverseExplorer;
import org.junit.Test;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.Map;

import static com.starwars.social.ecosystem.UniversalConstants.*;
import static com.starwars.social.ecosystem.UniverseRelationships.chooseSides;
import static com.starwars.social.ecosystem.UniverseRelationships.makeFriends;
import static org.junit.Assert.assertEquals;

/**
 * User: andy
 * Date: 10/1/12
 */
public class UniverseTests {
  private static Universe universe = new Universe("data/universe.db");

  @Test
  public void createPersonNode() {
    Transaction tx = universe.getGraphDb().beginTx();
    Node person;
    try {
      person = UniverseNodes.createPersonNode(universe, "Test Person");
      tx.success();
    } finally {
      tx.finish();
    }
    assert (person.hasProperty("name"));
    assertEquals(person.getProperty("name"), "Test Person");

    deleteNode(person);
  }

  @Test
  public void createSystemNode() {
    Transaction tx = universe.getGraphDb().beginTx();
    Node system;
    try {
      system = UniverseNodes.createSystemNode(universe, "Test System", 99999);
      tx.success();
    } finally {
      tx.finish();
    }
    assert (system.hasProperty("name"));
    assertEquals(system.getProperty("name"), "Test System");
    assertEquals(system.getProperty("distance"), 99999);

    deleteNode(system);
  }

  @Test
  public void createAllegianceNode() {
    Transaction tx = universe.getGraphDb().beginTx();
    Node allegiance;
    try {
      allegiance = UniverseNodes.createAllegianceNode(universe, "Test Allegiance");
      tx.success();
    } finally {
      tx.finish();
    }
    assert (allegiance.hasProperty("name"));
    assertEquals(allegiance.getProperty("name"), "Test Allegiance");

    deleteNode(allegiance);
  }

  @Test
  public void createFriendsWithRelationshipNode() {
    Node person;
    Node person2;
    Transaction tx = universe.getGraphDb().beginTx();

    try {
      person = UniverseNodes.createPersonNode(universe, "Test Person");
      person2 = UniverseNodes.createPersonNode(universe, "Test Person-2");
      makeFriends(universe, person, person2);
      tx.success();

    } finally {
      tx.finish();
    }

    assert (person.hasProperty("name"));
    assert (person2.hasProperty("name"));
    assertEquals(person.getProperty("name"), "Test Person");
    assertEquals(person2.getProperty("name"), "Test Person-2");
    assertEquals(person.getSingleRelationship(RelationshipTypes.FRIENDS_WITH, Direction.OUTGOING).getType(), RelationshipTypes.FRIENDS_WITH);

    deleteNodeAndRelationship(person);
    deleteNodeAndRelationship(person2);
  }

  @Test
  public void createPersonWithAllegianceNode() {
    Node person;
    Node allegiance;
    Transaction tx = universe.getGraphDb().beginTx();

    try {
      person = UniverseNodes.createPersonNode(universe, "Test Person");
      allegiance = UniverseNodes.createAllegianceNode(universe, "Test Allegiance");
      chooseSides(universe, person, allegiance);
      tx.success();

    } finally {
      tx.finish();
    }

    assert (person.hasProperty("name"));
    assert (allegiance.hasProperty("name"));
    assertEquals(person.getProperty("name"), "Test Person");
    assertEquals(allegiance.getProperty("name"), "Test Allegiance");
    assertEquals(person.getSingleRelationship(RelationshipTypes.DEVOTED_TO, Direction.OUTGOING).getType(), RelationshipTypes.DEVOTED_TO);

    deleteNodeAndRelationship(person);
  }

  @Test
  public void lukeFriendOfAFriendIsChewbacca() {
    ExecutionResult result = UniverseExplorer.getFriendsOfFriends(universe, LUKE);
    if (result.iterator().hasNext()) {
      System.out.println(result.iterator().next().get("fof.name"));
    }
    for (Map<String, Object> row : result) {
      assertEquals("Chewbacca", row.get("fof.name"));
    }

  }

  @Test
  public void tarkinsShortestPathToYoda() {
    ExecutionResult result = UniverseExplorer.getShortestPathBetweenTwoPeople(universe, TARKIN_NODE, YODA_NODE);
    if (result.iterator().hasNext()) {
      System.out.println(result);
    }
    for (Map<String, Object> row : result) {
      String expected = "(12)--[KNOWS,7]-->(13)--[KNOWS,8]-->(11)";
      assertEquals(expected, row.get("shortestPath").toString());
    }
  }

  private void deleteNode(Node node) {
    Transaction tx = universe.getGraphDb().beginTx();
    try {
      ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
      String query = "START n = node(" + node.getId() + ") DELETE n";
      System.out.println("DELETING NODE FOR:[" + node.getProperty("name") + "]");
      engine.execute(query);
      tx.success();
    } finally {
      tx.finish();
    }
  }

  private void deleteNodeAndRelationship(Node node) {
    Transaction tx = universe.getGraphDb().beginTx();
    try {
      ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
      String query = "START n = node(" + node.getId() + ")  MATCH n-[r]-() DELETE n, r";
      System.out.println("DELETING NODE & RELATIONSHIPS FOR:[" + node.getProperty("name") + "]");
      engine.execute(query);
      tx.success();
    } finally {
      tx.finish();
    }
  }
}
