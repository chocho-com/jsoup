package com.chocho.jsoup.service;

import com.chocho.jsoup.model.Weather;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author chocho
 * @since 2020-06-21
 */
public interface WeatherService extends IService<Weather> {
    // HTML解析获取七日天气预报数据
    List<Weather> getWeekList(Document document);
}
