package com.DataSaver.demo.Dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExpenceResDto {
    String name;
    int Travel;
    int Food;
}
