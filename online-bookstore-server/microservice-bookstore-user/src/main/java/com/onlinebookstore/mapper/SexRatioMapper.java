package com.onlinebookstore.mapper;

import com.onlinebookstore.entity.SexRatio;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rkc
 * @version 1.0
 * @date 2020/11/30 10:28
 */
@Mapper
public interface SexRatioMapper {

    /**
     * 得到男女性别比例
     * @return List<SexRatio>
     */
    List<SexRatio> getSexRatio();
}
