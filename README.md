Navigating the Star Wars Universe with Neo4j
============================================

This project supports the blog entry at: http://solutiondesign.com/navigating-the-star-wars-universe-with-neo4j/

**Universe.java**: The primary class that seeds the "Universe" with people, places of origin, and relationships to other characters.

This series of method calls does the following:
* **createSytems()**: Adds planet nodes to the graph to represent the home worlds (or planets that the character has spent a significant amount of time) and includes a property that holds the distance from the galactic center in parsecs.
* **populateUniverse()**: Adds the character nodes to the graph.
* **establishPolitics()**:  Adds allegiance nodes that represent different convictions held by the various characters, examples include: Jedi, Sith, Empire and Rebellion.
* **establishRelationship()**: Binds the character nodes with relationship types: FRIENDS_WITH, KNOWS or TEACHES
* **associateAllegiances()**: Creates relationships between  characters and allegiances using the relationship type DEVOTED_TO.
* **designateHomeWorld()**:  Finally, relationships are created between characters and planet created in the createSystems() method using relationship type LIVED_ON.

**UniverseNodes.java**: Encapsulates methods that deal with nodes

**UniverseRelationships.java**: Encapsulates methods that handle relationships

**UniverseExplorer.java**: Encapsulates the methods to query the graph using Cypher