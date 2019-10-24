package com.neusoft.bookstore.file.mapper;

import com.neusoft.bookstore.file.modle.File;
import com.neusoft.bookstore.goods.model.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FileMapper {
    /**
     * 保存文件
     * @param goods
     */
    void saveFile(Goods goods);
    /**
     * 查询文件信息
     * @param file
     * @return
     */
    List<File> listFile(File file);
    /**
     * 根据文件id删除文件
     * @param file
     */
    void deleteFileById(File file);
    /**
     * 修改文件
     * @param goods
     */
    void updateFile(Goods goods);
    /**
     * 删除当前商品下的图片
     * @param goods
     */
    void deleteFileByGoods(Goods goods);
}
