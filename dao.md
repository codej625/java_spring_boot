# 스프링부트에서 DAO 적용 관련

<br /><br />


* DAO (Data Access Object)
---

```
데이터베이스와의 상호작용을 추상화하고 데이터베이스에 접근하는 데 사용된다.
DAO는 주로 CRUD(Create, Read, Update, Delete) 연산을 수행하는 메서드들을 포함한다.
```

<br /><br /><br />

1. 패키지 분류

```
1) Mybatis를 사용하는 프로젝트
dao -> dao/model, dao/mapper

* model / mapper 명명규칙 예시
UserDAO / UserMapper
```

<br />

```
2) JPA를 사용하는 프로젝트
domain -> domain/entity, domain/repository

* entity / repository 명명규칙 예시
User / UserRepository
```
