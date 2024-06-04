# 서버에서 지속적으로 Response를 받아보자.

<br /><br />

* 메세지 받기

<br />

1. WebSocketConfig.class
```java
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

  private MessageHandler messageHandler;

  WebSocketConfig(MessageHandler messageHandler) {
    this.messageHandler = messageHandler;
  }

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(messageHandler, "/websocket");
  }
}
```

<br />

2. MessageHandler.class
```java
@Component
public class MessageHandler extends TextWebSocketHandler {

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws IOException {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      int count = 1;

      @Override
      public void run() {
        try {
          if (session.isOpen()) {
            session.sendMessage(new TextMessage("Websocket " + count));
            System.out.println("count " + count);
            count++;
          } else {
            cancel();
            System.out.println("close");
          }
        } catch (IOException e) {
          System.out.println(e.getMessage());
        }
      }
    }, 0, 1000); /* 0초후 1초마다 실행 */
  }
}
```

<br />

3. WebsocketController.class
```java
@Controller
public class WebsocketController {

  @GetMapping(value = "/")
  public String sockeTest() throws Exception {
    return "websocket.html";
  }
}
```

<br />

4. websocket.html
```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8">
    <title>WebSocket Test</title>
  </head>
  <body>
    <div class="result"></div>
    <script>
      const socket = new WebSocket("ws://localhost:8080/websocket");

      try {
        socket.onmessage = function (event) {
          const receivedMessage = event.data;
          const message = document.querySelector('.result');
          message.innerText = receivedMessage;
        };
      } catch (err) {
        console.error("WebSocket close");
      }
    </script>
  </body>
</html>
```
