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
₩₩₩sql
CREATE DATABASE clush;
₩₩₩

- **hostname** : `localhost`
- **port** : `3306`
- **username**과 **password**는 `application.yml` 파일에 설정되어 있습니다.
- 또한, **DB 테이블**은 `ddl-auto: update`로 설정되어 있으므로, 애플리케이션 실행 시 자동으로 테이블이 생성 및 업데이트됩니다.

### ERD (Entity Relationship Diagram)





