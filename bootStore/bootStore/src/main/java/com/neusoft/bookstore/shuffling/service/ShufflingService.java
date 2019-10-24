package com.neusoft.bookstore.shuffling.service;

import com.neusoft.bookstore.base.Response;
import com.neusoft.bookstore.shuffling.model.Shuffling;

public interface ShufflingService {
    /**
     * 分页查询轮播列表
     * @param shuffling
     * @return
     */
    Response listShuffling(Shuffling shuffling);
    /**
     * 新增轮播
     * @param shuffling
     * @return
     */
    Response saveShuffling(Shuffling shuffling);
    /**
     * 删除轮播
     * @param shuffling
     * @return
     */
    Response deleteShufflingById(Shuffling shuffling);
    /**
     * 轮播启用禁用
     * @param shuffling
     * @return
     */
    Response updShuffling(Shuffling shuffling);
}
