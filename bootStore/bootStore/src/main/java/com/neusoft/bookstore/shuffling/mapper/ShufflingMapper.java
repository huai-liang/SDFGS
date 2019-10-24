package com.neusoft.bookstore.shuffling.mapper;

import com.neusoft.bookstore.shuffling.model.Shuffling;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ShufflingMapper {
    /**
     * 分页查询轮播列表
     * @param shuffling
     * @return
     */
    List<Shuffling> listShuffling(Shuffling shuffling);
    /**
     * 新增轮播
     * @param shuffling
     * @return
     */
    int saveShuffling(Shuffling shuffling);
    /**
     * 删除轮播
     * @param shuffling
     * @return
     */
    int deleteShufflingById(Shuffling shuffling);
    /**
     * 轮播启用禁用
     * @param shuffling
     * @return
     */
    int updShuffling(Shuffling shuffling);
}
