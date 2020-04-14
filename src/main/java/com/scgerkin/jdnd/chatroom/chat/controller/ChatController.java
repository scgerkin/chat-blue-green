package com.scgerkin.jdnd.chatroom.chat.controller;

import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ChatController {

  @Value("${websocket.endpoint}")
  private String webSocketUrl;

  public void setWebSocketUrl(String webSocketUrl) {
    this.webSocketUrl = webSocketUrl;
  }

  /**
   * Login Page.
   */
  @GetMapping("/")
  public ModelAndView login() {
    return new ModelAndView("login");
  }

  /**
   * Chatroom Page.
   */
  @GetMapping("/index")
  public ModelAndView index(String username, HttpServletRequest request)
      throws UnknownHostException {
    // exit early if we do not have a username and return to the login page
    if (username == null || username.isEmpty() || request.getParameterValues("username") == null) {
      return new ModelAndView("login");
    }
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("chat");
    modelAndView.addObject("username", username);
    System.out.println("New user connection to chat.");
    modelAndView.addObject("webSocketUrl", webSocketUrl);
    return modelAndView;
  }
}
