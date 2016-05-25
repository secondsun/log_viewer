/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.saga.log_viewer;

/*-
 * #%L
 * log_viewer
 * $Id:$
 * $HeadURL:$
 * %%
 * Copyright (C) 2016 Summers Pittman
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 2.1 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-2.1.html>.
 * #L%
 */

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author summers
 */
@ServerEndpoint("/logging")
public class LogReceiver {

    private final LogEventSenderSessionBean senderBean;

    @Inject
    public LogReceiver(LogEventSenderSessionBean sb) {
        this.senderBean = sb;
    }

    @OnMessage
    public String hello(String message) {
        System.out.println("Received : " + message);

        senderBean.sendMessage(new LogEvent(LogEvent.Event.MESSAGE, message, ""));

        return null;

    }

    @OnOpen
    public void myOnOpen(Session session) {
        System.out.println("WebSocket opened: " + session.getId() + "@" + this);
    }

    @OnClose
    public void myOnClose(CloseReason reason, Session session) {
        System.out.println("Closing a WebSocket due to " + reason.getReasonPhrase());
    }

    @OnError
    public void onError(Session session, Throwable thr) {
        Logger.getLogger(LogReceiver.class.getName()).log(Level.SEVERE, null, thr);
    }
}
