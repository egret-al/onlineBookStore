package com.onlinebookstore.entity.orderserver;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/29 10:26
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SeriesItem implements Serializable {
    private static final long serialVersionUID = 1060395470395142349L;

    private List<Integer> data;
    private String name;
    private String type;
}
