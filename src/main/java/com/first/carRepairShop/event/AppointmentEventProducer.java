package com.first.carRepairShop.event;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppointmentEventProducer {
    private final KafkaTemplate<String,AppointmentConfirmEvent> kafkaTemplate;

    public void publishEvent(AppointmentConfirmEvent appointmentConfirmEvent){

        String eventType = "APPOINTMENT_CONFIRMED"; // hardcoded for this event type
        String key = eventType + "-" + appointmentConfirmEvent.getCustomerId();

        kafkaTemplate.send("appointment-confirmed-topic",key,appointmentConfirmEvent);
    }

}
