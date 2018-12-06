package com.nettysocketio.nettysocketio.message;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author yy
 * @ProjectName netty-socketio
 * @Description: TODO
 * @date 2018/10/27 10:43
 */
@Component
public class ServerRunner implements CommandLineRunner {

    public ServerRunner(SocketIOServer server) {
        this.server = server;
    }

    private final SocketIOServer server;

    @Override
    public void run(String... args) throws Exception {
        server.start();
    }
}
