package com.starwars.social.ecosystem;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

import static com.starwars.social.ecosystem.UniversalConstants.*;

/**
 * User: andy
 * Date: 9/30/12
 */

public class Universe {

  private GraphDatabaseService graphDb;

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
    this.createSystems();
    this.populateUniverse();
    this.establishPolitics();
    this.establishRelationships();
    this.associateAllegiances();
  }

  private void createSystems() {

  }

  private void establishPolitics() {
    Transaction tx = this.graphDb.beginTx();
    try {
      JEDI_NODE = UniverseNodes.createAllegianceNode(this, JEDI);
      REBELLION_NODE = UniverseNodes.createAllegianceNode(this, REBELLION);
      EMPIRE_NODE = UniverseNodes.createAllegianceNode(this, EMPIRE);
      HIGHEST_BIDDER_NODE = UniverseNodes.createAllegianceNode(this, HIGHEST_BIDDER);
      DARK_SIDE_NODE = UniverseNodes.createAllegianceNode(this, DARK_SIDE);
      LIGHT_SIDE_NODE = UniverseNodes.createAllegianceNode(this, LIGHT_SIDE);
      tx.success();
    } finally {
      tx.finish();
    }
  }

  private void populateUniverse() {
    Transaction tx = this.graphDb.beginTx();
    try {
      LUKE_NODE = UniverseNodes.createPersonNode(this, LUKE);
      HAN_NODE = UniverseNodes.createPersonNode(this, HAN);
      LEIA_NODE = UniverseNodes.createPersonNode(this, LEIA);
      OBI_WAN_NODE = UniverseNodes.createPersonNode(this, OBI_WAN);
      YODA_NODE = UniverseNodes.createPersonNode(this, YODA);
      TARKIN_NODE = UniverseNodes.createPersonNode(this, TARKIN);
      VADER_NODE = UniverseNodes.createPersonNode(this, VADER);
      C3P0_NODE = UniverseNodes.createPersonNode(this, C3P0);
      R2D2_NODE = UniverseNodes.createPersonNode(this, R2D2);
      BOBA_FETT_NODE = UniverseNodes.createPersonNode(this, BOBA_FETT);
      JABBA_NODE = UniverseNodes.createPersonNode(this, JABBA);
      GREEDO_NODE = UniverseNodes.createPersonNode(this, GREEDO);
      CHEWBACCA_NODE = UniverseNodes.createPersonNode(this, CHEWBACCA);
      tx.success();
    } finally {
      tx.finish();
    }
  }

  private void associateAllegiances() {
    Transaction tx = this.graphDb.beginTx();
    try {
      UniverseRelationships.chooseSides(LUKE_NODE, JEDI_NODE);
      UniverseRelationships.chooseSides(LUKE_NODE, REBELLION_NODE);
      UniverseRelationships.chooseSides(LUKE_NODE, LIGHT_SIDE_NODE);
      UniverseRelationships.chooseSides(VADER_NODE, JEDI_NODE);
      UniverseRelationships.chooseSides(VADER_NODE, EMPIRE_NODE);
      UniverseRelationships.chooseSides(VADER_NODE, DARK_SIDE_NODE);
      tx.success();
    } finally {
      tx.finish();
    }
  }

  private void establishRelationships() {
    Transaction tx = this.graphDb.beginTx();
    try {
      UniverseRelationships.makeFriends(this, LUKE_NODE, HAN_NODE);
      UniverseRelationships.makeFriends(this, LUKE_NODE, LEIA_NODE);
      UniverseRelationships.makeFriends(this, HAN_NODE, CHEWBACCA_NODE);
      tx.success();
    } finally {
      tx.finish();
    }
  }

  public GraphDatabaseService getGraphDb() {
    return graphDb;
  }

}
