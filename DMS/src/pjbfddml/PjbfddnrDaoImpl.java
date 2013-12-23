package com.business.pjbfddml;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//模块名称：备件报废。数据库表明：主表pjdfddml，子表：pjdfddnr
public class PjbfddnrDaoImpl extends HibernateDaoSupport implements PjbfddnrDao {
    /**
     * 新增
     *
     * @param pjbfddnr
     */
    @Override
    public void add(Pjbfddnr pjbfddnr) {
        getHibernateTemplate().save(pjbfddnr);
    }

    /**
     * 修改
     *
     * @param pjbfddnr
     */
    @Override
    public void update(Pjbfddnr pjbfddnr) {
        getHibernateTemplate().saveOrUpdate(pjbfddnr);
    }

    /**
     * 删除自身
     *
     * @param pjbfddnr
     */
    @Override
    public void delete(Pjbfddnr pjbfddnr) {
        getHibernateTemplate().delete(pjbfddnr);
    }

    /**
     * 通过主表删除子表
     *
     * @param zyh
     */
    @Override
    public void deletePjbfddnrs(String zyh) {
        // str形式-->33|34
        String hqlDel = "delete from Pjbfddnr as p where ";
        String[] rowFlags = zyh.split("\\|");

        for (int i = 0; i < rowFlags.length; i++) {
            String temp = rowFlags[i];
            hqlDel += "(p.id.pjbfddml.zyh='" + temp + "') or";
        }
        // 截掉末尾的 or
        hqlDel = hqlDel.substring(0, hqlDel.length() - 2);
        // 批量删除
        getHibernateTemplate().bulkUpdate(hqlDel);
    }

    /**
     * 通过主表查询子表
     *
     * @param zyh
     * @return
     */
    @Override
    public Set<Pjbfddnr> queryByZyh(String zyh) {
        String query = "from Pjbfddnr as p where p.id.pjbfddml.zyh=? order by p.id.pjbfddml.zyh";
        Set<Pjbfddnr> setDdnr = new HashSet<Pjbfddnr>();

        List<Pjbfddnr> listDdnr = getHibernateTemplate().find(query, zyh);
        for (Pjbfddnr ddnr : listDdnr) {
            setDdnr.add(ddnr);
        }
        return setDdnr;
    }

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
    @Override
    public Map queryList(PjbfddmlParam pzjgParam, int linesPerPage,
                         int pagesPerQuery, int needDispPage, int totalLines,
                         final String queryFlag, String orderField, String orderTrend) {
        return null;
    }

}
