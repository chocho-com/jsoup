package com.chocho.jsoup.service.impl;

import com.chocho.jsoup.model.Weather;
import com.chocho.jsoup.mapper.WeatherMapper;
import com.chocho.jsoup.service.WeatherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chocho
 * @since 2020-06-21
 */
@Service
public class WeatherServiceImpl extends ServiceImpl<WeatherMapper, Weather> implements WeatherService {

    @Autowired
    private WeatherMapper weatherMapper;

    @Override
    public List<Weather> getWeekList(Document document) {

        ArrayList<Weather> weathers = new ArrayList<>();
        // 数据提取
        Elements sevenBox = document.getElementById("7d").getElementsByClass("t");
        Elements liColumns = sevenBox.get(0).getElementsByTag("li");
        for (Element column : liColumns) {
            Weather weather = new Weather();
            // 获取 name
            String name = column.getElementsByTag("h1").text().split("（")[1].split("）")[0];
            String day = column.getElementsByTag("h1").text().split("\\D{1,2}")[0];
            String date = getDate(day);
            // 今日天气状态
            String status = column.getElementsByClass("wea").text();
            // 温度
            String tem = column.getElementsByClass("tem").text();
            String[] temArr = tem.split("\\D{1,2}");
            // 最高气温
            String maxTem;
            // 最低气温
            String minTem;
            if (temArr.length == 2) {
                maxTem = tem.split("\\D{1,2}")[0];
                minTem = tem.split("\\D{1,2}")[1];
            } else {
                maxTem = tem.split("\\D{1,2}")[0];
                minTem = tem.split("\\D{1,2}")[0];
            }
            weather.setName(name);
            weather.setStatus(status);
            weather.setDate(date);
            weather.setMax(maxTem);
            weather.setMin(minTem);
            weathers.add(weather);
        }

        // 插入数据库
        for (Weather weather : weathers) {
            weatherMapper.insert(weather);
        }

        return weathers;
    }

    // 获取对应日期
    public String getDate(String day) {
        Date date = new Date();
        //格式日期
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date.setDate(Integer.parseInt(day));
        return dateFormat.format(date);
    }
}
