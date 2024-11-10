package wthfmv.bandwith.domain.websocket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import wthfmv.bandwith.domain.track.entity.Track;
import wthfmv.bandwith.domain.track.service.TrackService;
import wthfmv.bandwith.domain.websocket.message.WebsocketMessage;

import java.util.*;

@Component
@RequiredArgsConstructor
public class CustomWebsocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Set<WebSocketSession>> sessionStore = new HashMap<>();


    private final TrackService trackService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("Session ID: " + session.getId());
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String clientMessage = message.getPayload();
        System.out.println("Received message: " + clientMessage);

        // 메시지 변환
        WebsocketMessage websocketMessage = objectMapper.readValue(clientMessage, WebsocketMessage.class);

        // SessionStore 에 없으면 새로운 Map 원소 생성
        if (!sessionStore.containsKey(websocketMessage.getTrackId())) {
            sessionStore.put(websocketMessage.getTrackId(), new HashSet<>());
        }

        // 현재 Map 원소에 session 추가
        sessionStore.get(websocketMessage.getTrackId()).add(session);

        // 세션 가져오기
        Set<WebSocketSession> sessionSet = sessionStore.get(websocketMessage.getTrackId());

        // 메시지 보내기
        for (WebSocketSession s : sessionSet) {
            if (s.isOpen()) { // 열려있으면
                trackService.updateAndGetTrack(websocketMessage);
                s.sendMessage(new TextMessage(websocketMessage.getTrackId()));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        // Remove the session from each track ID set when the connection is closed
        sessionStore.forEach((trackId, sessionSet) -> {
            sessionSet.remove(session);
        });

        // Remove track IDs that have no more sessions
        sessionStore.entrySet().removeIf(entry -> entry.getValue().isEmpty());

        System.out.println("Session closed: " + session.getId());
        super.afterConnectionClosed(session, status);
    }
}