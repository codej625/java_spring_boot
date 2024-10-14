# OpenJDK 64-bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended

<br /><br />

```
OpenJDK 64-bit Server VM warning: Sharing is only supported for boot loader classes because bootstrap classpath has been appended

위에 오류는 JDK 1.8 버전 이후에 추가된 기능으로,
클래스 로딩 시 Class Data Sharing(CDS) 기능을 사용하면 발생한다.
```

<br /><br /><br />

* 해결 방법
---

1. build.gradle 파일을 열고 예시처럼 추가해준다.

```gradle
    ...

    testImplementation 'org.springframework.security:spring-security-test'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

tasks.named('test') {
    useJUnitPlatform()
    jvmArgs '-Xshare:off' // JVM 아규먼트 설정 <- 이부분을 추가
}
```
