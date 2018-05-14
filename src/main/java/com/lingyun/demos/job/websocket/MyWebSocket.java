package com.lingyun.demos.job.websocket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/websocket",configurator = HttpSessionConfigurator.class)
@Component
public class MyWebSocket {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());



    private static ConcurrentHashMap<String,Session> webSocketHashMap = new ConcurrentHashMap<>();


    private Session session; //websocket session

    private HttpSession httpSession; //http session


    @OnOpen
    public void onOpen(Session session,EndpointConfig config) throws IOException {


        HttpSession httpSession= (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        System.out.println(httpSession.getId()+"httpid");
        this.httpSession = httpSession;
        this.session = session;

        webSocketHashMap.put(this.httpSession.getId(),this.session);

        logger.info("新连接…webSocketHashMap…当前在线计数: {}", webSocketHashMap.size());

//        MessageReturn webActivity = activityService.webActivity();
//
//        int state = webActivity.getActivity().getState();
//
//        webActivity.getActivity().setState(state);
//        onMessage(gson.toJson(webActivity));

    }

    @OnClose
    public void onClose() throws IOException{


        webSocketHashMap.remove(this.httpSession.getId());

        logger.info("一个连接关闭…webSocketHashMap…当前在线计数: {}", webSocketHashMap.size());

    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        logger.info("接收到的消息: {}", message);

        //发送消息
        for(String httpSessionId : webSocketHashMap.keySet()){
            Session session = webSocketHashMap.get(httpSessionId);
            sessionSendMessage(session,message);
        }

    }

    //发送消息
    public void sessionSendMessage(Session session,String message) throws IOException {
        if(session.isOpen()){
            session.getBasicRemote().sendText(message);
        }
    }


}