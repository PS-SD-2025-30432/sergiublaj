package en.sd.chefmgmt.controller;

import java.time.ZonedDateTime;

import en.sd.chefmgmt.model.dto.chef.ChefRequestDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ControllerTestData {

    public ChefRequestDTO validChefRequestDto() {
        return ChefRequestDTO.builder()
                .name("New Chef")
                .cnp("9876543210987")
                .birthDate(ZonedDateTime.parse("1990-02-02T12:00:00+03:00[Europe/Bucharest]"))
                .rating(4.9)
                .build();
    }

    public ChefRequestDTO invalidChefRequestDto() {
        return ChefRequestDTO.builder()
                .name("")
                .cnp("short")
                .birthDate(ZonedDateTime.now())
                .rating(6.0)
                .build();
    }
}
