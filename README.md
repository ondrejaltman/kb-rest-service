# kb-rest-service

REST service providing feature flags configurations and their checksums.

## Authorization

Authorized requests are only those with `X-Api-Key` header. Following API keys are allowed:

- `rBspQTH94VKhSEvVUbTEMH9hzCLXvfNm`
- `1Ei6vrLcBHHiQigAKPH11RqGZpdbuMbV`
- `1W84SK8FWF6gGV5SATFZ8qPV8PvrJo71`

## 1. EP - Get configuration by name

### Request
```
GET /feature-flags/{name}
```

### Response
```json
{
  "name": "FF1",
  "conditions": [
    {
      "type": "ENVIRONMENT",
      "value": "TEST"
    },
    {
      "type": "IP_RANGE",
      "value": "192.0.0.0-192.0.0.255"
    },
    {
      "type": "USER_NAME",
      "value": "ondrej.altman"
    }
  ]
}
```

## 2. EP - Get checksum of configuration by name

### Request
```
GET /feature-flags/{name}/checksum
```

### Response
```json
{
  "checksum": "nIFBuzojMonEYUfSlPLztw=="
}
```