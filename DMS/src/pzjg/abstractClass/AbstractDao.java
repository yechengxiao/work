package com.pub.pzjg.abstractClass;

import com.pub.pzjg.Pzjg;
import com.pub.pzjg.PzjgParam;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.Map;

/**
 * User: ycx
 * Date: 13-7-30
 * Time: 下午3:19
 * 说明：抽象类。通用方法定义，子类按需重写此类中的方法。
 */
public abstract class AbstractDao extends HibernateDaoSupport {

    /**
     * 新增
     *
     * @param pzjg
     */
    public abstract void add(Pzjg pzjg);

    /**
     * 修改
     *
     * @param pzjg
     */
    public abstract void update(Pzjg pzjg);

    /**
     * 删除，允许一次删除多条数据
     *
     * @param rowFlag
     */
    public abstract void delete(String rowFlag);

    /**
     * 根据主键查询
     *
     * @param vsn
     * @return
     */
    public abstract Pzjg queryById(String vsn);

    /**
     * 在数据库中查询符合条件的所有数据
     * queryParam为查询条件对象。
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
    public abstract Map queryList(PzjgParam queryParam, int linesPerPage,
                                  int pagesPerQuery, int needDispPage, int totalLines,
                                  final String queryFlag, String orderField, String orderTrend);
}
