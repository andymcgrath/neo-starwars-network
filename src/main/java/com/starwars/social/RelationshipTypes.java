package com.starwars.social;

import org.neo4j.graphdb.RelationshipType;

/**
 * User: andy
 * Date: 9/30/12
 */

public enum RelationshipTypes implements RelationshipType {
  FRIENDS_WITH, RELATE_TO, LOVES, KNOWS, INTERESTED_IN, EMPLOYS, TEACHES
}
