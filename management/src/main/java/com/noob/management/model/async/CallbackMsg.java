package com.noob.management.model.async;

/**
 * CallbackMsg
 *
 * @author Gao Shen
 * @version 16/4/22
 */
public class CallbackMsg extends BaseMsg {

    public CallbackMsg(String clientId) {
        super(clientId);
        this.setType(MsgType.CALLBACK);
    }
}
