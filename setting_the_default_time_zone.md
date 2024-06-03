# 기본 시간대를 설정해보자.

<br /><br />

```
스프링부트 Application이 시작될 때, 중요한 설정을 함께 작동시킬 수 있다.

예를 들면 시간대를 초기화하거나, 기본값을 설정하는 경우이다.
```

<br /><br /><br />

* 예시
```java
@SpringBootApplication
public class SpringApplication {
  public void started() {
    TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.of("Asia/Seoul")));
  }
  public static void main(String[] args) {
    SpringApplication.run(${class_name}Application.class, args);
  }
}
```
