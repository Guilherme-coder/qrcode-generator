# Gerador de QR Code com Spring Boot

This project is a REST API developed with **Java + Spring Boot** that generates a QR Code from a URL. The generated QR Code is stored in Amazon S3 and returned.


## Public Demo (EC2 instance) 

You can test the API live using the public EC2 DNS:

https://ec2-54-177-235-106.us-west-1.compute.amazonaws.com/qrcode/generate:8080
Make sure to send a POST request with JSON content.

### Example Request
**POST** /qrcode/generate
**Content-Type**: application/json

```json
{
    "text": "www.youtube.com"
}
```

Response (200 OK)
```json
{
    "url": "https://qrcode-storager-s3.s3.us-east-2.amazonaws.com/d3fec855-4473-440d-aa14-1751c3df8a26"
}
```
#### The returned url points to a publicly accessible QR Code stored on Amazon S3.

## Technologies used

- Java 21+
- Spring Boot
- ZXing (for QR Code generation)
- Docker
- Amazon S3 (AWS SDK)

---

## Deployment with GitHub Actions

This project uses GitHub Actions for automated deployment to an AWS EC2 instance.

Whenever changes are pushed to the prod branch, a workflow is triggered to:

* Build the Docker image using Maven
* Push the image to Docker Hub
* On the EC2 instance (via self-hosted runner):
  * Pull the latest image from Docker Hub
  * Stop and remove the existing container (if running)
  * Run the container in background using environment variables from .env

### Prerequisites

* A self-hosted GitHub Actions runner is configured on the EC2 instance  
* Docker installed and running on the EC2  
* .env file present at /home/ubuntu/qrcode-generator/.env (not versioned)  
* Docker Hub account and credentials (used in secrets)

### Required GitHub Secrets

In my repository settings (Settings > Secrets and variables > Actions), i add:

| Name            | Description                  |
|-----------------|------------------------------|
| DOCKER_USERNAME | My Docker Hub username       |
| DOCKER_PASSWORD | My Docker Hub password/token |

These are used in the build workflow to authenticate and push to Docker Hub.

### Workflow Files

The project uses two workflows:

.github/workflows/prod.yml – builds and pushes the Docker image in build step and pull and run the Docker image in deploy step

## How to run project locally

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








