# 트랜잭션 스크립트 패턴을 리팩토링 하기 ( + 추상화)

<br />
<br />

* 트랜잭션 스크립트란?
---

```
트랜잭션 스크립트 패턴은 애플리케이션의 비즈니스 로직을 조직화하는 가장 기본적인 패턴 중 하나이다.

이 패턴에서는 각 비즈니스 트랜잭션(또는 사용자 요청)마다,
해당 요청을 처리하는 단일 스크립트(또는 프로시저, 함수)를 가진다.

로직이 복잡하지 않을 때는 이해하기 쉽고, 빠르게 구현할 수 있다.

얼추 보기엔 아무런 문제가 없다.
(오히려 대기업 SI 프로젝트에서도 많이 사용되고 있다.)

하지만 지속적인 프로젝트의 유지보수 관점으로 본다면 큰 문제점이 발생한다.

어떤 게 문제이고 해결 방법이 무엇인지 알아보자.
```

<br />
<br />
<br />
<br />

1. 문제점

<br />

`1) 유지보수성 저하`

```
애플리케이션의 규모가 커지고 비즈니스 로직이 복잡해질수록,
스크립트가 매우 길어지고 가독성이 떨어진다.
```

<br />

`2) 코드 중복`

```
여러 트랜잭션 스크립트에서 동일하거나,
유사한 로직(예: 데이터 검증, 계산 방식)이 반복될 가능성이 높다.
(보일러 플레이트 발생)
```

<br />

`3) 재사용성 낮음`

```
특정 스크립트의 일부 로직을
다른 곳에서 재사용하기 어렵다.
```

<br />

`4) 테스트의 어려움`

```
긴 스크립트 전체를 테스트해야 하므로,
단위 테스트가 어렵고 통합 테스트의 비중이 커진다.
```

<br />
<br />

```java
// 트랜잭션 스크립트 예시 코드

// PostServiceBad.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // 트랜잭션 관리

@Service
public class PostServiceBad {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    // PointService와 NotificationService를 직접 주입받지만,
    // 여기서 모든 로직을 다 처리하는 것은 아님 (위임은 할 수 있으나, 여기서는 로직 자체를 나열하는 예시)
    // 만약 포인트 계산, 알림 메시지 생성 등까지 여기서 한다면 더 나빠짐.
    // 일단은 호출만 한다고 가정하고, 중요한 것은 PostServiceBad 메소드 안에서 모든 단계가 순서대로 나열된다는 것.

    @Transactional // 이 메소드 전체가 하나의 트랜잭션으로 묶임
    public Post createPost(PostCreationRequest request) {
        System.out.println("--- 게시글 작성 시작 (나쁜 예) ---");

        // 1. 입력 데이터 검증
        System.out.println("⚫️ 입력 데이터 검증 시작");
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 필수입니다.");
        }
        if (request.getAuthorId() == null) {
             throw new IllegalArgumentException("작성자 ID는 필수입니다.");
        }
        // 작성자 ID로 사용자 존재 여부 확인
        if (!userRepository.existsById(request.getAuthorId())) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
        System.out.println("⚫️ 입력 데이터 검증 완료");


        // 2. 게시글 생성 및 저장
        System.out.println("⚫️ 게시글 저장 시작");
        Post newPost = new Post(request.getTitle(), request.getContent(), request.getAuthorId());
        Post savedPost = postRepository.save(newPost);
        System.out.println("⚫️ 게시글 저장 완료: " + savedPost);

        // 3. 작성자에게 포인트 지급 (여기서 직접 계산하거나 로직을 포함한다면 더 나빠짐)
        // 간단히 별도 서비스를 호출한다고 해도, 이 메소드 안에 이 단계가 순서대로 들어있음.
        System.out.println("⚫️ 포인트 지급 시작");
        // PointService pointService = new PointService(); // 이렇게 직접 생성하면 스프링 관리 안됨 - @Autowired 사용 가정
        // pointService.awardPointsForPostCreation(savedPost.getAuthorId(), 10); // 포인트 로직 호출
         System.out.println("⚫️ [포인트 로직] 사용자 ID " + savedPost.getAuthorId() + "에게 게시글 작성으로 10 포인트를 지급합니다."); // 로직 포함 예시
        System.out.println("⚫️ 포인트 지급 완료");


        // 4. 새로운 게시글 알림 발송 (여기서 직접 알림 메시지 생성, 발송 로직을 포함한다면 더 나빠짐)
        // 간단히 별도 서비스를 호출한다고 해도, 이 메소드 안에 이 단계가 순서대로 들어있음.
        System.out.println("⚫️ 알림 발송 시작");
        // NotificationService notificationService = new NotificationService(); // 이렇게 직접 생성하면 스프링 관리 안됨 - @Autowired 사용 가정
        // notificationService.notifyNewPost(savedPost.getId(), savedPost.getTitle(), savedPost.getAuthorId()); // 알림 로직 호출
         System.out.println("⚫️ [알림 로직] 새로운 게시글이 등록되었습니다! [ID: " + savedPost.getId() + ", 제목: '" + savedPost.getTitle() + "']"); // 로직 포함 예시
        System.out.println("⚫️ 알림 발송 완료");


        System.out.println("--- 게시글 작성 완료 (나쁜 예) ---");
        return savedPost;
    }
}
```

```
PostServiceBad의 createPost 메소드는 게시글 작성과 관련된 모든 단계의 책임을 한꺼번에 지고 있다.

코드가 길어지고, 각 단계의 수정이 다른 단계에 영향을 줄 수 있으며, 
포인트 지급 로직만 변경하거나 알림 방식을 바꾸려면 이 메소드를 수정해야 한다.
```

<br />
<br />
<br />
<br />

2. 해결 방법

<br />

`결합도를 낮추는 시나리오`

```
PostService는 이제 게시글 작성이라는 전체 프로세스를 조율하는 역할만 하고,
각 세부적인 단계는 해당 로직을 책임지는 다른 객체(private 메소드 또는 다른 서비스)에게 위임한다.
```

```java
// PostServiceGood.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostServiceGood {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository; // 사용자 정보 확인용
    @Autowired
    private PointService pointService; // 포인트 관련 로직은 PointService에 위임
    @Autowired
    private NotificationService notificationService; // 알림 관련 로직은 NotificationService에 위임

    @Transactional // 이 메소드가 여러 하위 작업의 트랜잭션을 관리
    public Post createPost(PostCreationRequest request) {
        System.out.println("--- 게시글 작성 시작 (좋은 예) ---");

        // 1. 입력 데이터 검증 (private 메소드로 분리)
        validatePostCreationRequest(request);
        checkAuthorExists(request.getAuthorId()); // 작성자 존재 확인 로직도 분리

        // 2. 게시글 생성 및 저장 (Repository에 위임)
        Post newPost = createAndSavePost(request);
        System.out.println("⚫️ 게시글 저장 완료: " + newPost);

        // 3. 작성자에게 포인트 지급 (PointService에 위임)
        awardPointsToAuthor(newPost.getAuthorId());

        // 4. 새로운 게시글 알림 발송 (NotificationService에 위임)
        notifyPostCreation(newPost.getId(), newPost.getTitle(), newPost.getAuthorId());

        System.out.println("--- 게시글 작성 완료 (좋은 예) ---");
        return newPost;
    }

    // -- private 메소드로 로직 분리 --

    private void validatePostCreationRequest(PostCreationRequest request) {
        System.out.println("⚫️ 입력 데이터 검증 시작");
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 필수입니다.");
        }
         if (request.getAuthorId() == null) {
             throw new IllegalArgumentException("작성자 ID는 필수입니다.");
        }
        System.out.println("⚫️ 입력 데이터 검증 완료");
    }

    private void checkAuthorExists(Long authorId) {
         System.out.println("⚫️ 작성자 존재 확인");
         if (!userRepository.existsById(authorId)) {
             throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
         }
         System.out.println("⚫️ 작성자 존재 확인 완료");
    }


    private Post createAndSavePost(PostCreationRequest request) {
        System.out.println("⚫️ 게시글 저장 시작");
        Post newPost = new Post(request.getTitle(), request.getContent(), request.getAuthorId());
        // Repository는 데이터 저장/조회 역할만 담당
        return postRepository.save(newPost);
    }

    // -- 다른 서비스에 로직 위임 --

    private void awardPointsToAuthor(Long authorId) {
        System.out.println("⚫️ 포인트 지급 로직 위임");
        // 포인트 지급 로직은 PointService가 알아서 처리
        pointService.awardPointsForPostCreation(authorId, 10);
    }

    private void notifyPostCreation(Long postId, String postTitle, Long authorId) {
         System.out.println("⚫️ 알림 발송 로직 위임");
        // 알림 발송 로직은 NotificationService가 알아서 처리
        notificationService.notifyNewPost(postId, postTitle, authorId);
    }
}
```

```
위처럼 메서드와 서비스를 분리하는 것이 실제 서비스 레이어에서 트랜잭션 스크립트를 피하고,
각 서비스나 메소드가 명확한 역할과 책임을 가지도록 하여 결합도를 낮추는 기본적인 방법이다.

처음에는 private 메소드로 분리하는 것부터 시작하고,
로직이 복잡해지거나 다른 도메인과 관련된 작업이면 별도의 서비스 클래스로 분리하는 것을 고려해 본다.
```

<br />
<br />
<br />
<br />

3. 추상화를 더하기

```
if와 같은 조건문이 많아지면 코드의 추가가 아닌 수정이 이루어지게 되고,
그 결과로 예측할 수 없는 사이드 이펙트가 발생한다.

추상화를 통해 재사용성과 사이드 이펙트를 예방해 보자.
```

```java
// 예시로 위에 코드에서 추상화를 적용할 부분

private void validatePostCreationRequest(PostCreationRequest request) {
    System.out.println("⚫️ 입력 데이터 검증 시작");
    if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
        throw new IllegalArgumentException("제목은 필수입니다.");
    }
    if (request.getContent() == null || request.getContent().trim().isEmpty()) {
        throw new IllegalArgumentException("내용은 필수입니다.");
    }
     if (request.getAuthorId() == null) {
         throw new IllegalArgumentException("작성자 ID는 필수입니다.");
    }
    System.out.println("⚫️ 입력 데이터 검증 완료");
}
```

<br />
<br />

`1) 입력 객체를 받아서 유효성을 검사하는 역할의 인터페이스`

```java
// PostCreationValidator.java

public interface PostCreationValidator {
    // 요청 객체가 유효하지 않으면 예외를 발생시킨다.
    void validate(PostCreationRequest request);
}
```

<br />

`2) 각 검증 규칙별 구현체 작성`

```java
// TitleNotEmptyValidator.java
import org.springframework.stereotype.Component;

@Component
public class TitleNotEmptyValidator implements PostCreationValidator {

    @Override
    public void validate(PostCreationRequest request) {
        System.out.println("⚫️ [Validator] 제목 비어있지 않음 검증");
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
    }
}

// ContentNotEmptyValidator.java
import org.springframework.stereotype.Component;

@Component
public class ContentNotEmptyValidator implements PostCreationValidator {

    @Override
    public void validate(PostCreationRequest request) {
        System.out.println("⚫️ [Validator] 내용 비어있지 않음 검증");
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 필수입니다.");
        }
    }
}

// AuthorExistsValidator.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorExistsValidator implements PostCreationValidator {

    @Autowired
    private UserRepository userRepository; // 사용자 존재 확인을 위해 필요

    @Override
    public void validate(PostCreationRequest request) {
        System.out.println("⚫️ [Validator] 작성자 존재 검증");
         if (request.getAuthorId() == null) {
             // 이 검증은 Title/Content처럼 null 체크만 하는 별도 Validator로 분리할 수도 있다.
             // 여기서는 AuthorExists와 함께 처리한다고 가정
             throw new IllegalArgumentException("작성자 ID는 필수입니다.");
        }
        if (!userRepository.existsById(request.getAuthorId())) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }
    }
}
```

```
이제 각 Validator 구현체들은 자신이 담당하는 특정 검증 규칙에 대한 책임만 갖는다.

TitleNotEmptyValidator는 제목만 보고,
ContentNotEmptyValidator는 내용만 보고,
AuthorExistsValidator는 작성자 ID만 본다.
```

<br />

`3) 검증 로직들을 묶어주는 Composite Class 작성`

```
이 여러 개의 개별적인 Validator들을 순서대로 실행시켜주는 역할을 하는 클래스가 필요하다. (응집)

이 클래스 역시 PostCreationValidator 인터페이스를 구현하여,
PostService는 이 조율자를 단일 Validator처럼 사용할 수 있다.
```

```java
// PostCreationValidationChain.java 또는 CompositePostCreationValidator.java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component // 스프링 빈으로 등록
// PostCreationValidator 인터페이스도 구현하여 단일 Validator 처럼 사용될 수 있게 함
public class PostCreationValidationChain implements PostCreationValidator {

    // 스프링이 PostCreationValidator 인터페이스를 구현하는 모든 빈들을 리스트로 주입한다.
    private final List<PostCreationValidator> validators;

    @Autowired
    public PostCreationValidationChain(List<PostCreationValidator> validators) {
        // 주입받을 때 원하는 순서로 정렬하거나 필터링하는 로직이 필요할 수 있다.
        // 여기서는 주입된 순서대로 실행한다고 가정.
        this.validators = validators;
        System.out.println("⚫️ [Validator Chain] 등록된 Validator 개수: " + validators.size());
        validators.forEach(v -> System.out.println("  - " + v.getClass().getSimpleName()));
    }

    @Override
    public void validate(PostCreationRequest request) {
        System.out.println("⚫️ [Validator Chain] 전체 검증 시작");
        // 주입받은 모든 Validator를 순서대로 실행한다.
        for (PostCreationValidator validator : validators) {
            validator.validate(request); // 각 Validator의 validate 메소드 호출
            // 만약 중간에 어떤 Validator라도 예외를 발생시키면, 여기서 전체 검증은 중단된다.
        }
        System.out.println("⚫️ [Validator Chain] 전체 검증 완료");
    }
}
```

```
PostCreationValidationChain는 PostCreationValidator 타입의 빈들을 리스트로 주입받아,
요청이 들어오면 이 리스트에 있는 Validator들을 순회하며 validate 메소드를 호출한다.

각 개별 Validator는 자신의 책임만 다하고,
문제가 없으면 다음 Validator로 넘어간다.
```

<br />

`4) PostServiceGood 수정 (ValidationChain 주입 및 사용)`

```
PostServiceGood는 이전 예시와 거의 동일하다.

단지 주입받는 PostCreationValidator 구현체가 개별 Validator가 아닌,
ValidationChain 이라는 점이 다르다.
```

```java
// PostServiceGood.java (최종 수정 버전)

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostServiceGood {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PointService pointService;
    @Autowired
    private NotificationService notificationService;

    // Validation Chain을 주입 받는다. 이 Chain은 PostCreationValidator 인터페이스를 구현합니다.
    @Autowired
    private PostCreationValidator postCreationValidator; // 이제 이 변수에는 PostCreationValidationChain 객체가 주입됨


    @Transactional
    public Post createPost(PostCreationRequest request) {
        System.out.println("--- 게시글 작성 시작 (좋은 예 - Validator Chain 적용) ---");

        // 1. 입력 데이터 검증 (Validator Chain에게 위임)
        // 체인의 validate 메소드를 호출하면, 내부적으로 모든 개별 Validator들이 순차적으로 실행된다.
        postCreationValidator.validate(request);
        // 체인 내부에서 어떤 Validator라도 유효성 검사에 실패하면 예외가 발생하여 여기서 중단된다.

        // 2. 게시글 생성 및 저장 (Repository에 위임)
        Post newPost = createAndSavePost(request);
        System.out.println("⚫️ 게시글 저장 완료: " + newPost);

        // 3. 작성자에게 포인트 지급 (PointService에 위임)
        awardPointsToAuthor(newPost.getAuthorId());

        // 4. 새로운 게시글 알림 발송 (NotificationService에 위임)
        notifyPostCreation(newPost.getId(), newPost.getTitle(), newPost.getAuthorId());

        System.out.println("--- 게시글 작성 완료 (좋은 예 - Validator Chain 적용) ---");
        return newPost;
    }

    // -- private 메소드는 필요시 남겨놓기 --

    private Post createAndSavePost(PostCreationRequest request) {
        System.out.println("⚫️ 게시글 저장 시작");
        Post newPost = new Post(request.getTitle(), request.getContent(), request.getAuthorId());
        return postRepository.save(newPost);
    }

    // -- 다른 서비스에 로직 위임 --
    private void awardPointsToAuthor(Long authorId) {
        System.out.println("⚫️ 포인트 지급 로직 위임");
        pointService.awardPointsForPostCreation(authorId, 10);
    }

    private void notifyPostCreation(Long postId, String postTitle, Long authorId) {
         System.out.println("⚫️ 알림 발송 로직 위임");
        notificationService.notifyNewPost(postId, postTitle, authorId);
    }
}
```
