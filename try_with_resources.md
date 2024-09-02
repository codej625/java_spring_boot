# try-with-resources

<br /><br />

```
자동으로 리소스를 닫아 주어 메모리 누수나 다른 자원 문제를 방지하는 데 유용한
try-with-resources 구문을 사용해보자.
```

<br /><br /><br />

1. 예시 (finally 사용)
```java
BufferedReader reader = null;
try {
    reader = new BufferedReader(new FileReader("example.txt"));
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
} catch (IOException e) {
    e.printStackTrace();
} finally {
    // 리소스를 명시적으로 닫아야 함
    if (reader != null) {
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

<br /><br />

2. 예시 (try-with-resources 사용)
```java
BufferedReader reader = null;
try (BufferedReader reader = new BufferedReader(new FileReader("example.txt"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
} catch (IOException e) {
    e.printStackTrace();
}
// 리소스는 자동으로 닫히며, 별도로 close() 호출이 필요 없음
```

<br />

```
주의할 사항으로는 try-with-resources 구문에서 사용하는 리소스는
AutoCloseable 인터페이스를 구현해야 한다.

AutoCloseable은 Java 7에서 도입된 인터페이스로, close() 메서드를 포함한다.

java.io.Closeable 인터페이스는 AutoCloseable을 상속받아 close() 메서드를 정의하고 있으며, 
주로 I/O 리소스에서 사용된다.
```
