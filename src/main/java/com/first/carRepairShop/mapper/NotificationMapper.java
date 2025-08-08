package com.first.carRepairShop.mapper;

import com.first.carRepairShop.dto.NotificationDto;
import com.first.carRepairShop.dto.NotificationDtoFull;
import com.first.carRepairShop.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    NotificationDto toNotificationDto(Notification notification);

    Notification toNotificationEntity(NotificationDto notificationDto);

    NotificationDtoFull toNotificationDtoFull(Notification notification);
    Notification toNotificationEntityFromFull(NotificationDtoFull notificationDtoFull);
    List<NotificationDto> toNotificationDtoList(List<Notification> notificationList);
    List<Notification> toNotificationListFromDto(List<NotificationDto> notificationDtoList);
    List<NotificationDtoFull> toNotificationDtoFullList(List<Notification> notificationList);
    List<Notification> toNotificationListFromFull(List<NotificationDtoFull> notificationDtoFullList);


}
