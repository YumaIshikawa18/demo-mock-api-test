# demo-mock-api-test

Spring Boot を使用したモック API のサンプルです。

## 前提条件

- Java 21 が実行可能な環境
- Docker と Docker Compose

## ビルド

```bash
./mvnw package
```

## テスト実行

```bash
./mvnw test
```

## Docker Compose での起動

```bash
docker compose up --build
```

`compose.yml` の定義に従い、`bridge-api` と WireMock が起動します。

JAR をビルドしてコンテナを作り直す場合は次のコマンドを実行します。

```bash
./mvnw clean package && docker compose down --volumes && docker compose up -d --build
```

WireMock で外部 API をモックし、`bridge-api` からは Spring の `RestClient` を用いてアクセスします。

## `/bridge` エンドポイント

`bridge-api` では `/bridge` への `POST` リクエストを受け付けます。リクエストの JSON 例とレスポンス例は以下のとおりです。

```json
{
  "name": "Ken Watanabe"
}
```

レスポンス例:

```json
[
  {
    "name": "Ken Watanabe",
    "age": 40,
    "hobbies": ["gardening"]
  }
]
```

このデータは WireMock の `/humans/name` から取得しており、実際の外部 API をモックしています。

### Docker Compose 起動後の利用例

```bash
docker compose up --build
```

起動すると `bridge-api` がポート `8081`、WireMock がポート `8080` で待ち受けます。以下のように `curl` でアクセスできます。

```bash
curl -X POST http://localhost:8081/bridge \
  -H "Content-Type: application/json" \
  -d '{"name":"Ken Watanabe"}'
```

### `MOCK_SERVER_URL` 環境変数

`compose.yml` では `bridge-api` に `MOCK_SERVER_URL` を設定しています。この値は Spring Boot の `mock-server.url` プロパティにバインドされ、`RestClient` の `baseUrl` として使用されます。そのため、この環境変数を変更すると `bridge-api` が参照する外部 API を切り替えられます。デフォルトでは `http://wiremock:8080` が指定されており、WireMock コンテナと連携します。
