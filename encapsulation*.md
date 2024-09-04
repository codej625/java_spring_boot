# Encapsulation (캡슐화)

<br /><br />

```
서로 연관있는 속성과 기능들을
하나의 캡슐(capsule)로 만들어 데이터를 외부로부터 보호하는 것을 말한다.

캡슐화에는 크게 두가지의 개념이 있다.


* 데이터 보호(data protection)
외부로부터 클래스에 정의된 속성과 기능들을 보호


* 데이터 은닉(data hiding)
내부의 동작을 감추고 외부에는 필요한 부분만 노출


즉, 외부로부터 클래스에 정의된 속성과 기능들을 보호하고,
필요한부분만 외부로 노출될 수 있도록 하여 각 객체 고유의 독립성과 책임 영역을 안전하게 지키고자 하는 목적
```

<br /><br /><br />

1. 접근제어자(access modifiers)

```
접근제어자는 클래스 또는 클래스의 내부의 멤버들에 사용되어
해당 클래스나 멤버들을 외부에서 접근하지 못하도록 접근을 제한하는 역할을 한다.
```

<br />

| 접근 제어자  | 같은 클래스 내 접근 | 같은 패키지 내 접근   | 다른 패키지 내 접근 (서브클래스 포함) | 다른 패키지 내 접근 (서브클래스 제외) | 설명                                              |
|-------------|--------------------|---------------------|------------------------------------------|--------------------------------------|---------------------------------------------------|
| `public`    | 가능               | 가능                 | 가능                                     | 가능                                  | 접근제한 없음                                      |
| `protected` | 가능               | 가능                 | 가능                                     | 불가능                                | 동일 패키지 + 다른 패키지의 하위 클래스에서 접근 가능 |
| `default`   | 가능               | 가능                 | 불가능                                   | 불가능                                | 동일 패키지 내에서만 접근 가능                       |
| `private`   | 가능               | 불가능               | 불가능                                   | 불가능                                 | 동일 클래스 내에서만 접근 가능                      |


<br /><br /><br />

2. Getter, Setter 메서드

```
멤버변수(필드)에는 접근제어자를 private으로,
Getter, Setter 메서드에는 public을 주어 멤버변수에 접근하는 방식이다.
```

<br />

```java
public class Person {

    private String name;
    private int age;

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
```
