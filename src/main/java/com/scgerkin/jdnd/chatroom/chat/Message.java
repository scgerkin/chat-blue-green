package com.scgerkin.jdnd.chatroom.chat;

import com.alibaba.fastjson.JSON;

/**
 * WebSocket message model (payload wrapper class).
 */
public class Message {

  private Type type;
  private String username;
  private String msg;
  private Integer numOnline;

  public Message() {
  }

  public Message(String username) {
    this.username = username;
  }

  public Message(String username, String msg) {
    this.username = username;
    this.msg = msg;
  }

  /**
   * Full-arg constructor.
   */
  public Message(Type type, String username, String msg, Integer numOnline) {
    this.type = type;
    this.username = username;
    this.msg = msg;
    this.numOnline = numOnline;
  }

  /**
   * Returns the a message as a JSON formatted string.
   */
  public static String json(Type type, String sender, String content, Integer numOnline) {
    return JSON.toJSONString(new Message(type, sender, content, numOnline));
  }

  public String json() {
    return JSON.toJSONString(this);
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  public Integer getNumOnline() {
    return numOnline;
  }

  public void setNumOnline(Integer numOnline) {
    this.numOnline = numOnline;
  }

  @Override
  public String toString() {
    return "type=" + type.name()
        + " sender=" + username
        + " content=" + msg
        + " numOnline=" + numOnline;
  }

  public enum Type {
    CONNECT, ENTER, SPEAK, LEAVE, DISCONNECT
  }
}
