# BCrypt 단방향 암호화

<br /><br />

```
단방향 암호화란 한쪽 방향으로 암호화를 한다는 의미이다.
즉, 암호화만 가능하고 복호화는 할 수 없다.

예시를 보면서 이해해보자.
```

<br /><br /><br />

* 예시
---

<br />

1. Entity

```java
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class UsersTable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "rec_key")
  private Long recKey;

  @Column(name = "user_id")
  private String usersId;

  @Column(name = "user_password")
  private String usersPassword;

  @Column(name = "hashed_password") // hash
  private String hashedPassword;

  @Column(name = "creat_date", nullable = true) // 생성 날짜
  private LocalDateTime createDate;

  @Column(name = "update_date", nullable = true) // 변경 날짜
  private LocalDateTime updateDate;
}
```

<br /><br /><br />

2. Logic

```java
// ...

private final usersRepository usersRepository; // Repository

public boolean usersLogin(Map<String, Object> params) {

  boolean result = false;

  try {

    {
      String id = (String) params.get("id"); // Client 입력 값 
      String pw = (String) params.get("pw");

      UsersTable usersTable = usersRepository.findByUsersIdAndUsersPassword(id, pw);

      String originPassword = UsersTable.getUsersPassword();
      String hashPassword = UsersTable.getHashedPassword();

      result = matches(originPassword, hashPassword);
    }

  } catch (Exception e) { return result; }

  return result;
}
```

<br />

```java
// Select 시 필요
private boolean matches(String rawPassword, String hashedPassword) {
  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
  return passwordEncoder.matches(rawPassword, hashedPassword);
}

// Insert 시 필요
private String hashPassword(String password) {
  PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
  return passwordEncoder.encode(password);
}
```

<br /><br />

```
* 여기서는 조회를 기준으로 한다.

1) users 테이블을 조건을 주어 조회한다.

2) matches() 함수를 사용하여 입력한 비밀번호와 hash 암호화되어있는 비밀번호를 비교한다. 
```
