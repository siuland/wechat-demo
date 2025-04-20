package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import org.example.common.CommonResult;
import org.example.dao.WeatherMapper;
import org.example.model.WeatherDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/weather")
public class WeatherController {

    @Autowired
    private WeatherMapper weatherMapper;

    @GetMapping
    public CommonResult<List<WeatherDO>> getWeatherByCity(String city) throws Exception {
        LambdaQueryWrapper<WeatherDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(WeatherDO::getCity, city);
        List<WeatherDO> weatherDOS = weatherMapper.selectList(lambdaQueryWrapper);
        return CommonResult.success(weatherDOS);
    }

}
