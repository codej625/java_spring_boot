# void 타입의 리턴

<br /><br />

```
Java에서 Method 리턴 타입을 void로 지정하면,
(예시 public void {method_name}() {})
해당 메서드는 반환 값이 없다.

그래서 메서드 중간 특정 값을 리턴할수도 종료시킬 수도 없다.
그럴 때는 값을 적지 않고,
return; 이렇게 적어주면 된다.
```

<br /><br /><br />

1. 예시

```java
public void checkValue(int value) {
    if (value < 1) {
        return; // value의 값이 1보다 작을 시 메서드 종료.
    }
    // ... 로직
}
```
