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

<br />

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

`공통 인터페이스 정의`

```java
// NotificationService.java

public interface NotificationService {
    // 이 서비스가 처리하는 알림 타입을 반환하는 메서드 (전략 선택에 사용)
    String getType();

    // 알림을 보내는 메서드
    void send(String message);
}
```

<br />

`구체적인 전략 구현 클래스`

```java
// EmailNotificationService.java

import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationService {

    @Override
    public String getType() {
        return "email"; // 이 전략은 'email' 타입을 처리한다.
    }

    @Override
    public void send(String message) {
        System.out.println("Sending email notification: " + message);
        // 실제 이메일 발송 로직
    }
}

// 각 알림 타입별 구현 클래스를 만들고 @Service 어노테이션을 붙여 Spring 빈으로 자동 등록되게 한다.
```

```java
// SmsNotificationService.java

import org.springframework.stereotype.Service;

@Service
public class SmsNotificationService implements NotificationService {

    @Override
    public String getType() {
        return "sms"; // 이 전략은 'sms' 타입을 처리한다.
    }

    @Override
    public void send(String message) {
        System.out.println("Sending SMS notification: " + message);
        // 실제 SMS 발송 로직
    }
}
```

```java
// PushNotificationService.java

import org.springframework.stereotype.Service;

@Service
public class PushNotificationService implements NotificationService {

    @Override
    public String getType() {
        return "push"; // 이 전략은 'push' 타입을 처리한다.
    }

    @Override
    public void send(String message) {
        System.out.println("Sending Push notification: " + message);
        // 실제 Push 알림 발송 로직
    }
}
```

<br />

`컨텍스트/핸들러 클래스`

```
적절한 NotificationService 구현체를 찾아 실행하는 클래스이다.

Spring은 인터페이스(NotificationService)를 구현하는 모든 빈을 자동으로 주입해 줄 수 있다.

여기서는 Map 형태로 주입받아 getType()으로 키를 만들고, 해당 서비스를 찾아 사용한다.
```

```java
// NotificationHandler.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component // 또는 @Service 등 Spring 빈으로 등록
public class NotificationHandler {

    // NotificationService 인터페이스를 구현하는 모든 빈을 List로 주입받음
    @Autowired
    private List<NotificationService> notificationServices;

    // 알림 타입(String)을 키로, 해당 NotificationService 빈을 값으로 가지는 Map
    private Map<String, NotificationService> notificationServiceMap;

    // 의존성 주입이 완료된 후 Map을 초기화 (Spring 4.3 이후로는 생성자 주입 시 @Autowired 생략 가능)
    @PostConstruct
    public void init() {
        notificationServiceMap = new HashMap<>();
        for (NotificationService service : notificationServices) {
            notificationServiceMap.put(service.getType(), service);
        }
    }

    /**
     * 주어진 타입에 해당하는 NotificationService를 찾아 알림을 보낸다.
     * @param type 알림 타입 ex) "email", "sms", "push"
     * @param message 보낼 메시지
     */
    public void sendNotification(String type, String message) {
        NotificationService service = notificationServiceMap.get(type);

        if (service == null) {
            throw new IllegalArgumentException("Unsupported notification type: " + type);
        }

        service.send(message); // 찾은 서비스의 send 메서드 실행
    }
}
```

<br />
<br />
<br />

3. 사용하기

```
이제 다른 서비스나 컨트롤러에서,
NotificationHandler를 주입받아 사용하면 된다.
```

```java
// SomeController.java

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {

    @Autowired
    private NotificationHandler notificationHandler;

    @GetMapping("/send")
    public String send(@RequestParam String type, @RequestParam String message) {
        try {
            notificationHandler.sendNotification(type, message);
            return "Notification sent (" + type + ")";
        } catch (IllegalArgumentException e) {
            return "Error: " + e.getMessage();
        }
    }
}
```
