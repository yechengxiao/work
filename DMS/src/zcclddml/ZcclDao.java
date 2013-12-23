package com.business.zcclddml;

import java.util.List;
import java.util.Map;

public interface ZcclDao {
    /**
     * 新增
     *
     * @param zccl
     */
    void add(Zccl zccl);

    /**
     * 修改
     *
     * @param zccl
     */
    void update(Zccl zccl);

    /**
     * 通过mclh删除，mclh下多条zclh一并删除
     *
     * @param mclh
     */
    void deleteByMclh(String[] mclh);

    /**
     * 查询符合条件的数据列表，并实现翻页
     *
     * @param zcclParam
     * @param linesPerPage
     * @param pagesPerQuery
     * @param needDispPage
     * @param totalLines
     * @param queryFlag
     * @param orderField
     * @param orderTrend
     * @return
     */
    Map queryList(ZcclParam zcclParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, String queryFlag, String orderField, String orderTrend);

    /**
     * 通过mclh来查询zccl
     *
     * @param mclh
     * @return
     */
    List<Zccl> queryByMclh(String mclh);

}
