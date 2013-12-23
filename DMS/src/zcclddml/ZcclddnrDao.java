package com.business.zcclddml;

import java.util.List;

public interface ZcclddnrDao {
    /**
     * 新增
     *
     * @param zcclddnr
     */
    void add(Zcclddnr zcclddnr);

    /**
     * 修改
     *
     * @param zcclddnr
     */
    void update(Zcclddnr zcclddnr);

    /**
     * 根据主表删除子表,可以批量删除
     *
     * @param zyh
     */
    void deleteByZyh(String[] zyh);

    /**
     * 根据主表查询子表，可以返回多条
     *
     * @param zyh
     * @return
     */
    List<Zcclddnr> queryByZyh(String zyh);
}
