package com.starwars.social;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

/**
 * User: andy
 * Date: 9/30/12
 */

public class Universe {
  public static final String LUKE = "Luke Skywalker";
  public static final String HAN = "Han Solo";
  public static final String LEIA = "Princess Leia Organa";
  public static final String OBI_WAN = "Obi-Wan Kenobi";
  public static final String YODA = "Yoda";
  public static final String TARKIN = "Grand Moff Tarkin";
  public static final String VADER = "Darth Vader";
  public static final String C3P0 = "C-3PO";
  public static final String R2D2 = "R2-D2";
  public static final String BOBA_FETT = "Boba Fett";
  public static final String JABBA = "Jabba The Hut";
  public static final String GREEDO = "Greedo";
  public static final String CHEWBACCA = "Chewbacca";
  public static final String PERSON_NAME = "name";

  public static Node LUKE_NODE;
  public static Node HAN_NODE;
  public static Node LEIA_NODE;
  public static Node OBI_WAN_NODE;
  public static Node YODA_NODE;
  public static Node TARKIN_NODE;
  public static Node VADER_NODE;
  public static Node C3P0_NODE;
  public static Node R2D2_NODE;
  public static Node BOBA_FETT_NODE;
  public static Node JABBA_NODE;
  public static Node GREEDO_NODE;
  public static Node CHEWBACCA_NODE;

  final GraphDatabaseService graphDb;

  public Universe(String databasePath) {
    this.graphDb = new GraphDatabaseFactory().newEmbeddedDatabaseBuilder(databasePath).setConfig(GraphDatabaseSettings.node_auto_indexing, "true").setConfig(GraphDatabaseSettings.node_keys_indexable, "name")
        .setConfig(GraphDatabaseSettings.relationship_auto_indexing, "true").setConfig(GraphDatabaseSettings.relationship_keys_indexable, "name").newGraphDatabase();
    this.createStarWarsUniverse();
    registerShutdownHook(this.graphDb);
  }

  public void shutdown() {
    this.graphDb.shutdown();
  }

  private static void registerShutdownHook(final GraphDatabaseService graphDb) {
    Runtime.getRuntime().addShutdownHook(new Thread() {
      @Override
      public void run() {
        graphDb.shutdown();
      }
    });
  }

  public void createStarWarsUniverse() {
    this.populateUniverse();
    this.establishRelationships();
    this.associateAllegiances();
  }

  private void populateUniverse() {
    Transaction tx = this.graphDb.beginTx();
    try {
      LUKE_NODE = this.createPersonNode(LUKE);
      HAN_NODE = this.createPersonNode(HAN);
      LEIA_NODE = this.createPersonNode(LEIA);
      OBI_WAN_NODE = this.createPersonNode(OBI_WAN);
      YODA_NODE = this.createPersonNode(YODA);
      TARKIN_NODE = this.createPersonNode(TARKIN);
      VADER_NODE = this.createPersonNode(VADER);
      C3P0_NODE = this.createPersonNode(C3P0);
      R2D2_NODE = this.createPersonNode(R2D2);
      BOBA_FETT_NODE = this.createPersonNode(BOBA_FETT);
      JABBA_NODE = this.createPersonNode(JABBA);
      GREEDO_NODE = this.createPersonNode(GREEDO);
      CHEWBACCA_NODE = this.createPersonNode(CHEWBACCA);
      tx.success();
    } finally {
      tx.finish();
    }
    System.out.println("Luke Node: " + LUKE_NODE.getProperty("name"));
    System.out.println("Luke Node: " + LUKE_NODE.getId());
  }

  private void establishRelationships() {
    Transaction tx = this.graphDb.beginTx();
    try {
      makeFriends(LUKE_NODE, HAN_NODE);
      makeFriends(LUKE_NODE, LEIA_NODE);
      makeFriends(HAN_NODE, CHEWBACCA_NODE);
      tx.success();
    } finally {
      tx.finish();
    }
  }

  private void associateAllegiances() {

  }

  /*
   * Method to create new node
   * - First checks if NODE already exists
   * - If not, creates the new node and adds a new entry in the PERSONS index
   */
  public Node createPersonNode(String name) {
    if (this.lookupPerson(name) != null) {
      System.out.println("Found Node [" + name + "] with id [" + this.lookupPerson(name).getId() + "]");
      return this.lookupPerson(name);

    } else {
      System.out.println("Creating Node [" + name + "]");
      Node person = this.graphDb.createNode();
      person.setProperty(PERSON_NAME, name);
      this.graphDb.index().forNodes("Persons").putIfAbsent(person, PERSON_NAME, name);
      return person;
    }
  }

  public void makeFriends(Node person1, Node person2) {
    System.out.println("Creating unique relationship between " + person1.getProperty("name") + " and " + person2.getProperty("name"));
    ExecutionEngine engine = new ExecutionEngine(this.graphDb);
    String query = "START left=node(" + person1.getId() + "), right=node(" + person2.getId() + ") CREATE UNIQUE left-[r:" + RelationshipTypes.FRIENDS_WITH + "]->right RETURN left, r, right";
    ExecutionResult result = engine.execute(query);
    System.out.println("Unique Relationship: \n" + result);
  }

  public Node createInterestNode(String interest) {
    Node interestNode = this.graphDb.createNode();
    interestNode.setProperty("name", interest);
    this.graphDb.index().forNodes("Interests").putIfAbsent(interestNode, "interest", interest);
    return interestNode;
  }

  public void addInterest(Node person, Node interest) {
    person.createRelationshipTo(interest, RelationshipTypes.INTERESTED_IN);
  }

  public Node lookupPerson(String name) {
    return this.graphDb.index().forNodes("Persons").get(PERSON_NAME, name).getSingle();
  }

  public ExecutionResult getFriendsOfFriends(String name) {
    ExecutionEngine engine = new ExecutionEngine(this.graphDb);
    String query = "START p=node:node_auto_index(name='" + name + "') " + "MATCH p-[:" + RelationshipTypes.FRIENDS_WITH + "]->()-[:" + RelationshipTypes.FRIENDS_WITH + "]->fof " + "RETURN p, fof.name";
    System.out.println("Query = " + query);
    ExecutionResult result = engine.execute(query);
    System.out.println(result.toString());
    return result;
  }

  public ExecutionResult helloUniverse() {
    ExecutionEngine engine = new ExecutionEngine(this.graphDb);

    String query = "START n=node(*) MATCH n-[r?]->m RETURN n,type(r),m";
    System.out.println("Query = " + query);

    ExecutionResult result;
    result = engine.execute(query);
    System.out.println(result.toString());
    return result;
  }
}
