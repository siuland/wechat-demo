package org.example;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import org.apache.ibatis.annotations.Mapper;
import org.example.dao.WeatherMapper;
import org.example.model.WeatherDO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class SampleTest {

    @Autowired
    private WeatherMapper weatherMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        LambdaQueryWrapper<WeatherDO> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(WeatherDO::getCity,"杭州");
        List<WeatherDO> userList = weatherMapper.selectList(lambdaQueryWrapper);
        Assert.isTrue(3 == userList.size(), "");
        userList.forEach(System.out::println);
    }

}