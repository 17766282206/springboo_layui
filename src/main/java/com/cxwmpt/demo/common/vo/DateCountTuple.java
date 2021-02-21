package com.cxwmpt.demo.common.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 2019-3-21  17:13 Create by XXX
 * DateCountTuple
 *
 * @author XXX
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateCountTuple {
    /**
     * 日期
     */
    private Integer time;
    /**
     * 数量
     */
    private Integer count;

}
