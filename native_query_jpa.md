# JPA에서 Native query를 사용해보자.

<br /><br />

```
JPA를 사용하다 보면 복잡한 쿼리 혹은 조인이 필요할 때가 있다.
이럴 때 Native query를 사용해서 문제를 해결할 수 있다.
```

<br /><br /><br />

1. Controller

```java
@ResponseBody
@GetMapping(value = "/admin-reservation-list")
public ResponseEntity<List<Map<String, Object>>> adminReservationList() {

  List<Map<String, Object>> result = adminService.adminReservationList();

  return ResponseEntity.ok(result);
}
```

<br /><br /><br />

2. Method

```
Entity는 사용하지 않는 예시이다.
```

<br />

```java
public List<Map<String, Object>> adminReservation() {

  List<Map<String, Object>> result = null;
  
  try {
    // 조회 조건에 사용할 파라미터 만들기
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    String resvDate = LocalDate.now().format(formatter);

    // DB Access
    List<Object[]> resultTable = reservationRepository.getReserveInfo(resvDate, "1");
    
    // 조회 값이 비어 있지 않으면
    if (!resultTable.isEmpty()) {
      result = resultTable.stream().map(entity -> {

        Map<String, Object> map = new HashMap<>();
        map.put("recKey", entity[1]);
        map.put("groupKey", entity[2]);
        map.put("roomKey", entity[3]);
        map.put("startTime", entity[4]);
        map.put("endTime", entity[5]);
        map.put("status", entity[6]);
        map.put("createUser", entity[7]);
        map.put("userName", entity[9]);
        map.put("roomName", entity[10]);

        return map;
      }).toList();
    }
  } catch (Exception e) { return result; }
  
  return result;
}
```

<br /><br /><br />

3. Repository

```
서브 쿼리를 사용해 JOIN을 하는 쿼리 예시이다.
파라미터는 : 를 사용해서 @Param 의 값을 사용할 수 있다.
```

<br />

```java
@Repository
public interface ReservationRepository extends JpaRepository<RoomReserveInfoTable, Long> {

 // ... 로직

  @Query(value =
    "SELECT " +
    "    A.create_date " +
    "    , A.rec_key " +
    "    , A.group_key " +
    "    , A.room_key " +
    "    , A.start_time " +
    "    , A.end_time " +
    "    , A.status " +
    "    , A.create_user " +
    "    , A.user_id " +
    "    , A.user_name " +
    "    , B.room_name " +
    "FROM ( " +
    "    SELECT " +
    "        a.create_date " +
    "        , a.rec_key " +
    "        , a.group_key " +
    "        , a.room_key " +
    "        , a.start_time " +
    "        , a.end_time " +
    "        , a.status " +
    "        , a.create_user " +
    "        , b.user_id " +
    "        , b.user_name " +
    "    FROM rm_reserve_info_tbl a " +
    "    LEFT JOIN lib_user_tbl b ON a.resv_user_id = b.user_id " +
    "    WHERE a.resv_date = :resvDate AND a.status = :status " +
    ") A " +
    "LEFT JOIN rm_info_tbl B ON A.room_key = B.room_key " +
    "ORDER BY A.create_date DESC", nativeQuery = true)
  List<Object[]> getReserveInfo(@Param("resvDate") String resvDate, @Param("status") String status);
}
```
