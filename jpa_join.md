# JPA Join

<br /><br />

* FetchType
---

1. EAGER
```
기본적으로 연관된 엔티티를 즉시 로드한다.
이 경우에는 연관된 엔티티의 데이터도 함께 조회된다.
```

<br />

2. LAZY
```
연관된 엔티티는 실제로 접근할 때까지 로드되지 않는다.
이 옵션을 사용하면 연관된 엔티티의 데이터를 조회하지 않고,
필요할 때 추가적인 쿼리를 실행하여 데이터를 로드한다.
```

<br />

```
예를 들어,
@ManyToOne 등의 어노테이션에 fetch = FetchType.LAZY 옵션을 사용하여 연관된 엔티티를 지연로딩할 수 있다.

이렇게 하면 엔티티를 개별적으로 조회할 때 연관된 엔티티를 즉시 로드하지 않고,
필요한 경우에만 추가적인 쿼리를 통해 로드할 수 있다.

예시 코드를 보자.
```

<br /><br /><br />

1. 연관 엔티티 즉시 로드
```java
@Entity
public class ParentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
  private List<ChildEntity> children;

  // getters and setters
}

@Entity
public class ChildEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private ParentEntity parent;

  // getters and setters
}
```
```
위 예시에서 ParentEntity는 @OneToMany 관계로 ChildEntity와 연결되어 있으며,
FetchType.EAGER로 설정되어 있다.

이 경우에는 ParentEntity를 조회할 때 연관된 ChildEntity들도 즉시 로드된다.
```

<br /><br />

2. 연관 엔티티 지연 로드
```java
@Entity
public class ParentEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
  private List<ChildEntity> children;

  // getters and setters
}

@Entity
public class ChildEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "parent_id")
  private ParentEntity parent;

  // getters and setters
}
```
```
위 예시에서 ParentEntity의 children 필드는 FetchType.LAZY로 설정되어 있다.

이 경우 ParentEntity를 조회할 때는 ChildEntity들은 로드되지 않고,
실제로 접근하여 필요할 때 쿼리가 실행되어 데이터가 로드된다.
```

<br /><br />

3. @Transient
```
@Transient 어노테이션은 특정 필드를 영속화 과정에서 무시하도록 지정할 때 사용된다.

예를 들어, 엔티티 클래스에 데이터베이스와 매핑할 필요가 없는
일시적인 데이터나 계산된 값을 담는 필드 등을 정의할 때 유용하다.
```
```java
@Entity
public class MyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Transient
  private transientField; // 이 필드는 데이터베이스와 매핑되지 않음

  // getters and setters
}
```
```
위 예시에서 transientField는 @Transient 어노테이션이 적용되어 데이터베이스와의 매핑을 하지 않는다.
이 필드는 영속성 컨텍스트와 관련된 작업에서 무시된다.
```
