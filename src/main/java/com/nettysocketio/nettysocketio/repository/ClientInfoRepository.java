package com.nettysocketio.nettysocketio.repository;

import com.nettysocketio.nettysocketio.bean.ClientInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * @author yy
 * @ProjectName netty-socketio
 * @Description: TODO
 * @date 2018/10/27 10:13
 */
public interface ClientInfoRepository extends CrudRepository<ClientInfo,String> {

    ClientInfo findClientInfoByClientId(String clientId);

}
