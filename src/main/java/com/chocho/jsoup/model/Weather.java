package com.chocho.jsoup.model;

import com.baomidou.mybatisplus.annotation.*;


import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author chocho
 * @since 2020-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Weather implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;

    private String status;

    private String date;

    //最高气温
    private String max;
    //最低气温
    private String min;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;


}
