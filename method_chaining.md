# 메서드 체이닝에 대해 알아보자.

<br /><br />

* 메서드 체이닝(Method Chaining)
---

```
메서드 체인(Method chaining)은 객체 지향 프로그래밍에서 사용되는 기법 중 하나이다.
이는 한 객체에서 여러 메서드 호출을 연결하여 사용하는 방식을 말한다.
```

<br /><br /><br />

* 예시
```
일반적으로 메서드 체인을 사용하면 하나의 객체에 대해 여러 메서드를 연이어 호출할 수 있다.
각 메서드는 해당 객체를 반환하므로,
다음 메서드 호출은 반환된 객체를 대상으로 이루어진다.
이러한 방식으로 코드를 작성하면 중복을 줄이고 가독성을 높일 수 있다.

예를 들어, 다음과 같은 클래스가 있다고 가정.
```

<br />

```java
/*
  Person 클래스에서 setName()과 setAge() 메서드는 각각 Person 객체의 이름과 나이를 설정하고,
  printInfo() 메서드는 객체의 정보를 출력한다.
*/
public class Person {

  private String name;
  private int age;

  public Person setName(String name) {
    this.name = name;
    return this;
  }

  public Person setAge(int age) {
    this.age = age;
    return this;
  }

  public void printInfo() {
    System.out.println("Name: " + name);
    System.out.println("Age: " + age);
  }
}
```

<br />

```java
/* 메서드 체인을 사용하여 이 클래스의 객체를 생성하고 설정할 수 있다. */
Person person = new Person()
                    .setName("John")
                    .setAge(30);
```

<br /><br />

```
* Tip

자바스크립트에서의 .은 객체의 속성이나 메서드에 접근하기 위해 사용되며,
자바에서의 .은 메서드 체이닝에서의 다음 동작을 나타낸다.
```
