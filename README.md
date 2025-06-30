# Gerador de QR Code com Spring Boot

This project is a REST API developed with **Java + Spring Boot** that generates a QR Code from a URL. The generated QR Code is stored in Amazon S3 and returned.
## Technologies used

- Java 21+
- Spring Boot
- ZXing (for QR Code generation)
- Docker
- Amazon S3 (AWS SDK)

---

## How to run project

### Prerequisites

- Docker
- Account and bucket configured in Amazon S3
- Valid AWS credentials (via `.env` or environment variables)

### 1. Clone repository

```bash
git clone https://github.com/seu-usuario/qrcode-generator.git
cd qrcode-generator
```

### 2. Create an .env in the root of the project with the following content:
```env
AWS_ACCESS_KEY_ID=your-access-key
AWS_SECRET_ACCESS_KEY=your-secret-key
AWS_REGION=us-east-1
AWS_S3_BUCKET=your-bucket-name
```

### 3. Build and start with Docker
```bash
docker build -t qrcode-generator .
docker run --env-file .env -p 8080:8080 qrcode-generator
```

### How test
QR Code generation endpoint

POST /qrcode/generate
```json
{
  "text": "https://www.google.com"
}
```

Response (200 OK)
```json
{
  "url": "https://your-bucket-name.s3.us-east-2.amazonaws.com/a4a3fadf-2f13-4a2f-ed94-a3252790d991"
}
```

### Features
* QR Code generation from text or URL
* Automatic upload to AWS S3
* Return of the image's public URL
* Docker-ready for easy deployment








