package com.business.pjbfddml;

import java.util.Map;

/**
 * User: ycx
 * Date: 13-8-26
 * Time: 下午2:18
 */
public interface PjbfddmlDao {
    /**
     * @param pjbfddml
     */
    void add(Pjbfddml pjbfddml);

    /**
     * @param pjbfddml
     */
    void update(Pjbfddml pjbfddml);

    /**
     * @param rowFlag
     */
    void delete(String rowFlag);

    /**
     * @param zyh
     * @return
     */
    Pjbfddml queryById(String zyh);

    /**
     * 查询所有。备件报废管理页面调用的查询方法。
     *
     * @param pjbfddmlParam 备件报废信息 ，linesPerPage 每页显示的条数，pagesPerQuery
     *                      每次查询缓冲页数，needDispPage 指定将要显示哪一页，totalLinesNum
     *                      总的结果集记录数，queryFlag 查询标志，beginNum 查询起始位置, qryNum 查询记录数
     * @return 多个配件，以列表形式返回
     */
    Map queryList(PjbfddmlParam pjbfddmlParam, int linesPerPage,
                  int pagesPerQuery, int needDispPage, int totalLines,
                  String queryFlag, String orderField, String orderTrend);

    /**
     * 查询所有符合条件的BjkcBzk
     *
     * @param bjkcBzkParam
     * @param linesPerPage
     * @param pagesPerQuery
     * @param needDispPage
     * @param totalLines
     * @param queryFlag
     * @return
     */
    Map queryBjkcBzk(BjkcBzkParam bjkcBzkParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, String queryFlag);
}
