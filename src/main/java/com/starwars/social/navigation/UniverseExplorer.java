package com.starwars.social.navigation;

import com.starwars.social.ecosystem.RelationshipTypes;
import com.starwars.social.ecosystem.Universe;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.Node;

import static com.starwars.social.ecosystem.UniversalConstants.PERSON_NAME;

/**
 * User: andy
 * Date: 9/30/12
 */

public class UniverseExplorer {

  public static ExecutionResult getFriendsOfFriends(Universe universe, String name) {
    ExecutionEngine engine = new ExecutionEngine(universe.getGraphDb());
    String query = "START p=node:node_auto_index(name='" + name + "') " + "MATCH p-[:" + RelationshipTypes.FRIENDS_WITH + "]->()-[:" + RelationshipTypes.FRIENDS_WITH + "]->fof " + "RETURN p, fof.name";
    System.out.println("Query = " + query);
    ExecutionResult result = engine.execute(query);
    System.out.println(result.toString());
    return result;
  }

  public static Node lookupPerson(Universe universe, String name) {
    return universe.getGraphDb().index().forNodes("Persons").get(PERSON_NAME, name).getSingle();
  }
}
