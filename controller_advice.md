# @ControllerAdvice

<br /><br />

* @ControllerAdvice?

```
@ControllerAdvice는 스프링 MVC의 애노테이션으로,
컨트롤러 전역에서 공통적으로 적용할 수 있는 처리 로직을 정의하는 데 사용된다.

이를 통해 여러 컨트롤러에서 발생하는 예외를 중앙집중식으로 처리하거나,
데이터 바인딩, 모델 속성을 공통으로 설정할 수 있다.
```

<br /><br /><br />

1. 전역 예외 처리 (Global Exception Handling)

```
애플리케이션의 모든 컨트롤러에서 발생하는 예외를
하나의 클래스에서 처리할 수 있다.

@ExceptionHandler 애노테이션을 사용하여 특정 예외를 처리하는 메서드를 정의한다.
```

<br />

```java
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<String> handleException(Exception ex) {
        // 전역 예외 처리 로직
        return new ResponseEntity<>("예외 -> " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

<br />

```
이 클래스는 애플리케이션 전역에서 발생하는 Exception을 처리한다.

@ExceptionHandler 애노테이션을 사용하여 처리할 예외를 명시하고,
ResponseEntity를 통해 HTTP 상태 코드와 메시지를 반환한다.
```

<br /><br /><br />

2. 전역 데이터 바인딩 (Global Data Binding)

```
모든 컨트롤러에 대해
공통적인 데이터 바인딩 규칙을 설정할 수 있다.

@ControllerAdvice와 @InitBinder를 함께 사용하여,
모든 컨트롤러에 대해 공통적인 데이터 바인딩 규칙을 적용할 수 있다.
```

<br />

```java
@ControllerAdvice
public class GlobalBindingConfig {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
}
```

<br />

```
이 코드는 모든 컨트롤러에서
Date 객체를 "yyyy-MM-dd" 형식으로 파싱하도록 설정한다.
```

<br /><br /><br />

3. 전역 모델 속성 (Global Model Attributes)
```
모든 컨트롤러에서 사용할 공통 모델 속성을 설정할 수 있다.

@ControllerAdvice와 @ModelAttribute를 사용하여
모든 컨트롤러에서 공통적으로 사용할 모델 속성을 추가할 수 있다.
```

<br />

```java
@ControllerAdvice
public class GlobalModelAdvice {

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("appName", "My Spring Boot Application");
    }
}
```

<br />

```
이 코드는 모든 컨트롤러에서 appName이라는 모델 속성을 자동으로 추가한다.
이 속성은 모든 뷰에서 사용할 수 있다.
```

<br /><br /><br />

* 정리

```
* 우선 순위

@ControllerAdvice는 특정 컨트롤러에 대한 로컬 예외 처리보다 우선처리
따라서 전역 예외 처리가 로컬 예외 처리보다 먼저 적용.
```

<br />

```
* 범위

@ControllerAdvice는 @RestController,
@Controller와 함께 사용할 수 있다.
모든 컨트롤러에서 일관된 로직을 적용할 수 있다.
```

<br />

```
* 복합 사용

전역 예외 처리와 로컬 예외 처리(@ExceptionHandler를
컨트롤러에 직접 정의하는 방식)를 함께 사용할 수 있다.

이는 세세한 예외 처리를 필요로 할 때 유용하다.
```
