# Gradle 설정에 대해 알아보자.

<br /><br />

* Gradle
---

```
Gradle은 JVM 기반 프로젝트를 위한 강력한 빌드(자동화) 도구이다.
Apache Ant와 Apache Maven의 장점을 결합하여 만들어졌다.

다양한 프레임워크와 툴체인(예: Java, Kotlin, Android, Spring 등)을 지원하며,
사용자 커뮤니티와 플러그인 생태계가 활발히 운영되고 있다.
```

<br /><br /><br />

1. Spring Boot에서 사용하는 Gradle의 기본 형태

```gradle
plugins {
  id 'java'
  id 'org.springframework.boot' version '{spring_boot_version}'
  id 'io.spring.dependency-management' version '{management_version}'
}

group = 'com.domain'
version = '{project_version}'

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of({java_version})
  }
}

configurations {
  compileOnly {
    extendsFrom annotationProcessor
  }
}

repositories {
  mavenCentral()
}

dependencies {
  implementation 'com.h2database:h2:1.4.200' // Test DB
  implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
  implementation 'org.springframework.boot:spring-boot-starter-security'
  implementation 'nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect'
  implementation 'org.springframework.boot:spring-boot-starter-web'
  implementation 'org.springframework.boot:spring-boot-starter-web-services'
  implementation 'org.thymeleaf.extras:thymeleaf-extras-springsecurity6'
  runtimeOnly 'com.microsoft.sqlserver:mssql-jdbc'
  compileOnly 'org.projectlombok:lombok'
  developmentOnly 'org.springframework.boot:spring-boot-devtools'
  annotationProcessor 'org.projectlombok:lombok'

  testImplementation 'org.springframework.boot:spring-boot-starter-test'
  testImplementation 'org.springframework.security:spring-security-test'
  testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

tasks.named('test') {
  useJUnitPlatform()
}
```

<br /><br /><br />

2. dependencies의 종류 설명

```
작성 중
```
