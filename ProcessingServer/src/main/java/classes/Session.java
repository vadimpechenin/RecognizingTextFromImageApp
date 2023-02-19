package classes;

import dbclasses.ObjectWithID;

import java.time.Instant;

public class Session extends ObjectWithID {
    public String currentUserID;
    public Instant lastActivityTime;
}
