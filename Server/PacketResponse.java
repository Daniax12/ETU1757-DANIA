package allpacket;

import java.io.Serializable;

import bd.Table;

public class PacketResponse implements Serializable{
    private String responseType;
    private Table table;

    
    public String getResponseType() {
        return responseType;
    }
    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }
    public Table getTable() {
        return table;
    }
    public void setTable(Table table) {
        this.table = table;
    }

    
}