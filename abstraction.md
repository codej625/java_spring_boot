# 추상화

<br />
<br />

* 추상화의 목적
---

<br />

`복잡성 감소`

```
복잡한 시스템을 단순화하기 위해 불필요한 세부사항을 숨기고,
공통적인 기능만을 드러내어 사용한다.
```

<br />

`재사용`

```
성공통된 기능을 추상화된 형태로 정의해 놓음으로써 코드의 재사용성을 높이고,
변경이 필요할 때 더 쉽게 수정할 수 있다.
```

<br />
<br />
<br />
<br />

* 추상화의 구현
---

<br />

`인터페이스 (Interface)`

```
메서드의 시그니처만을 정의하고, 실제 구현은 구현 클래스에서 한다.

예를 들어, Playable이라는 인터페이스는 play()라는 메서드를 선언하고,
Game이나 Movie 클래스가 이를 구현하여 자신만의 play() 메서드를 제공할 수 있다.
```

<br />

`추상 클래스 (Abstract Class)`
```
일부 메서드에 대한 구현을 제공할 수 있으며,
다른 메서드는 추상 메서드로 남겨놓아 자식 클래스에서 구현하도록 강제한다.

예를 들어, Shape라는 추상 클래스는 draw()라는 추상 메서드를 가질 수 있으며,
Circle, Square 같은 구체적인 클래스는 이 메서드를 자신의 방식으로 구현한다.
```

```
"인터페이스"는 공통된 기능의 계약을 정의하며, 구현을 강제한다.
(자바 1.8에서부터 디폴트 메서드와, 정적 메서드를 지원) 

"추상 클래스"는 기본적인 기능을 제공하면서,
특정 메서드는 자식 클래스에서 구현하도록 강제한다.
```

<br />
<br />
<br />

1. interface 사용 예시

```
Strategy Pattern (전략 패턴)을
Spring의 의존성 주입(Dependency Injection) 기능을 활용하여 구현해보자.

예를 들면,
밑과 같은 분기 처리가 있다고 가정
```

```java
// ex) 알림 발송
public void sendNotification(String type, String message) {
    if ("email".equals(type)) {
        // 이메일 발송 로직
        System.out.println("Sending email: " + message);
    } else if ("sms".equals(type)) {
        // SMS 발송 로직
        System.out.println("Sending SMS: " + message);
    } else if ("push".equals(type)) {
        // Push 알림 발송 로직
        System.out.println("Sending Push: " + message);
    } else {
        throw new IllegalArgumentException("Unsupported notification type: " + type);
    }
}
```

```
새로운 알림 방식이 추가될 때마다 이 메서드를 수정해야 하고,
코드가 길어지고 복잡해지며, 실수할 가능성이 높아진다.

위의 문제를 인터페이스(추상화)를 사용해서 해결하자
```

<br />
<br />
<br />

2. interface 및 class 정의

<br />

`패키지 구조`

```
notification-system/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/notification/
│   │   │       ├── NotificationApplication.java
│   │   │       ├── controller/
│   │   │       │   └── NotificationController.java
│   │   │       ├── exception/
│   │   │       │   └── GlobalExceptionHandler.java
│   │   │       ├── service/
│   │   │       │   ├── NotificationHandler.java
│   │   │       │   ├── NotificationService.java
│   │   │       │   └── impl/
│   │   │       │       ├── EmailNotificationService.java
│   │   │       │       ├── SmsNotificationService.java
│   │   │       │       └── PushNotificationService.java
│   │   └── resources/
│   │       └── application.properties
├── .gitignore
├── README.md
├── pom.xml
```

<br />
<br />
<br />

3. 애플리케이션 진입점

```java
// Spring Boot 애플리케이션의 메인 클래스

package com.example.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }
}
```

<br >
<br >
<br >

4. 공통 인터페이스

```java
// 알림 서비스의 인터페이스로, `send` 메서드만 정의

package com.example.notification.service;

public interface NotificationService {
    void send(String message);
}
```

<br >
<br >
<br >

5. 전략 구현 클래스

```java
// 각 알림 타입(`email`, `sms`, `push`)에 대한 구현체로, `@Service` 어노테이션에 빈 이름을 지정

package com.example.notification.service.impl;

import com.example.notification.service.NotificationService;
import org.springframework.stereotype.Service;

@Service("email")
public class EmailNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Sending email notification: " + message);
    }
}
```

<br >

```java
package com.example.notification.service.impl;

import com.example.notification.service.NotificationService;
import org.springframework.stereotype.Service;

@Service("sms")
public class SmsNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Sending SMS notification: " + message);
    }
}
```

<br />

```java
package com.example.notification.service.impl;

import com.example.notification.service.NotificationService;
import org.springframework.stereotype.Service;

@Service("push")
public class PushNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("Sending Push notification: " + message);
    }
}
```

<br >
<br >
<br >

6. 핸들러 클래스

```java
// 알림 타입에 따라 적절한 서비스를 찾아 실행한다.

package com.example.notification.service;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NotificationHandler {

    private final Map<String, NotificationService> notificationServices;

    public NotificationHandler(Map<String, NotificationService> notificationServices) {
        this.notificationServices = notificationServices;
    }

    public void sendNotification(String type, String message) {
        NotificationService service = notificationServices.get(type);
        if (service == null) {
            throw new IllegalArgumentException("Unsupported notification type: " + type);
        }
        service.send(message);
    }
}
```

<br >
<br >
<br >

7. 컨트롤러

```java
// REST API 엔드포인트를 제공하며, 예외 처리는 전역 핸들러에 위임

package com.example.notification.controller;

import com.example.notification.service.NotificationHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    private final NotificationHandler notificationHandler;

    public NotificationController(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    @GetMapping("/send")
    public String send(@RequestParam String type, @RequestParam String message) {
        notificationHandler.sendNotification(type, message);
        return "Notification sent (" + type + ")";
    }
}
```

<br >
<br >
<br >

8. 전역 예외 처리

```java
// `IllegalArgumentException`을 전역적으로 처리

package com.example.notification.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
```
