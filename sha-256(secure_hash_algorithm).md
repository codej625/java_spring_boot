# SHA-256 (Secure Hash Algorithm) 구현 방법

<br /><br />

1. dependencies 추가
```gradle
dependencies {
  ..
  // commons-codec(SHA-256)
  implementation 'commons-codec:commons-codec:1.15'
  ..
}
```

<br>

2. Util Class 추가 (혹은 Config방식으로 구현 가능)
```java
@Component
public class EncryptionUtil {

    // SHA-256 암호화
    public String sha256(String plaintext) {
        return DigestUtils.sha256Hex(plaintext);
    }
}
```
or
```java
@Configuration
public class SpringConfig {
    
    @Bean
    public DigestUtils digestUtils() { return new DigestUtils(); }
}
```

<br />

3. Autowierd 후 sha256Hex() 메서드 사용
```java
DigestUtils.sha256Hex({value});
```

<br /><br /><br />

* 해시값은 변하지 않는다.
```
예시 ->

입력 문자열: "Hello, world!"
SHA-256 해시 값: b94d27b9934d3e08a52e52d7da7dabf9d1c3c99a5e94b547c8397b1c1715a2b4

다시 같은 입력 문자열: "Hello, world!"
SHA-256 해시 값: b94d27b9934d3e08a52e52d7da7dabf9d1c3c99a5e94b547c8397b1c1715a2b4
```
