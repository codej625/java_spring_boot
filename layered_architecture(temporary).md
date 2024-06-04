# 계층형 아키텍처

<br /><br />

* Package 구조
---

<br />

```
* 예시

com.example.application
├── config
│   ├── AppConfig.java
│   └── DatabaseConfig.java
├── controller
│   ├── presentation
│   │   └── PresentationController.java
│   └── other
│       └── OtherController.java
├── common
│   └── AdditionalDTO.java
├── dto
│   ├── request
│   │   ├── presentation
│   │   │   └── PresentationRequestDTO.java
│   │   └── other
│   │       └── OtherRequestDTO.java
│   ├── response
│   │   ├── presentation
│   │   │   └── PresentationResponseDTO.java
│   │   └── other
│   │       └── OtherResponseDTO.java
├── service
│   ├── presentation
│   │   └── PresentationService.java
│   └── other
│       └── OtherService.java
├── repository
│   ├── presentation
│   │   └── PresentationRepository.java
│   └── other
│       └── OtherRepository.java
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
└── utils
    └── SomeUtility.java
```
