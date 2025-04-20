package org.example.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
public class WeatherInfo {
    private Integer id;

    private String city;

    private String date;

    private String temp;

    private String high_temp;

    private String low_temp;


}
