package com.chocho.jsoup.controller;


import com.chocho.jsoup.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chocho
 * @since 2020-06-21
 */
@RestController
@RequestMapping("/jsoup/weather")
@Slf4j
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    // 获取七日天气数据
    @GetMapping("/weather/get")
    public void getDataFromHtml() {
        String url = "http://www.weather.com.cn/weather/101020100.shtml";
        try {
            Document document = Jsoup.connect(url).get();
            weatherService.getWeekList(document);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}

