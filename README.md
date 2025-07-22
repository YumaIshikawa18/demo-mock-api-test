# demo-mock-api-test

This project demonstrates how to call a mock server from a Spring Boot
application using `RestClient`. The WireMock server is configured via
Docker Compose and listens on port `8081` by default.

## Running

Build the bridge API and start the services:

```bash
./mvnw package
docker compose up
```

Send a request to the bridge API:

```bash
curl -X POST -H 'Content-Type: application/json' \
  -d '{"name":"田中"}' http://localhost:8082/bridge
```

The application will forward the request to WireMock and return the mock
response defined in `wiremock/mappings/humans-name-mapping.json`.
