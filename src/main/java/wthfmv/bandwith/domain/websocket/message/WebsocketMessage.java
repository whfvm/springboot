package wthfmv.bandwith.domain.websocket.message;

import lombok.Data;

@Data
public class WebsocketMessage {
    private String trackId; //mongo 에 저장된 트랙의 id
    private String[] query; // mongo 에 적용할 쿼리
    private Object[] value;
    private WebSocketMessageMethod[] webSocketMessageMethod;
    private String uuid;
}
