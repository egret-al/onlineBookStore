package com.onlinebookstore.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/30 10:27
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SexRatio {
    private String sex;
    private Integer count;
}
