package com.business.pjbfddml;

import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ycx
 * Date: 13-8-26
 * Time: 下午3:12
 * To change this template use File | Settings | File Templates.
 */
public interface PjbfddnrDao {
    /**
     * 新增
     *
     * @param pjbfddnr
     */
    void add(Pjbfddnr pjbfddnr);

    /**
     * 修改
     *
     * @param pjbfddnr
     */
    void update(Pjbfddnr pjbfddnr);

    /**
     * 删除自身
     *
     * @param pjbfddnr
     */
    void delete(Pjbfddnr pjbfddnr);

    /**
     * 通过主表删除子表
     *
     * @param zyh
     */
    void deletePjbfddnrs(String zyh);

    /**
     * 通过主表查询子表
     *
     * @param zyh
     * @return
     */
    Set<Pjbfddnr> queryByZyh(String zyh);

    /**
     * 查询所有
     *
     * @param pzjgParam
     * @param linesPerPage
     * @param pagesPerQuery
     * @param needDispPage
     * @param totalLines
     * @param queryFlag
     * @param orderField
     * @param orderTrend
     * @return
     */
    Map queryList(PjbfddmlParam pzjgParam, int linesPerPage,
                  int pagesPerQuery, int needDispPage, int totalLines,
                  String queryFlag, String orderField, String orderTrend);
}
