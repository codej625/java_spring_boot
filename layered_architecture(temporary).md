# 계층형 아키텍처

<br /><br />

* Package 구조
---

<br />

```
* 예시

com.example.application
├── aop
│   └── LoggingAspect.java
│
├── intercepter
│   └── SessionInterceptor.java
│
├── config
│   ├── SecurityConfig.java
│   └── DatabaseConfig.java
│
├── controller
│   ├── presentation
│   │   └── PresentationController.java
│   └── other
│       └── OtherController.java
│
├── common
│   └── AdditionalDTO.java
│
├── dto
│   ├── request
│   │   ├── presentation
│   │   │   └── PresentationRequestDTO.java
│   │   └── other
│   │       └── OtherRequestDTO.java
│   │
│   ├── response
│   │   ├── presentation
│   │   │   └── PresentationResponseDTO.java
│   │   └── other
│   │       └── OtherResponseDTO.java
│   │
├── service
│   ├── presentation
│   │   └── PresentationService.java
│   └── other
│       └── OtherService.java
│
├── repository
│   ├── presentation
│   │   └── PresentationRepository.java
│   └── other
│       └── OtherRepository.java
│
├── domain
│   ├── presentation
│   │   ├── entity
│   │   │   └── PresentationEntity.java
│   │   ├── vo
│   │   │   └── PresentationValueObject.java
│   │   └── PresentationBusinessLogic.java
│   └── other
│       ├── entity
│       │   └── OtherEntity.java
│       ├── vo
│       │   └── OtherValueObject.java
│       └── OtherBusinessLogic.java
│
└── utils
    └── SomeUtility.java
```

<br /><br /><br />

1. Domain package
```
개발 대상, 즉 도메인을 모든 사람이 동일한 관점에서 이해할 수 있고
공유할 수 있도록 단순화 한 것을 도메인 모델이라고 한다.

1) 비즈니스 로직 처리
2) JPA를 사용한다면, @Entity가 사용되는 영역
3) VO(Value Object) 또한 도메인 영역에 속한다.
```
