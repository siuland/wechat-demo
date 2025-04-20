package org.example.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("china_cities_weather_20250414_095826")
public class WeatherDO {
    private Integer id;

    private String city;

    private String date;

    private String temp;

    @TableField("high_temp")
    private String highTemp;

    @TableField("low_temp")
    private String lowTemp;


}
