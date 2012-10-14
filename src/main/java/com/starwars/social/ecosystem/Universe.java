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
    this.designateHomeWorld();
  }

  private void createSystems() {
    Transaction tx = this.graphDb.beginTx();
    try {
      TATOOINE_NODE = UniverseNodes.createSystemNode(this, TATOOINE, 13184);
      DAGOBAH_NODE = UniverseNodes.createSystemNode(this, DAGOBAH, 15407);
      ALDERAAN_NODE = UniverseNodes.createSystemNode(this, ALDERAAN, 1502);
      CORUSCANT_NODE = UniverseNodes.createSystemNode(this, CORUSCANT, 3066);
      CORELLIA_NODE = UniverseNodes.createSystemNode(this, CORELLIA, 2453);
      KASHYYYK_NODE = UniverseNodes.createSystemNode(this, KASHYYYK, 9811);
      tx.success();
    } finally {
      tx.finish();
    }
  }

  private void establishPolitics() {
    Transaction tx = this.graphDb.beginTx();
    try {
      JEDI_NODE = UniverseNodes.createAllegianceNode(this, JEDI);
      SITH_NODE = UniverseNodes.createAllegianceNode(this, SITH);
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
      C3P0_NODE = UniverseNodes.createDroidNode(this, C3P0);
      R2D2_NODE = UniverseNodes.createDroidNode(this, R2D2);
      BOBA_FETT_NODE = UniverseNodes.createPersonNode(this, BOBA_FETT);
      JABBA_NODE = UniverseNodes.createPersonNode(this, JABBA);
      GREEDO_NODE = UniverseNodes.createPersonNode(this, GREEDO);
      CHEWBACCA_NODE = UniverseNodes.createPersonNode(this, CHEWBACCA);
      tx.success();
    } finally {
      tx.finish();
    }
  }

  private void designateHomeWorld() {
    Transaction tx = this.graphDb.beginTx();
    try {
      UniverseRelationships.callsHome(this, LUKE_NODE, TATOOINE_NODE);
      UniverseRelationships.callsHome(this, HAN_NODE, CORELLIA_NODE);
      UniverseRelationships.callsHome(this, LEIA_NODE, ALDERAAN_NODE);
      UniverseRelationships.callsHome(this, YODA_NODE, DAGOBAH_NODE);
      UniverseRelationships.callsHome(this, VADER_NODE, TATOOINE_NODE);
      UniverseRelationships.callsHome(this, CHEWBACCA_NODE, KASHYYYK_NODE);
      UniverseRelationships.callsHome(this, OBI_WAN_NODE, TATOOINE_NODE);
      UniverseRelationships.callsHome(this, TARKIN_NODE, CORUSCANT_NODE);
      tx.success();
    } finally {
      tx.finish();
    }
  }

  private void associateAllegiances() {
    Transaction tx = this.graphDb.beginTx();
    try {
      UniverseRelationships.chooseSides(this, LUKE_NODE, JEDI_NODE);
      UniverseRelationships.chooseSides(this, LUKE_NODE, REBELLION_NODE);
      UniverseRelationships.chooseSides(this, LUKE_NODE, LIGHT_SIDE_NODE);
      UniverseRelationships.chooseSides(this, VADER_NODE, SITH_NODE);
      UniverseRelationships.chooseSides(this, VADER_NODE, EMPIRE_NODE);
      UniverseRelationships.chooseSides(this, VADER_NODE, DARK_SIDE_NODE);
      UniverseRelationships.chooseSides(this, LEIA_NODE, REBELLION_NODE);
      UniverseRelationships.chooseSides(this, HAN_NODE, REBELLION_NODE);
      UniverseRelationships.chooseSides(this, TARKIN_NODE, EMPIRE_NODE);
      UniverseRelationships.chooseSides(this, BOBA_FETT_NODE, HIGHEST_BIDDER_NODE);
      UniverseRelationships.chooseSides(this, OBI_WAN_NODE, JEDI_NODE);
      UniverseRelationships.chooseSides(this, OBI_WAN_NODE, REBELLION_NODE);
      UniverseRelationships.chooseSides(this, OBI_WAN_NODE, LIGHT_SIDE_NODE);
      UniverseRelationships.chooseSides(this, YODA_NODE, JEDI_NODE);
      UniverseRelationships.chooseSides(this, YODA_NODE, REBELLION_NODE);
      UniverseRelationships.chooseSides(this, YODA_NODE, LIGHT_SIDE_NODE);
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

      UniverseRelationships.teaches(this, YODA_NODE, OBI_WAN_NODE);
      UniverseRelationships.teaches(this, YODA_NODE, LUKE_NODE);
      UniverseRelationships.teaches(this, OBI_WAN_NODE, LUKE_NODE);
      UniverseRelationships.makeAcquaintances(this, OBI_WAN_NODE, VADER_NODE);

      UniverseRelationships.makeAcquaintances(this, TARKIN_NODE, VADER_NODE);
      UniverseRelationships.makeAcquaintances(this, VADER_NODE, YODA_NODE);
      UniverseRelationships.makeAcquaintances(this, VADER_NODE, LEIA_NODE);
      UniverseRelationships.makeAcquaintances(this, LEIA_NODE, LUKE_NODE);
      UniverseRelationships.makeAcquaintances(this, LUKE_NODE, YODA_NODE);

      tx.success();
    } finally {
      tx.finish();
    }
  }

  public GraphDatabaseService getGraphDb() {
    return graphDb;
  }

}
