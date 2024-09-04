# Domain Model Pattern vs Transaction Script Pattern

<br /><br />

```
비즈니스 로직을 어디서 처리하냐는
굉장히 중요한 문제이다.

정답은 없으며, 상황에 맞는 선택이 필요하다.
```

<br /><br /><br />

* Domain Model Pattern
---

```
대부분의 비즈니스 로직을 엔티티 안에 구성한다.

서비스 계층은 엔티티에 필요한 역할을 위임하는 역할을 합니다. (트랜잭션의 관리)

"엔티티" 안에 비즈니스 로직을 가지고 객체지향을 활용하는 기법이다.
(DDD를 접목시킬 경우 이 방법을 사용)
```

<br />

```java
// 예시

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {
    @Id
    @GeneratedValue @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "member_id")
    private Member member; //주문 회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY) @JoinColumn(name = "delivery_id") //1대1 관계에서 order에서 조회가 더많이 일어나니 Order 테이블에서 delivery_id를 여기서 연관관계 주인이된다.
    private Delivery delivery; //배송정보

    private LocalDateTime orderDate; //주문시간 @Enumerated(EnumType.STRING) Date 말고 LocalDateTime 쓰자

    @Enumerated(EnumType.STRING) //Enum순서의 보장 ORDINAL 사용 x
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==생성 메서드==//
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem orderItem: orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order; //로직이 변경될 경우 여기만 고치면 된다.
    }

    //==비즈니스 로직==//
    /**
     * 주문 취소
     */
    public void cancel(){
        // 배송완료이면 취소 x
        if(delivery.getStatus() == DeliveryStatus.COMP){
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem orderItem: orderItems){
            orderItem.cencel();
        }
    }

    //==조회 로직==//
    /**
     * 전체 주문 가격 조회
     */
    public int getTotalPrice(){
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }
}
```

<br />

```java
// 예시2

@Transactional
public Order cancelOrder(int orderId) {
	
    Orders order = ordersRepository.findById(orderId);
    Billing billing = billingRepository.findByOrderId(orderId);
    Delivery delivery = deliveryRepository.findByOrderId(orderId);
    
    delivery.cancel();
    
    order.cancel();
    billing.cancel();
    
    return order;
}
```

<br />

```
엔티티 안에 비즈니스 로직을 처리하는 것을 볼 수 있다.

트랜잭션 스크립트 모델로 구현한다면,
엔티티는 DTO의 역할만 하고 위에 해당 비즈니스 로직이 서비스 계층으로 옮겨지게 된다.
```

<br /><br /><br />

* Transaction Script Pattern
---

```
서비스 레이어에서 비즈니스 로직을 처리한다.
결합도가 높아지고 응집도는 낮아진다.
```

<br />

```java
// 예시

@Transactional
public Order cancelOrder(int orderId) {
	
    OrderDto order = orderDao.selectOrders(orderId);
    BiliingDto billing = billingDao.selectBilling(orderId);
    DeliveryDto delivery = deliveryDao.selectDelivery(orderId);
    
    String deliveryStatus = delivery.getStatus();
    
    if("IN_PROGRESS".equals(deliveryStatus)) {
    	delivery.setStatus("CANCEL");
        deliveryDao.update(delivery);
    }
    
    order.setStatus("CANCEL");
    orderDao.update(order);
    
    billing.setStatus("CANCEL");
    deliveryDao.update(billing);
    
    return order;
}
```

<br />

```
모든 로직이 서비스 클래스 내부에서 처리된다. 
(서비스 계층이 무의미함, 레이어 아키텍처의 이점을 챙길 수 없다.)
```
