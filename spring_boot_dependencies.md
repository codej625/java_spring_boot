# 스프링 부트 프로젝트를 생성할 때 추가하는 의존성을 알아보자.

<br /><br />

* 개발 시 필수 의존성들
```
Dependencies란 프로젝트가 의존하는 외부 라이브러리나 모듈을 의미한다.
일반적으로 프로젝트를 개발할 때,
필요한 기능을 직접 구현하기보다는 이미 개발된 라이브러리나 모듈을 활용하는 경우가 많다.
이러한 외부 라이브러리나 모듈을 의존성(dependency)이라고 한다.

의존성은 프로젝트를 더욱 효율적으로 개발할 수 있도록 도와주며,
이미 검증된 코드를 재사용할 수 있게 해준다.

밑에서 많이 사용하는 의존성 들을 알아보자.
```

<br /><br /><br />

1. Spring Web
```
- Spring Framework의 일부로서 웹 애플리케이션을 개발하는 데 사용되는 모듈이다.
- 웹 애플리케이션을 구축하는 데 필요한 다양한 기능과 기능들을 제공한다.
예를 들어, MVC (Model-View-Controller) 아키텍처를 지원하여 웹 애플리케이션의 요청을 처리하고 응답을 생성하는 데 도움이 된다.
- Spring MVC와 관련이 있으며,
Spring Boot 프로젝트에서 Spring Web Dependency를 추가하면 Spring MVC를 사용하여 웹 애플리케이션을 개발할 수 있다.
- 주요 기능으로는 HTTP 요청 매핑, 요청 및 응답 처리, 폼 처리, 파일 업로드 등이 있다.
```

<br />

2. Spring Web Service
```
- Spring Web Service는 SOAP (Simple Object Access Protocol) 기반의 웹 서비스를 개발하는 데 사용된다.
- SOAP는 네트워크를 통해 XML 메시지를 교환하여 분산 시스템 간의 통신을 지원하는 프로토콜이다.
- Spring 프레임워크의 일부로서 SOAP 기반의 웹 서비스를 손쉽게 구현할 수 있도록 도와준다.
- WSDL (Web Services Description Language) 파일을 사용하여 서비스를 정의하고,
JAXB (Java Architecture for XML Binding)를 사용하여 Java 객체와 XML 사이의 매핑을 처리할 수 있다.
- Spring Boot 프로젝트에서 Spring Web Service Dependency를 추가하면 SOAP 기반의 웹 서비스를 개발할 수 있다.
```

<br />

3. Spring Boot DevTools
```
- Spring Boot 애플리케이션을 개발할 때 개발 생산성을 향상시키는 도구이다.
- 코드 변경 감지 및 자동 재시작, 내장 서버 재시작 및 라이브 리로드 등의 기능을 제공하여 애플리케이션 개발 및 테스트를 빠르게 할 수 있다.
```

<br />

4. Spring Security
```
- Spring 기반 애플리케이션의 보안을 담당하는 프레임워크이다.
- 사용자 인증 및 권한 부여, 암호화, 보안 토큰 관리 등의 기능을 제공하여 애플리케이션의 보안을 강화할 수 있다.
```

<br />

5. Spring Data JPA or MyBatis Framework
```
JPA

- Spring 프레임워크에서 JPA(Java Persistence API)를 사용하여 데이터베이스와의 상호작용을 지원하는 모듈이다.
- 객체 관계 매핑 (ORM)을 통해 자바 객체와 데이터베이스 테이블 간의 매핑을 관리하고, 간단한 CRUD 작업을 자동화하여 개발 생산성을 높인다.
```
```
MyBatis

- MyBatis는 SQL을 자바 코드에서 분리하여 관리할 수 있도록 도와주는 데이터베이스 액세스 프레임워크이다.
- SQL 매핑 파일을 사용하여 SQL 쿼리를 정의하고, 자바 객체와의 매핑을 처리하여 데이터베이스와의 상호작용을 쉽게 할 수 있다.
```

<br />

6. Database Driver
```
- 데이터베이스와 애플리케이션 사이의 통신을 위한 드라이버이다.
- 각 데이터베이스에 대한 드라이버는 해당 데이터베이스와의 통신을 지원하며,
JDBC(Java Database Connectivity)를 통해 데이터베이스와 상호작용한다.
```

<br />

7. Lombok
```
- Lombok은 자바 개발자가 보일러플레이트 코드를 줄이고 반복 코드를 간소화하는 데 도움을 주는 라이브러리이다.
- 애노테이션을 사용하여 Getter, Setter, Constructor 등의 메서드를 자동으로 생성하거나,
Equals 및 HashCode 메서드를 생성하는 등의 기능을 제공한다.
```
