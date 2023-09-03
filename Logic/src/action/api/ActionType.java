package action.api;

import java.io.Serializable;

public enum ActionType implements Serializable {
    INCREASE, DECREASE, CALCULATION, CONDITION, SET, KILL
}
