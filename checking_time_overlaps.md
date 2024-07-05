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

    List<RoomReserveInfoTable> resultTable;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");

    {
        String startTime = params.get("startTime");
        String endTime = params.get("endTime");


        newStartTime = LocalTime.parse(startTime, formatter);
        newEndTime = LocalTime.parse(endTime, formatter);

        Long groupKey = Long.parseLong(params.get("groupKey"));
        Long roomKey = Long.parseLong(params.get("roomKey"));

        resultTable = reservationRepository.findByGroupKeyAndRoomKey(groupKey, roomKey);
    }

    if (!resultTable.isEmpty()) {

        for (RoomReserveInfoTable result : resultTable) {

            LocalTime existingStartTime = LocalTime.parse(result.getStartTime(), formatter);
            LocalTime existingEndTime = LocalTime.parse(result.getEndTime(), formatter);

            if (!(newEndTime.isBefore(existingStartTime) || newStartTime.isAfter(existingEndTime))) { return false; }
        }
    }

    return true;
}
```
