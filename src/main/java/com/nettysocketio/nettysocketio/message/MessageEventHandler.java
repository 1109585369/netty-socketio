package com.nettysocketio.nettysocketio.message;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.nettysocketio.nettysocketio.bean.ClientInfo;
import com.nettysocketio.nettysocketio.repository.ClientInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.Cleaner;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.UUID;

/**
 * @author yy
 * @ProjectName netty-socketio
 * @Description: TODO
 * @date 2018/10/27 10:15
 */
@Component
public class MessageEventHandler {

    public MessageEventHandler(SocketIOServer server) {
        this.server = server;
    }

    private final SocketIOServer server;

    @Autowired
    private ClientInfoRepository clientInfoRepository;

    //添加connect事件，当客户端发起连接时调用，本文中将clientid与sessionid存入数据库

    @OnConnect
    public void onConnect(SocketIOClient client){
        System.out.println("===============进入了onconnect方法==============");
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        ClientInfo clientInfo = clientInfoRepository.findClientInfoByClientId(clientId);
        if(clientInfo != null){
                clientInfo.setConnected((short) 1);
                clientInfo.setMostsignbits(client.getSessionId().getMostSignificantBits());
                clientInfo.setLastconnecteddate(new Date());
                clientInfo.setLeastsignbits(client.getSessionId().getLeastSignificantBits());
                clientInfoRepository.save(clientInfo);
        }

    }


    //添加@OnDisconnect事件，客户端断开连接时调用，刷新客户端信息
    @OnDisconnect
    public void onDisconnect(SocketIOClient client){
        System.out.println("===============进入了onDisconnect方法==============");
        String clientId = client.getHandshakeData().getSingleUrlParam("clientid");
        ClientInfo clientInfo = clientInfoRepository.findClientInfoByClientId(clientId);
        if(clientInfo != null){
            clientInfo.setConnected((short) 0);
            clientInfo.setMostsignbits(null);
            clientInfo.setLeastsignbits(null);
            clientInfoRepository.save(clientInfo);
        }

    }

    //消息接收入口，当接收到消息后，查找发送目标客户端，并且向该客户端发送消息，且给自己发送消息
    @OnEvent(value = "messageevent")
    public void onEvent(SocketIOClient client, AckRequest request,MessageInfo data){
        System.out.println("===============进入了onEvent方法==============");
            String targetClientId = data.getTargetClientId();
            ClientInfo clientInfo = clientInfoRepository.findClientInfoByClientId(targetClientId);
            if(clientInfo != null){
                UUID uuid = new UUID(clientInfo.getMostsignbits(),clientInfo.getLeastsignbits());
                System.out.println(uuid.toString());
                MessageInfo sendData = new MessageInfo();
                sendData.setSourceClientId(data.getSourceClientId());
                sendData.setTargetClientId(data.getTargetClientId());
                sendData.setMsgType("chat");
                sendData.setMsgContent(data.getMsgContent());
                client.sendEvent("messageevent",sendData);
                server.getClient(uuid).set("messageevent",sendData);
            }
    }




}
