package com.starwars.social;

import com.starwars.social.ecosystem.RelationshipTypes;
import com.starwars.social.ecosystem.Universe;
import com.starwars.social.ecosystem.UniverseNodes;
import com.starwars.social.navigation.UniverseExplorer;
import org.junit.Test;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.Map;

import static com.starwars.social.ecosystem.UniversalConstants.LUKE;
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
  }

  @Test
  public void createRelationshipNode() {
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
}
