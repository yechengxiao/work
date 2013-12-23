package com.business.zcclddml;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.util.List;

/**
 * User: ycx
 * Date: 13-8-29
 * Time: 上午9:55
 */
public class ZcclddnrDaoImpl extends HibernateDaoSupport implements ZcclddnrDao {
    /**
     * 新增
     *
     * @param zcclddnr
     */
    @Override
    public void add(Zcclddnr zcclddnr) {
        getHibernateTemplate().save(zcclddnr);
    }

    /**
     * 修改
     *
     * @param zcclddnr
     */
    @Override
    public void update(Zcclddnr zcclddnr) {
        getHibernateTemplate().saveOrUpdate(zcclddnr);
    }

    /**
     * 根据主表删除子表,可以批量删除
     *
     * @param zyh
     */
    @Override
    public void deleteByZyh(String[] zyh) {
        String query = "delete from Zcclddnr as ddnr where ";
        for (int i = 0; i < zyh.length; i++) {
            query += " (ddnr.zcclddnrPK.zyh='" + zyh[i] + "') or";
        }
        query = query.substring(0, query.length() - 3);// 截掉末尾
        getHibernateTemplate().bulkUpdate(query);
    }

    /**
     * 根据主表查询子表，可以返回多条
     *
     * @param zyh
     * @return
     */
    @Override
    public List<Zcclddnr> queryByZyh(String zyh) {
        String query = "from Zcclddnr as ddnr where ddnr.zcclddnrPK.zyh=? order by ddnr.zcclddnrPK.zyh";
        return getHibernateTemplate().find(query, zyh);
    }

}