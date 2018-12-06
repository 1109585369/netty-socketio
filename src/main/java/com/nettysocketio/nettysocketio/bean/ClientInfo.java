package com.nettysocketio.nettysocketio.bean;

import com.sun.istack.internal.NotNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author yy
 * @ProjectName netty-socketio
 * @Description: TODO
 * @date 2018/10/27 10:06
 */

@Entity
@Table(name = "clientInfo")
public class ClientInfo {
        
    @Id
    @NotNull
    private String clientId;
    private Short connected;
    private Long mostsignbits;
    private Long leastsignbits;
    private Date lastconnecteddate;
    public String getClientid() {
        return clientId;
    }
    public void setClientid(String clientid) {
        this.clientId = clientid;
    }
    public Short getConnected() {
        return connected;
    }
    public void setConnected(Short connected) {
        this.connected = connected;
    }
    public Long getMostsignbits() {
        return mostsignbits;
    }
    public void setMostsignbits(Long mostsignbits) {
        this.mostsignbits = mostsignbits;
    }
    public Long getLeastsignbits() {
        return leastsignbits;
    }
    public void setLeastsignbits(Long leastsignbits) {
        this.leastsignbits = leastsignbits;
    }
    public Date getLastconnecteddate() {
        return lastconnecteddate;
    }
    public void setLastconnecteddate(Date lastconnecteddate) {
        this.lastconnecteddate = lastconnecteddate;
    }

}
