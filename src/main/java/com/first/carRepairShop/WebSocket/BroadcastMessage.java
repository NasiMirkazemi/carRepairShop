package com.first.carRepairShop.WebSocket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BroadcastMessage  {

    private String message;

    @Override
    public String toString() {
        return "BroadcastMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}
