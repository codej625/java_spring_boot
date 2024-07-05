# 예약 시 중복을 체크하는 함수(예시)

<br /><br />

1. 전제 조건
```
* 전제 상황

Insert 시 예약 시간의 중복이 생길 수 있는 상황
```
```
* Insert Params

String startTime = 1000;
String endTime = 1200;
(예 10:00 형식이면 가공해서 형 변환하여 사용)
```

<br /><br />

2. 소스 확인
```java
private boolean resultTable(Map<String, String> params) {

    LocalTime newStartTime;
    LocalTime newEndTime;

    List<{entity_name}> resultTable;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");

    {
        // 입력 시간을 Map에서 가져온다.
        String startTime = params.get("startTime");
        String endTime = params.get("endTime");

        // 날짜 계산이 가능한 형태로 형 변환
        newStartTime = LocalTime.parse(startTime, formatter);
        newEndTime = LocalTime.parse(endTime, formatter);

        Long groupKey = Long.parseLong(params.get("groupKey"));
        Long roomKey = Long.parseLong(params.get("roomKey"));

        // 특정 조건을 사용해서 테이블을 조회한다.
        resultTable = reservationRepository.findByGroupKeyAndRoomKey(groupKey, roomKey);
    }

    if (!resultTable.isEmpty()) {

        for ({entity_name} result : resultTable) {

            LocalTime existingStartTime = LocalTime.parse(result.getStartTime(), formatter);
            LocalTime existingEndTime = LocalTime.parse(result.getEndTime(), formatter);

            if (!(newEndTime.isBefore(existingStartTime) || newStartTime.isAfter(existingEndTime))) { return false; }
        }
    }

    return true;
}
```
```java
@Repository
public interface ReservationRepository extends JpaRepository<{entity_name}, Long> {
    public List<{entity_name}> findByGroupKeyAndRoomKey(Long groupKey, Long roomKey);
}
```
