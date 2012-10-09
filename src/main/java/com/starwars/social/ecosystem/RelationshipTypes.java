package com.starwars.social.ecosystem;

import org.neo4j.graphdb.RelationshipType;

/**
 * User: andy
 * Date: 9/30/12
 */

public enum RelationshipTypes implements RelationshipType {
  FRIENDS_WITH, DEVOTED_TO, KNOWS, TEACHES, LIVED_ON
}
