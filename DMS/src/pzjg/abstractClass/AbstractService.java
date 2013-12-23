package com.pub.pzjg.abstractClass;

import com.pub.pzjg.Pzjg;
import com.pub.pzjg.PzjgParam;
import com.pub.util.TranFailException;

import java.util.Map;

/**
 * User: ycx
 * Date: 13-7-30
 * Time: 下午3:20
 * 说明：抽象类。通用方法定义，子类按需重写此类中的方法。
 */
public abstract class AbstractService {
    /**
     * 新增
     *
     * @param pzjg
     * @throws TranFailException
     */
    public abstract void add(Pzjg pzjg) throws TranFailException;

    /**
     * 修改
     *
     * @param pzjg
     * @throws TranFailException
     */
    public abstract void update(Pzjg pzjg) throws TranFailException;

    /**
     * 删除整车物料库信息
     *
     * @param rowFlag 多个整车物料库信息id拼接，用"|"分隔
     * @return
     */
    public abstract void delete(String rowFlag) throws TranFailException;

    /**
     * 根据主键查询
     *
     * @param vsn
     * @return
     * @throws TranFailException
     */
    public abstract Pzjg getById(String vsn) throws TranFailException;

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
    public abstract Map getList(PzjgParam queryParam, int linesPerPage,
                                int pagesPerQuery, int needDispPage, int totalLines,
                                String queryFlag, String orderField, String orderTrend)
            throws TranFailException;
}
