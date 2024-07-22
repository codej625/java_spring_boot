# Map clear에 대해

<br /><br />

1. 맵 클리어

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

<br />

```
맵 전체를 삭제 하려면?
```

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
