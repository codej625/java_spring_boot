# 코드를 추가하지 말고 확장해서 조건을 처리하는 방법

<br />
<br />

* 코드를 추가하는 방법이 왜 좋지 않을까?
---

```
기존 스프링으로 만들어진 SI 프로젝트를 보면,
백엔드, 프론트엔드를 가리지 않고 어지러울 정도로 조건문 범벅이 되어있다.

나중에 고도화를 하든, 리팩토링을 하든 과연 가능이나 할까?
싶은 의문이 든다.

이를 해결하기 위해선 코드를 확장하는 방법을 알아야 한다.
```

<br />
<br />
<br />
<br />

1. 코드를 계속해서 추가하면 생기는 문제점

<br />

`코드 복잡성 증가`

```
새로운 코드를 추가하면 기존 코드와 충돌하거나 예상치 못한 결과를 초래할 수 있다.

특히, 조건문이 복잡해질수록 코드의 가독성과 유지 보수성이 떨어진다.
```

<br />

`중복 코드 발생` 

```
유사한 기능을 수행하는 코드를 여러 곳에 추가하면 코드 중복이 발생한다.

중복된 코드는 수정 시 모든 부분을 변경해야 하므로 유지 보수가 어렵고 오류 발생 가능성이 높다.
```

<br />

`테스트 어려움`

```
코드가 복잡해지면 테스트해야 할 범위가 넓어져 테스트가 어려워진다.

이는 코드의 안정성을 떨어뜨릴 수 있다.
```

<br />

`성능 저하`

```
불필요한 코드를 추가하면 프로그램의 성능이 저하될 수 있다.

특히, 반복문이나 조건문 내부에 코드를 추가하면 성능에 큰 영향을 미칠 수 있다.
```

<br />

`메모리 사용량 증가`

```
필요 없는 변수나 객체를 생성하는 코드를 추가하면 메모리 사용량이 증가한다.

이는 프로그램의 효율성을 떨어뜨릴 수 있다.
```

<br />
<br />
<br />

2. 코드를 확장하여 사용하는 예시

<br />

```
편의상 interface, class를 별도로 작성하지는 않았다.

간단한 예시를 보면서 감을 익혀보자.
```

```java
// 인터페이스 정의
public interface ConditionProcessor {
    boolean supports(String condition);  // 해당 조건을 처리할 수 있는지 확인
    void process(String condition);      // 조건에 따른 처리 로직
}

// 구체적인 프로세서 구현 (조건 A)
import org.springframework.stereotype.Service;

@Service
public class ConditionAProcessor implements ConditionProcessor {
    @Override
    public boolean supports(String condition) {
        return "A".equals(condition);
    }

    @Override
    public void process(String condition) {
        System.out.println("Processing condition A");
    }
}

// 구체적인 프로세서 구현 (조건 B)
@Service
public class ConditionBProcessor implements ConditionProcessor {
    @Override
    public boolean supports(String condition) {
        return "B".equals(condition);
    }

    @Override
    public void process(String condition) {
        System.out.println("Processing condition B");
    }
}

// 서비스 레이어에서 프로세서 관리 및 사용
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConditionService {
    private final List<ConditionProcessor> processors;

    // 스프링이 모든 ConditionProcessor 구현체를 자동 주입
    @Autowired
    public ConditionService(List<ConditionProcessor> processors) {
        this.processors = processors;
    }

    // 조건을 처리하는 메서드
    public void handleCondition(String condition) {
        for (ConditionProcessor processor : processors) {
            if (processor.supports(condition)) {
                processor.process(condition);
                return;
            }
        }
        throw new IllegalArgumentException("No processor found for condition: " + condition);
    }
}

// 컨트롤러에서 서비스 호출
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConditionController {
    private final ConditionService conditionService;

    @Autowired
    public ConditionController(ConditionService conditionService) {
        this.conditionService = conditionService;
    }

    @GetMapping("/process")
    public String processCondition(@RequestParam String condition) {
        conditionService.handleCondition(condition); // 서비스 레이어 호출
        return "Condition processed";
    }
}
```

```
@GetMapping("/process"): /process?condition=값 형태의 GET 요청을 처리한다.

processCondition: conditionService.handleCondition을 호출하고 결과를 반환한다.
```

<br />

```
// 사용 예시

조건 A 처리
요청: GET /process?condition=A
결과: 콘솔에 "Processing condition A" 출력

조건 B 처리
요청: GET /process?condition=B
결과: 콘솔에 "Processing condition B" 출력

처리할 수 없는 조건
요청: GET /process?condition=X
결과: "No processor found for condition: X" 예외 발생
```
