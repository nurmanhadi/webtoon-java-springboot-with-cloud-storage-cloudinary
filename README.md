# 📚 Webtoon RESTful API

A comprehensive RESTful API for managing webtoons, allowing users to handle comics, chapters, and content (images) easily. Built with Spring Boot and Java 21, the application includes secure authentication with JWT, file uploads to Cloudinary, and robust input validation.

---

## 🚀 Features

- 🔐 **JWT Authentication**
- 📚 **CRUD Operations for Comics**
- 📖 **CRUD Operations for Chapters**
- 🖼️ **CRUD Operations for Content (Image Pages)**
- ☁️ **Upload Images to Cloudinary**
- 📊 **Pagination and Sorting**
- 🛡️ **Input Validation**
- 📦 **Consistent JSON Response Wrapper**
- 🫙 **Dockerfile and Docker Compose**
- 🌏 **Ready Production**

---

## 🛠️ Tech Stack

- Java 21  
- Spring Boot  
- MySQL  
- Cloudinary  
- Lombok  
- Jakarta Bean Validation  

---

## 📋 Requirements

- Java 21  
- Maven 3+  
- MySQL 8+  
- Cloudinary Account  

---

## ⚙️ Setup & Run the Project

### 1. Clone the Repository

```bash
git clone https://github.com/nurmanhadi/webtoon-java-springboot-with-cloud-storage-cloudinary.git
cd webtoon-java-springboot-with-cloud-storage-cloudinary
```

### 2. Configure `application.properties`

Edit the file at `src/main/resources/application.properties`:

```properties
spring.threads.virtual.enabled=true

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/your-database
spring.datasource.username=your-username
spring.datasource.password=your-password

spring.datasource.hikari.minimum-idle=10
spring.datasource.hikari.maximum-pool-size=30

cloudinary.cloud-name=your-cloud-name
cloudinary.api-key=your-api-key
cloudinary.api-secret=your-api-secret
```

### 3. Setup Database

Create the required tables using the schema provided in the `database/` folder.

### 4. Run the Application

```bash
./mvnw spring-boot:run
```

---

## 📖 API Documentation


| User   | Path       | Header                    | Required |
|--------|------------|---------------------------|----------|
| Admin  | api/admin  | Authorization: Bearer JWT | ✅       |
| Anonim | api/public | None                      | ❌       |

### 👤 User & Auth

#### Register  
`POST` `/api/auth/register`  
```json
{
  "username": "your_username",
  "password": "your_password"
}
```

#### Login  
`POST` `/api/auth/login`  
```json
{
  "username": "your_username",
  "password": "your_password"
}
```

---

### 📘 Comic Endpoints

#### Create Comic  
`POST` `/api/admin/comics`

**Form Data:**

| Key     | Type | Required |
|---------|------|----------|
| cover   | file | ✅       |
| title   | text | ✅       |
| synopsis| text | ✅       |
| author  | text | ✅       |
| artist  | text | ✅       |
| type    | text | ✅       |

#### Update Comic  
`PUT` `/api/admin/comics/{comicId}`

#### Get All Comics  
`GET` `/api/public/comics?page=1&size=10`

#### Get Comic by ID  
`GET` `/api/public/comics/{comicId}`

#### Delete Comic  
`DELETE` `/api/admin/comics/{comicId}`

---

### 📖 Chapter Endpoints

#### Create Chapter  
`POST` `/api/admin/comics/{comicId}/chapters`  
```json
{
  "number": 1
}
```

#### Update Chapter  
`PUT` `/api/admin/comics/{comicId}/chapters/{chapterId}`  
```json
{
  "number": 2
}
```

#### Get All Chapters  
`GET` `/api/public/comics/{comicId}/chapters`

#### Get Chapter by ID  
`GET` `/api/public/comics/{comicId}/chapters/{chapterId}`

#### Delete Chapter  
`DELETE` `/api/admin/comics/{comicId}/chapters/{chapterId}`

---

### 🖼️ Content Endpoints

#### Upload Content Image  
`POST` `/api/admin/comics/{comicId}/chapters/{chapterId}/contents`  
**Form Data:**

| Key     | Type | Required |
|---------|------|----------|
| content | file | ✅       |

#### Update Content  
`PUT` `/api/admin/comics/{comicId}/chapters/{chapterId}/contents/{contentId}`

#### Get All Content by Chapter Number
`GET` `/api/public/comics/{comicId}/chapters/{chapterNumber}/contents`

#### Delete Content  
`DELETE` `/api/admin/comics/{comicId}/chapters/{chapterId}/contents/{contentId}`

---

### 📖 Category Enpoints
#### Add Category
`POST` `/api/admin/categories`

```json
{
  "name": "harem"
}
```

#### Update Category
`PUT` `/api/admin/categories/{categoryId}`

```json
{
  "name": "action"
}
```

#### Get All Category
`GET` `/api/public/categories`

#### Get Category by Id
`GET` `/api/public/categories/{categoryId}`

---

### 📖 Comic Category Enpoints
#### Add Comic Category
`POST` `/api/admin/comic-categories`

```json
{
  "comicId": 7,
  "categoryId": 3
}
```

### Get all by Category Id
`GET` `/api/admin/comic-categories/categories/{categoryId}?page={page}&size={size}`

#### Delete Comic Category
`DELETE` `/api/admin/comic-categories/{comicCategoryId}`

---

## ✅ Success Response Format

```json
{
  "data": "OK",
  "error": null
}
```

## ❌ Error Response Format

```json
{
  "data": null,
  "error": "resource not found"
}
```

---

## 📌 Notes

- All `/api/admin/**` endpoints require a valid JWT token.
- File uploads are stored in Cloudinary and accessible via returned URLs.
- Spring's validation handles input validation via Jakarta Bean Validation.
- The project uses consistent response wrapping to standardize API output.

---

## 📝 License

This project is licensed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0). You are free to use, modify, and distribute this project under the terms of that license.

---