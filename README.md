# 🚀 CoreRouteX & Routing-Graph Microservices

## Project Overview

This project demonstrates a **microservices-based routing system** that calculates shortest paths between locations and maintains a weighted graph of edges.

- **CoreRouteX (8080)** → Main microservice where users interact. Handles:
    - Adding locations
    - Calculating distances (Haversine formula)
    - Sending edges to Routing-Graph
    - Fetching shortest distance between locations

- **Routing-Graph (8081)** → Helper microservice that stores edges and computes shortest paths using **Dijkstra’s algorithm**. Receives commands from CoreRouteX only.

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
|   CoreRouteX      |  --->    |   Routing-Graph   |
|  (Port 8080)      |  Edge    |  (Port 8081)      |
+-------------------+          +-------------------+
| - Locations       |          | - Graph Edges     |
| - Haversine Dist  |          | - Shortest Path   |
| - Send Edge       |          | - Dijkstra Algo   |
+-------------------+          +-------------------+


## CoreRouteX & Routing-Graph Overview

CoreRouteX sends edges and distance data to Routing-Graph, which stores edges in its database and computes shortest distances when requested.
All user interactions happen via CoreRouteX (port 8080).

## CoreRouteX (8080) API Endpoints

1️⃣ Add Location
Adds a new location with name and latitude/longitude coordinates to the system.
POST /api/locations
Content-Type: application/json
{
  "name": "A",
  "latitude": 12.9716,
  "longitude": 77.5946
}

2️⃣ Get All Locations
Fetches all locations stored in CoreRouteX.
GET /api/locations

3️⃣ Calculate Distance by Coordinates
Calculates distance (Haversine formula) between two coordinates.
POST /api/locations/distance?fromLatitude=12.9716&fromLongitude=77.5946&toLatitude=12.9352&toLongitude=77.6245

4️⃣ Calculate Distance by Location Names
Calculates distance between two existing locations by their names.
POST /api/locations/distance-by-name?from=A&to=C

5️⃣ Send Edge to Routing-Graph
Sends a weighted edge (from → to with distance) to the Routing-Graph microservice.
POST /api/locations/send-edge?from=A&to=B&distance=100

6️⃣ Get Shortest Distance via Routing-Graph
Fetches the shortest distance between two locations using the Routing-Graph microservice.
GET /api/edges/shortest-distance?from=A&to=C

Note: Users interact only via CoreRouteX. Routing-Graph handles edge storage and shortest distance calculation in the background.

## Key Features / Highlights
Haversine Formula → Calculates geographic distances between coordinates.
Weighted Graph (Routing-Graph) → Stores edges with distances and calculates shortest path using Dijkstra’s algorithm.
Microservice Communication → CoreRouteX communicates with Routing-Graph via REST API.
Unit Tests (JUnit + Mockito) → Tests service logic without depending on external systems.
Integration Tests → Verifies end-to-end flow for key CoreRouteX services.
Validation → DTO and request validations ensure clean data.
Logging → SLF4J logging for traceable operations.
Demo Ready → All tested functionality works locally without cloud deployment.
Professional Structure → Clear separation of concerns, layered architecture, and reusable services.

## Getting Started (Local Development)
Clone the repository
git clone <repo-url>
cd core-routex
Run CoreRouteX:
    mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8080"
Run Routing-Graph:
    mvn spring-boot:run -Dspring-boot.run.arguments="--server.port=8081"
Test APIs
Use Postman or Swagger to interact with the endpoints.
Verify adding locations, calculating distances, sending edges, and fetching shortest paths.
All tested functionality works locally without cloud deployment. Feel free to explore the APIs and extend the project.