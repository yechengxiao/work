package com.pub.pzjg;

import com.pub.util.TranFailException;

import java.util.List;
import java.util.Map;

public interface PzjgService {

    /**
     * 新增
     *
     * @param pzjg
     * @throws TranFailException
     */
    abstract void add(Pzjg pzjg) throws TranFailException;

    /**
     * 修改
     *
     * @param pzjg
     * @throws TranFailException
     */
    abstract void update(Pzjg pzjg) throws TranFailException;

    /**
     * 删除整车物料库信息
     *
     * @param rowFlag 多个整车物料库信息id拼接，用"|"分隔
     * @return
     */
    abstract void delete(String rowFlag) throws TranFailException;

    /**
     * 根据主键查询
     *
     * @param vsn
     * @return
     * @throws TranFailException
     */
    abstract Pzjg getById(String vsn) throws TranFailException;

    /**
     * @param queryParam
     * @param linesPerPage
     * @param pagesPerQuery
     * @param needDispPage
     * @param totalLines
     * @param queryFlag
     * @param orderField
     * @param orderTrend
     * @return
     * @throws TranFailException
     */
    abstract Map getList(PzjgParam queryParam, int linesPerPage,
                         int pagesPerQuery, int needDispPage, int totalLines,
                         String queryFlag, String orderField, String orderTrend)
            throws TranFailException;

    abstract Map<String, List<String>> getPzjgString(PzjgParam pzjgParam) throws TranFailException;

}
