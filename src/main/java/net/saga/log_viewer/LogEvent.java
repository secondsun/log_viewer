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

import java.io.Serializable;
import java.util.Objects;

public class LogEvent implements Serializable {

    public enum Event {
        OPEN("O"), MESSAGE("M"), CLOSE("C");
        public final String key;

        private Event(String key) {
            this.key = key;
        }

        public static Event fromKey(String key) {
            switch (key) {
                case "O"://OPEN.key:
                    return OPEN;
                case "M"://MESSAGE.key:
                    return MESSAGE;
                case "C"://CLOSE.key:
                    return CLOSE;
                default:
                    throw new IllegalArgumentException("No such key " + key);
            }
        }

    };

    private final Event event;
    private final String message;
    private final String key;

    public LogEvent(Event event, String message, String key) {
        this.event = event;
        this.message = message;
        this.key = key;
    }

    public Event getEvent() {
        return event;
    }

    public String getMessage() {
        return message;
    }

    public String getKey() {
        return key;
    }

    
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.event);
        hash = 17 * hash + Objects.hashCode(this.message);
        hash = 17 * hash + Objects.hashCode(this.key);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LogEvent other = (LogEvent) obj;
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        if (this.event != other.event) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "LogEvent{" + "event=" + event + ", message=" + message + ", key=" + key + '}';
    }

}
