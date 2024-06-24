# JPA(Java Persistence API)

<br /><br />

* 객체 중심의 객체지향 어플리케이션과 테이블 중심의 관계형 데이터베이스는 서로의 목표가 다르다.
---

| 객체지향 모델 | 관계형 모델 |
|------------|----------|
| 객체, 클래스 | 테이블, 로우|
| 속성(Attribute, Property | Column |
| Identity | PK|
| Relationship / 다른 엔티티 참조| FK |
| 상속 / 다형성 | 없음 |
| 메소드 | SQL 로직, SP, 트리거 |
| 코드의 이식성 | 벤더 정속적 |

```
위와 같이 다른 부분을 패러다임의 불일치라 한다.
이는 개발 과정에서 큰 비용을 발생시키며,
영속성으로 데이터를 저장하기 위해 객체와 테이블을 일치시키는 과정이 필요하다.
```

<br /><br /><br />

* ORM vs SQLM
---

```
자바 어플리케이션에서 관계형 데이터베이스의 사용을 돕는(패러다임 불일치를 해결) 프레임워크를 Persistence Framework라 한다.

크게 ORM, SQL Mapping으로 나뉘는데, SQL Mapping은 자바 코드와 SQL을 분리하여 개발자가 작성한 SQL의 수행 결과를 객체로 매핑한다.
ORM은 객체와 관계형 데이터베이스 사이에 매핑을 담당하며, SQL을 생성하여 패러다임의 불일치를 해결한다.

JPA(ORM), MyBatis(SQLM)
```

<br /><br /><br />

* JPA란?
---

```
JPA(Java Persistence API)은 자바 프로그램에서 관계형 데이터베이스에 접근하는 방식을 명세화한 인터페이스이다.
JPA는 자바 진영의 ORM(Object-Relational Mapping) 기술 표준이다.
JPA는 자바 애플리케이션과 JDBC 사이에서 동작하며, 일반적으로 구현체는 Hibernate Framework를 사용한다.

App(JPA -> Hibernate(ORM) -> JDBC) <-> RDBMS
```
