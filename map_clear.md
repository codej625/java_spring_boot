# Map clear

<br /><br />

```
Map 사용을 마친 후 Map을 비우는 것은 메모리 사용을 최적화하는 데 도움이 된다.
스코프를 사용하거나 맵을 클리어해서 여러 방법으로 메모리 최적화 방법을 알아보자.
```

<br /><br /><br />

1. Map remove

```java
Map<String, Object> testMap = new HashMap<String, Object>();

testMap.put("red", "apple");
testMap.put("green", "melon");
testMap.put("yellow", "banana");

System.out.println("testMap: " + testMap);

// remove() 를 사용하여 해당 key, value 삭제 
testMap.remove("red");

System.out.println("remove 후 testMap: " + testMap);
```

<br /><br /><br />

2. Map clear

```java
Map<String, Object> testMap = new HashMap<String, Object>();

testMap.put("red", "apple");
testMap.put("green", "melon");
testMap.put("yellow", "banana");

System.out.println("testMap : " + testMap);

// clear() 를 사용하여 해당 Map의 모든 내용 삭제 
testMap.clear();

System.out.println("clear 후 testMap : " + testMap);
```
<br /><br /><br />

3. Scope 사용

```java
private boolean {테이블_조회_메서드}(Map<String, Object> params) {

  LocalTime newStartTime;
  LocalTime newEndTime;

  List<{entity_name}> resultTable;
  {
    // Date 포맷
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");

    // 입력 시간을 Map에서 가져온다.
    String startTime = params.get("startTime");
    String endTime = params.get("endTime");

    // 데이터베이스에서 사용할 키
    Long groupKey = Long.parseLong(params.get("groupKey"));
    Long roomKey = Long.parseLong(params.get("roomKey"));

    // 날짜 계산이 가능한 형태로 형 변환
    newStartTime = LocalTime.parse(startTime, formatter);
    newEndTime = LocalTime.parse(endTime, formatter);


    // 특정 조건을 사용해서 테이블을 조회
    resultTable = reservationRepository.findByGroupKeyAndRoomKey(groupKey, roomKey);
  }

// ... newStartTime, newEndTime을 사용 추가 로직 구현
```

<br />

```
변수가 메서드 전체에서 유효할 경우 해당 메모리 공간은 메서드가 실행되는 동안 유지된다.
반면 중괄호로 범위를 제한하면 해당 변수는 더 이상 필요하지 않을 때 메모리에서 해제될 수 있다.

하지만 이러한 메모리 관리의 영향은 일반적으로 미미하기 때문에,
변수의 범위를 좁게 제한하여 코드의 가독성을 높이고,
변수의 사용 범위를 명확히 유지 보수성 차원에서 Scope를 적용해볼 수 있다.
```
