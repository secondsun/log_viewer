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

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author summers
 */
@Stateless
@ServerEndpoint("/read")
public class LogReader {

    private static final Set<Session> SESSIONS
            = Collections.synchronizedSet(new HashSet<Session>());

    @OnOpen
    public void createSession(Session session) {
        SESSIONS.add(session);
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        SESSIONS.remove(session);
    }

    public static void onJMSMessage(@Observes @WSJMSMessage Message msg) {
        try {
            for (Session s : SESSIONS) {
                s.getBasicRemote().sendText("message from JMS: " + msg.getBody(LogEvent.class).getMessage());
            }
        } catch (IOException | JMSException ex) {
            Logger.getLogger(LogReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
