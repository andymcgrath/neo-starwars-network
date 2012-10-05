package com.starwars.social;

import org.junit.Test;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * User: andy
 * Date: 10/1/12
 */
public class UniverseTests {
  public static final Universe u = new Universe("data/universe.db");

  @Test
  public void createPersonNode() {
    Transaction tx = u.graphDb.beginTx();
    Node person;
    try {
      person = u.createPersonNode("Test Person");
      tx.success();
    } finally {
      tx.finish();
    }
    assert (person.hasProperty("name"));
    assertEquals(person.getProperty("name"), "Test Person");
  }

  @Test
  public void createRelationshipNode() {
    Transaction tx = u.graphDb.beginTx();
    Node person;
    Node person2;

    try {
      person = u.createPersonNode("Test Person");
      person2 = u.createPersonNode("Test Person-2");
      person.createRelationshipTo(person2, RelationshipTypes.FRIENDS_WITH);
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
  public void lukeFriendOfAFriend() {
    ExecutionResult result = u.getFriendsOfFriends(u.LUKE);
    if (result.iterator().hasNext()) {
      System.out.println(result.iterator().next().get("fof.name"));
    }

    for (Map<String, Object> row : result) {
      assertEquals("Chewbacca", row.get("fof.name"));
    }

  }
}
