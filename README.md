# clush-backend : Todo Api & 캘린더 API

## 1. 프로젝트 설명

Clush API는 Todo 관리 및 캘린더 공유 기능을 제공하는 백엔드 애플리케이션입니다. 
사용자는 Todo 및 캘린더 항목을 생성, 조회, 수정, 삭제할 수 있으며, 일정 공유 기능을 통해 다른 사용자와 캘린더를 공유할 수 있습니다. 
일정 소유자만이 캘린더를 공유하고 공유를 해제할 수 있으며, 공유된 사용자는 해당 일정만 조회할 수 있습니다.

## 2. 소스 빌드 및 실행 방법

### 사전 요구 사항
- **Spring Boot 3.3.3**
- **Java 17**
- **Gradle**
- **MySQL 8.3**

### MySQL 설정
```sql
CREATE DATABASE clush;
```

- **hostname** : `localhost`
- **port** : `3306`
- **username**과 **password**는 `application.yml` 파일에 설정되어 있습니다.
- 또한, **DB 테이블**은 `ddl-auto: update`로 설정되어 있으므로, 애플리케이션 실행 시 자동으로 테이블이 생성 및 업데이트됩니다.

### ERD (Entity Relationship Diagram)
<img width="942" alt="clush ERD" src="https://github.com/user-attachments/assets/ef40896c-e075-4ec6-a39c-7c6482123cca">

## 3. 주력 컴포넌트에 대한 설명 및 사용 이유

### 주력 컴포넌트 
- **Spring Boot** : 애플리케이션의 전반적인 구성을 담당하는 프레임워크.
- **JPA (Java Persistence API)** : 데이터베이스와의 ORM 매핑을 통해 객체지향적인 데이터베이스 처리를 지원.
- **Spring Validation** : DTO에 대한 유효성 검사를 처리하는 기능 제공.
- **Spring Session** : 사용자의 로그인 세션 관리를 위한 세션 방식.
- **Swagger** : API 명세 및 문서화를 위한 도구.

### 사용 이유
- **Spring Boot**는 빠르고 효율적인 애플리케이션 개발을 가능하게 하기 위해 선택되었습니다. 특히 내장 서버, 다양한 스타터 패키지, 자동 구성 기능으로 설정이 간편합니다.
- **JPA**는 SQL 쿼리를 수동으로 작성할 필요 없이 객체 모델을 통해 데이터베이스와 상호작용할 수 있게 해주며, 생산성을 높이는 데 기여했습니다.
- **Spring Validation**은 입력 데이터의 유효성을 검증하여 안정성을 확보하는 데 중요한 역할을 했습니다.
- **Spring Session**은 사용자 로그인 상태를 유지하고, 간편하게 인증 로직을 구현할 수 있는 방법을 제공했습니다. 또한 요구 사항이 회원 가입과 로그인 및 인증 인가 구현이 없었기 때문에 간단하게 구현했습니다. 
- **Swagger**는 API를 쉽게 테스트하고, 다른 개발자들과 명확하게 소통할 수 있도록 지원하는 도구로 선택되었습니다.

## 4. API 명세 : Swagger 사용

API 명세는 [Swagger UI](http://localhost:8080/swagger-ui.html) 에서 확인할 수 있습니다.
각 API의 실행 순서는 번호 순으로 구성되어 있습니다.


