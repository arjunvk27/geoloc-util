# GeoLoc Utility

This command-line utility uses the Open Weather Geocoding API to retrieve geographical coordinates and additional location information for US locations. It supports both city/state (e.g., "Madison, WI") and zip code (e.g., "12345") inputs, and it can process multiple locations in one run.

## Features

- Retrieves latitude, longitude, place name, and additional data.
- Supports both city/state and zip code queries.
- Uses the Open Weather Geocoding API with the provided API key.
- Integration tests are provided.

## Prerequisites

- Java 11 or higher.
- Maven 3.x.

## Setup and Running

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/geoloc-util.git
   cd geoloc-util

2. **Running Tests:** 
Integration tests can be run locally (using JUnit 5) and the results will be reported in your terminal.
To run the integration tests locally, use Maven. From the project root directory (where pom.xml is located), execute the following command:
 ```bash
mvn test
