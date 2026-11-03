# rewards calculator
=======
# Customer Rewards Calculator

A Java 8 / Spring Boot REST API that calculates customer reward points for purchases over a caller-selected date range.

## Business rule

Each purchase is evaluated independently:
- Amount up to and including $50: 0 points.
- Portion greater than $50 and up to $100: 1 point per whole dollar.
- Portion greater than $100: 2 points per whole dollar, plus 50 points for the $50-$100 band.

Examples: `$45 = 0`, `$75 = 25`, `$100 = 50`, `$120 = 90`, `$150 = 150`.

Fractional points are rounded down. Currency amounts use `BigDecimal`, not floating-point arithmetic.

## Technology

- Java 8
- Spring Boot 2.7.18
- Maven
- Spring Web and Bean Validation
- JUnit 5, Mockito, Spring Boot Test
- In-memory sample repositories

## Run locally

Requirements: JDK 8 and Maven 3.6+.

```bash
mvn clean test
mvn spring-boot:run
```

Application listens on `http://localhost:8080`.

## API

### Get rewards for one customer

`GET /api/customers/{customerId}/rewards?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD`

Example:

```bash
curl "http://localhost:8080/api/customers/1/rewards?startDate=2026-01-01&endDate=2026-03-31"
```

The response includes customer details, requested date range, transaction count, total points, monthly totals, and transaction-level calculations.

Example response (abridged):

```json
{
  "customer": {
    "id": 1,
    "name": "Alice Johnson",
    "email": "alice@example.com"
  },
  "startDate": "2026-01-01",
  "endDate": "2026-03-31",
  "transactionCount": 6,
  "totalRewardPoints": 555,
  "monthlyRewards": [
    {"month": "2026-01", "transactionCount": 2, "rewardPoints": 115},
    {"month": "2026-02", "transactionCount": 2, "rewardPoints": 150},
    {"month": "2026-03", "transactionCount": 2, "rewardPoints": 290}
  ],
  "transactions": []
}
```

### Sample customers

| ID | Name |
|---:|---|
| 1 | Alice Johnson |
| 2 | Bob Smith |
| 3 | Carol Davis |

### Error responses

- `400 Bad Request`: missing/malformed dates or start date after end date.
- `404 Not Found`: customer ID does not exist.
- `500 Internal Server Error`: unexpected server-side failure.

Errors use a consistent JSON structure containing timestamp, status, error, and message.

## Design

`RewardController` handles HTTP concerns. `RewardService` validates the date range, loads the customer, coordinates transaction retrieval, calculates transaction-level points, and aggregates monthly and overall totals. `RewardCalculator` contains the isolated tier logic. Repository classes provide a small deterministic sample dataset and can later be replaced with database-backed implementations.

## Asynchronous transaction retrieval

The transaction repository uses `CompletableFuture.supplyAsync` with a configured Spring `ThreadPoolTaskExecutor`. A short delay simulates an external asynchronous API call. This is a demonstration only; replace the in-memory repository with an HTTP client or persistence adapter for production.

## Testing

Run `mvn test`. Tests cover reward boundaries, higher-tier calculations, negative amounts, date-range validation, successful API response, unknown customer, and invalid date range. The integration test asserts the sample 3-month result for customer 1: 6 transactions and 590 points.

## Git / source control

Initialize and commit source (not generated binaries):

```bash
git init
git add pom.xml src README.md .gitignore
git commit -m "feat: add customer rewards calculation API"
```

## Limitations / production considerations

- Sample data is in memory and resets on restart.
- No authentication, database, pagination, or currency conversion is configured.
- For production, persist customers and transactions, define currency/rounding policy, add API security, and use a resilient external service client with timeouts.
