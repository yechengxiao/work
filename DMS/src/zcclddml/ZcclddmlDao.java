package com.business.zcclddml;

import java.util.Map;

public interface ZcclddmlDao {
    /**
     * 新增
     *
     * @param ddml
     */
    void add(Zcclddml ddml);

    /**
     * 修改
     *
     * @param ddml
     */
    void update(Zcclddml ddml);

    /**
     * 根据主表删除子表,可以批量删除
     *
     * @param zyh
     */
    void deleteByZyh(String[] zyh);

    /**
     * 查询，一次一条
     *
     * @param zyh
     * @return
     */
    Zcclddml queryByZyh(String zyh);

    /**
     * 批量查询，实现翻页
     *
     * @param queryParam
     * @param linesPerPage
     * @param pagesPerQuery
     * @param needDispPage
     * @param totalLines
     * @param queryFlag
     * @param orderField
     * @param orderTrend
     * @return
     */
    Map queryList(ZcclddmlParam queryParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, String queryFlag, String orderField, String orderTrend);
}
