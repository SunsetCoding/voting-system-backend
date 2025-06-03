package com.voting.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.voting.model.Poll;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * description
 *
 * @author: zhic.tan
 * @date 2025/5/29 上午12:46
 */

@Component
@Slf4j
public class PollWebSocketHandler implements WebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    public void broadcastUpdate(Poll poll) {
        String message;
        try {
            message = objectMapper.writeValueAsString(poll);
        } catch (Exception e) {
            return;
        }

        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (Exception e) {
                    log.warn("发送消息到会话失败: {}", session.getId(), e);
                    sessions.remove(session);
                }
            }
        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        sessions.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}