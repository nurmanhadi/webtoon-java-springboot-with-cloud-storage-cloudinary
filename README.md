## SUMMARY

RESTful API Webtoon management

---

### Features

1. AUTH JWT
2. CRUD Comic
3. CRUD Chapter
4. CRUD Content
5. Upload file image to cloud storage Cloudinary
5. Pagination and Sorting
6. Input Validation
8. JSON Response Wrapper

---

### Tech Stack

- Java 21
- Springboot
- MySQL
- Cloud Storage Cloudinary
- Lombok
- Jakarta Bean Validation

---

### Requirements

- Java 21
- Maven 3++
- MySQL v8++
- Cloudinary account (for upload image)

---

### Setup and Run

1. Clone Repository

    ```bash
    https://github.com/nurmanhadi/webtoon-java-springboot-with-cloud-storage-cloudinary.git
    ```

2. Configuration

    Change configuration file value in `application.properties`

    ```properties
    spring.threads.virtual.enabled=true

    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.datasource.url=jdbc:mysql://localhost:3306/your-database
    spring.datasource.username=your-user
    spring.datasource.password=yourpass

    spring.datasource.hikari.minimum-idle=10
    spring.datasource.hikari.maximum-pool-size=30

    cloudinary.cloud-name=your-cloud-name
    cloudinary.api-key=your-api-key
    cloudinary.api-secret=your-api-secret
    ```
    
3. Database Migration

    Create table for your database in `folder database`

4. Run Application

    ```bash
    ./mvnw spring-boot:run
    ```

---

##  API Documentation

### User
<details>
<summary>1. Register User</summary>

**Entpoint:**
`POST /api/users`

**Headers:**
Content-Type: application/json

**Request Body:**
```json
{
    "username": "username",
    "password": "pass"
}
```

**Response - Success (201)**
```json
{
    "data": "OK",
    "error": null
}
```
    
**Response - Error (400)**
```json
{
    "data": null,
    "error": "username already exists"
}
```
</details>

### Auth

<details>
<summary>1. Login</summary>

**Entpoint:**
`POST /api/auth/login`

**Headers:**
Content-Type: application/json

**Request Body:**
```json
{
    "username": "username",
    "password": "pass"
}
```

**Response - Success (200)**
```json
{
    "data": {
        "token": "jwt"
    },
    "error": null
}
```
    
**Response - Error (400)**
```json
{
    "data": null,
    "error": "username or password wrong"
}
```
</details>

### Comic

<details>
<summary>1. Add Comic</summary>

**Entpoint:**
`POST /api/secure/comics`

**Headers:**
Content-Type: multipart/form-data, Authorization: Bearer JWT

**Request Form:**

| key      | type | required |
|----------|------|----------|
| cover    | file | true     |
| title    | text | true     |
| synopsis | text | true     |
| author   | text | true     |
| artist   | text | true     |
| type     | text | true     |

**Response - Success (201)**
```json
{
    "data": "OK",
    "error": null
}
```
    
**Response - Error (400, 404)**
```json
{
    "data": null,
    "error": "comic not found"
}
```
</details>

<details>
<summary>2. Update Comic</summary>

**Entpoint:**
`PUT /api/secure/comics/{comicId}`

**Path Variable:**
comicId `String`

**Headers:**
Content-Type: multipart/form-data, Authorization: Bearer JWT

**Request Form:**

| key      | type | required |
|----------|------|----------|
| cover    | file | false    |
| title    | text | false    |
| synopsis | text | false    |
| author   | text | false    |
| artist   | text | false    |
| type     | text | false    |

**Response - Success (200)**
```json
{
    "data": "OK",
    "error": null
}
```
    
**Response - Error (400, 404)**
```json
{
    "data": null,
    "error": "comic not found"
}
```
</details>

<details>
<summary>3. Get All Comic</summary>

**Entpoint:**
`GET /api/comics?page={page}&size={size}`

**Query Param:**
page `Number`, size `Number` 

**Response - Success (200)**
```json
{
    "data": {
        "contents": [
            {
                "id": 7,
                "cover": "1750085043501",
                "title": "test7",
                "synopsis": "test",
                "author": "test",
                "artist": "test",
                "type": "TEST",
                "url": "url",
                "createdAt": "2025-06-16T00:00:00",
                "chapters": [
                    {
                        "id": 8,
                        "number": 7,
                        "createdAt": "2025-06-17T17:02:28"
                    },
                    {
                        "id": 7,
                        "number": 6,
                        "createdAt": "2025-06-17T17:02:24"
                    }
                ]
            }
        ],
        "page": 1,
        "size": 10,
        "totalPages": 1,
        "totalEmelents": 1
    },
    "error": null
}
```
</details>

<details>
<summary>4. Get Comic By Id</summary>

**Entpoint:**
`GET /api/comics/{comicId}`

**Path Variable:**
comicId `String` 

**Response - Success (200)**
```json
{
    "data": {
        "id": 7,
        "cover": "1750085043501",
        "title": "test7",
        "synopsis": "test",
        "author": "test",
        "artist": "test",
        "type": "TEST",
        "url": "url",
        "createdAt": "2025-06-16T00:00:00"
    },
    "error": null
}
```

**Response - Error (404)**
```json
{
    "data": null,
    "error": "comic not found"
}
```
</details>

<details>
<summary>5. Delete Comic</summary>

**Entpoint:**
`GET /api/secure/comics/{comicId}`

**Path Variable:**
comicId `String` 

**Headers:**
Authorization: Bearer JWT

**Response - Success (200)**
```json
{
    "data": "OK",
    "error": null
}
```

**Response - Error (404)**
```json
{
    "data": null,
    "error": "comic not found"
}
```
</details>