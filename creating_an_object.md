# 객체를 생성하는 방법

<br /><br />

* 여러 가지 객체 생성 방법중 장, 단점을 알아보자.
---

<br />

1. JavaBeans Pattern 
```
매개변수가 없는 생성자로 객체를 생성 후,
Setter 메서드들을 호출해 원하는 매개변수의 값을 설정하는 방식이다.
```

```java
public class Test {

  private String type = "defalut"; /* 기본값. */
  private String height;
  private String width;

  public void setType(String type) {
    this.type = type;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public String result() {
    return type + height + width;
  }
}

public class Main {

  public static void main(String[] args) {

    Test test = new Test();
    test.setHeight("100px");
    test.setWidth("200px");

    System.out.println(test.result());
  }
}
```

<br /><br /><br />

2. Constructor Pattern
```
필수 매개변수를 받는 생성자를 먼저 생성하고,
선택 매개변수 추가로 받는 생성자 등의 형태로 매개변수 개수만큼 생성자를 늘리는 방식(생성자를 오버로딩)
```

```java
User user = new User("유저1", "유저2", "유저3");

* 클래스 내부에서는 필요한 매개변수만큼 생성자를 만들어야 한다.
```

<br /><br /><br />

3. Builder Pattern
```
빌더 패턴은 메서드 체이닝을 통해 객체 생성 과정을 명확하게 표현할 수 있는데, 이 때문에 코드의 가독성이 향상된다.
다만 빌더 패턴은 빌더 객체를 생성하기 때문에 오버헤드를 불러올 수 있다.
```

```java
public class User {
  private final String firstName;
  private final String lastName;
  private final int age;

  private User(Builder builder) {
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    this.age = builder.age;
  }

  /* Builder */
  public static class Builder {

    private String firstName;
    private String lastName;
    private int age;

    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder age(int age) {
      this.age = age;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }
}
```

```java
User user = new User.Builder()
                    .firstName("codej")
                    .lastName("625")
                    .age(34)
                    .build();
```
