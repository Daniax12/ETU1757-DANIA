package allpacket;

import java.io.Serializable;

public class PacketRequest implements Serializable{
    private String typeAction;
    private String actionContent;

    public PacketRequest(String typeAction, String actionContent) {
        this.setTypeAction(typeAction);
        this.setActionContent(actionContent);
    }

    
    public String getTypeAction() {
        return typeAction;
    }
    public void setTypeAction(String typeAction) {
        this.typeAction = typeAction;
    }
    public String getActionContent() {
        return actionContent;
    }
    public void setActionContent(String actionContent) {
        this.actionContent = actionContent;
    }

    
}