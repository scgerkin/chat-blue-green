package com.scgerkin.jdnd.chatroom.chat.websocket;

import com.alibaba.fastjson.JSON;
import com.scgerkin.jdnd.chatroom.chat.Message;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

/**
 * WebSocket Server.
 *
 * @see ServerEndpoint WebSocket Client
 * @see Session   WebSocket Session
 */

@Component
@ServerEndpoint("/chat")
public class WebSocketChatServer {

  // for storing our current online sessions
  private static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();
  // for storing the user name associated with a session
  private static Map<Session, String> sessionUsername = new ConcurrentHashMap<>();

  /**
   * Takes a JSON string payload and sends it to all active sessions.
   *
   * @param msg A JSON string of a Message object.
   */
  private static void sendMessageToAll(String msg) {
    for (Session session : onlineSessions.values()) {
      try {
        session.getBasicRemote().sendText(msg);
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * Overload of above function. Converts a Message object to a JSON string.
   *
   * @param msg The Message we want to send to all current active sessions.
   */
  private static void sendMessageToAll(Message msg) {
    sendMessageToAll(JSON.toJSONString(msg));
  }

  /**
   * Adds a session to our map of current online sessions and updates the GUI with our new online
   * count.
   *
   * @param session The session to add.
   */
  @OnOpen
  public void onOpen(Session session) {
    onlineSessions.put(session.getId(), session);
    sendMessageToAll(new Message(Message.Type.CONNECT, null, null, onlineSessions.size()));
  }

  /**
   * Receives a JSON string payload from the GUI to send out to the online sessions. If the payload
   * type is ENTER, we will add the new user name to our map of user names.
   *
   * @param session The session sending a message.
   * @param jsonStr The JSON string payload we want to send to our sessions.
   */
  @OnMessage
  public void onMessage(Session session, String jsonStr) {
    Message msg = JSON.parseObject(jsonStr, Message.class);
    if (msg.getType().equals(Message.Type.ENTER)) {
      sessionUsername.put(session, msg.getUsername());
    }
    msg.setNumOnline(onlineSessions.size());
    sendMessageToAll(msg);
  }

  /**
   * Called when a session is terminated. Removes the session from the map of online sessions and
   * alerts the room that a user has left.
   *
   * @param session The session to remove.
   */
  @OnClose
  public void onClose(Session session) {
    onlineSessions.remove(session.getId());
    sendMessageToAll(
        new Message(Message.Type.LEAVE, sessionUsername.get(session), null, onlineSessions.size()));
    sessionUsername.remove(session);
  }

  /**
   * Prints any exceptions to the console.
   *
   * @param session The originating session of an exception.
   * @param error   The Throwable created.
   */
  @OnError
  public void onError(Session session, Throwable error) {
    error.printStackTrace();
  }
}