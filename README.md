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

