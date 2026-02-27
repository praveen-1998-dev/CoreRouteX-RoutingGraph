#   CoreRouteX & Routing-Graph Microservices

[![Java CI](https://github.com/praveen-1998-dev/CoreRouteX-RoutingGraph/actions/workflows/ci.yml/badge.svg)](https://github.com/praveen-1998-dev/CoreRouteX-RoutingGraph/actions/workflows/ci.yml)

## Project Overview

This project demonstrates a **microservices-based routing system** that calculates shortest paths between locations and maintains a weighted graph of edges.

- **CoreRouteX (Host:8080->Internal:8080)**  → Main microservice where users interact. Handles:
    - Adding locations
    - Calculating distances (Haversine formula)
    - Sending edges to Routing-Graph
    - Fetching shortest distance between locations

- **Routing-Graph (Host:8081->Internal:8080)** → Helper microservice that stores edges and computes shortest paths using **Dijkstra’s algorithm**. Receives commands from CoreRouteX.

**Key highlights of this project:**
- Microservice communication
- Weighted graph implementation
- Unit and integration tests for key services
- Professional logging and clean code

---

## Technologies Used

| Layer       | Technology                     |
|------------|--------------------------------|
| Framework  | Spring Boot 3                  |
| Database   | H2 (in-memory)                 |
| Testing    | JUnit 5, Mockito               |
| Build      | Maven                           |
| Communication | REST (RestTemplate)          |
| Logging    | SLF4J                           |

---

## Architecture

+-------------------+          +-------------------+
|   CoreRouteX      |          |   Routing Graph   |
|    Host 8080      |   --->   |    Host 8081      |
|    Port 8080      |  Edge    |    Port 8080      |
+-------------------+          +-------------------+
| - Locations       |          | - Graph Edges     |
| - Haversine Dist  |          | - Shortest Path   |
| - Send Edge       |          | - Dijkstra Algo   |
+-------------------+          +-------------------+


## CoreRouteX & Routing-Graph Overview

CoreRouteX sends edges and distance data to Routing-Graph, which stores edges in its database and computes shortest distances when requested.
All user interactions happen via CoreRouteX (port 8080).

### CoreRouteX & Routing-Graph API Endpoints


1️⃣ **Add Location**  
Adds a new location with name and latitude/longitude coordinates to the system.  
**POST** http://localhost:8080/api/locations  
Content-Type: application/json

``` JSON
{
  "name": "A",
  "latitude": 12.9716,
  "longitude": 77.5946
}
```
2️⃣ Get All Locations
Fetches all locations stored in CoreRouteX.
GET http://localhost:8080/api/locations

3️⃣ Calculate Distance by Coordinates
Calculates distance (Haversine formula) between two coordinates.
POST http://localhost:8080/api/locations/distance?fromLatitude=12.9716&fromLongitude=77.5946&toLatitude=12.9352&toLongitude=77.6245

4️⃣ Calculate Distance by Location Names
Calculates distance between two existing locations by their names.
POST http://localhost:8080/api/locations/distance-by-name?from=A&to=B

5️⃣ Send Edge to Routing-Graph
Sends a weighted edge (from → to with distance) to the Routing-Graph microservice.
POST http://localhost:8080/api/locations/send-edge?from=A&to=B&distance=5

6️⃣ Get All Edges from Routing-Graph
Fetches all edges that have been sent to the Routing-Graph.
GET http://localhost:8080/api/locations/edges

7️⃣ Get Shortest Distance via Routing-Graph
Fetches the shortest distance between two locations using the Routing-Graph microservice.
GET http://localhost:8080/api/locations/shortest-distance?from=A&to=C

## Getting Started (Local Development)

### Running With Docker (Step-by-Step)
1️⃣ Clone Both Repositories
   #### Clone CoreRouteX
        git clone https://github.com/praveen-1998-dev/CoreRouteX-RoutingGraph.git
        cd CoreRouteX-RoutingGraph
   #### Clone Routing-Graph (in a separate terminal)
         git clone https://github.com/praveen-1998-dev/RouteX-Graph.git
         cd RouteX-Graph
2️⃣ Build JAR Files

    Inside each project folder, run:

          mvn clean package

    This generates the .jar file inside the target/ folder.

3️⃣ Build Docker Images

   Inside each project directory:

    docker build -t core-routex .
    docker build -t routing-graph .
4️⃣ Run Containers:
    docker run -p 8080:8080 core-routex
    docker run -p 8081:8080 routing-graph
5️⃣ Access Services

    CoreRouteX → http://localhost:8080

    Routing-Graph → http://localhost:8081

Both services run internally on port 8080 inside their containers.
Docker maps them to different host ports (8080 and 8081) to avoid conflicts.


### Running Without Docker (Step-by-Step)

Clone and run CoreRouteX-RoutingGraph:
    git clone https://github.com/praveen-1998-dev/CoreRouteX-RoutingGraph.git
    cd CoreRouteX-RoutingGraph
    Run CoreRouteX on Port 8080
    mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8080"
Clone and Run RouteX-Graph:
    git clone https://github.com/praveen-1998-dev/RouteX-Graph.git
    cd RouteX-Graph
    Run it on port 8081
    mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"

## Important  core logic 

1️⃣ **AddingEdges**

- Edges represent direct connections between two locations with a **distance value**.
- Example:
    - Add edge A → B = 5 km
    - Add edge B → C = 4 km
    - Add edge A → C = 12 km

2️⃣ **Shortest Distance Calculation**
- When requesting the shortest distance between two locations, Routing-Graph considers **all possible paths**.
- In the above example:
    - Direct path A → C = 12 km
    - Indirect path A → B → C = 5 + 4 = 9 km
- Routing-Graph returns the **shortest path**, which is **A → B → C (9 km)** in this case.

3️⃣ **User Interaction**
- Users interact via CoreRouteX endpoints.
- Routing-Graph handles edge storage and shortest distance calculation in the background.
- This allows adding multiple paths and ensures that **the optimal route is always returned** automatically.


## Key Features / Highlights

Haversine Formula → Calculates geographic distances between coordinates.
Weighted Graph (Routing-Graph) → Stores edges with distances and calculates shortest path using Dijkstra’s algorithm.
Microservice Communication → CoreRouteX communicates with Routing-Graph via REST API.
Unit Tests (JUnit + Mockito) → Tests service logic without depending on external systems.
Integration Tests → Verifies end-to-end flow for key CoreRouteX services.
Validation → DTO and request validations ensure clean data.
Logging → SLF4J logging for traceable operations.
Demo Ready → All tested functionality works locally without cloud deployment.






Test APIs
Use Postman or Swagger to interact with the endpoints.
Verify adding locations, calculating distances, sending edges, and fetching shortest paths.
All tested functionality works locally without cloud deployment. Feel free to explore the APIs and extend the project.
