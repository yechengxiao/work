package com.business.zcclddml;

import com.pub.util.TurnPage;
import com.pub.util.tools;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: ycx
 * Date: 13-8-29
 * Time: 上午9:55
 */
public class ZcclDaoImpl extends HibernateDaoSupport implements ZcclDao {
    /**
     * 新增
     *
     * @param zccl
     */
    @Override
    public void add(Zccl zccl) {
        getHibernateTemplate().save(zccl);
    }

    /**
     * 修改
     *
     * @param zccl
     */
    @Override
    public void update(Zccl zccl) {
        getHibernateTemplate().saveOrUpdate(zccl);
    }

    /**
     * 通过mclh删除，mclh下多条zclh一并删除
     *
     * @param mclh
     */
    @Override
    public void deleteByMclh(String[] mclh) {
        String hql = "delete from Zccl as zccl where";
        for (String m : mclh) {
            hql += " zccl.zcclPK.mclh='" + m + "' or";
        }
        getHibernateTemplate().bulkUpdate(hql.substring(0, hql.length() - 3));
    }

    /**
     * 通过mclh来查询zccl
     *
     * @param mclh
     * @return
     */
    @Override
    public List<Zccl> queryByMclh(String mclh) {
        List<Zccl> zcclList;
        String query = "from Zccl as zccl where zccl.zcclPK.mclh='" + mclh + "'";
        zcclList = (List<Zccl>) getHibernateTemplate().find(query);

        return zcclList;
    }

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
    @Override
    public Map queryList(ZcclParam zcclParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, String queryFlag, String orderField, String orderTrend) {
        Map mapRs = new HashMap();
        List<Zccl> zcclList;// 用于存放列表信息。
        StringBuilder hql = new StringBuilder();// 用于查询hql语句
        StringBuilder hqlCount = new StringBuilder();// 用于统计查询总记录数
        String con = "";//拼接语句

        // 获取查询条件
        final String mclh = (zcclParam.getMclh() != null ? zcclParam.getMclh().trim() : "");


        // 组织查询hql语句
        hql.append("from Zccl as zccl");
        hqlCount.append("select count(*) from Zccl as zccl");

        // 获取数据库查询条件
        if (!mclh.equals("")) {
            con += " zccl.zcclPK.mclh like :mclh and";
        }

        if (!con.trim().equals("")) {
            con = con.substring(0, con.length() - 3);

            hql.append(" where " + con);
            hqlCount.append(" where " + con);
        }
        if (orderField == null || orderField.equals("")) {
            hql.append(" order by zccl.zcclPK.mclh");
        } else {
            hql.append(" order by " + orderField);
            if (orderTrend != null && !orderTrend.equals(""))
                hql.append(" " + orderTrend);
        }

        final String HQL = new String(hql);
        final String HQL_COUNT = new String(hqlCount);

        // 统计查询总记录数
        Long totalLinesNum;
        if ("new".equals(queryFlag)) {// 如果是一次新的查询或从删除和修改返回的查询，需要重新统计；对于从删除和修改返回的查询，queryFlag=new
            totalLinesNum = (Long) getHibernateTemplate().execute(
                    new HibernateCallback() {
                        @Override
                        public Object doInHibernate(Session session)
                                throws HibernateException, SQLException {
                            // 如果是全新查询，需要重新计算总记录数

                            // 计算该次查询的总记录数
                            Query q_count = session.createQuery(HQL_COUNT);

                            // 为查询集合记录数动态绑定参数
                            if (!mclh.equals("")) {
                                q_count.setString("mclh", "%" + mclh + "%");
                            }

                            // 返回查询集合记录数
                            return (Long) q_count.uniqueResult();
                        }
                    });
            totalLines = totalLinesNum.intValue();
            int totalPages = 0;
            if (totalLines % linesPerPage != 0)
                totalPages = totalLines / linesPerPage + 1;
            else
                totalPages = totalLines / linesPerPage;
            if (needDispPage > totalPages)
                needDispPage = totalPages;
        }

        Map queryScopeMap = tools.getTurnPageScope(linesPerPage, pagesPerQuery,
                needDispPage, totalLines);
        final int beginNum = Integer.parseInt(queryScopeMap.get("beginNum")
                .toString());// 查询起始位置
        final int qryNum = Integer.parseInt((String) queryScopeMap
                .get("qryNum").toString());// 查询记录数

        // 根据查询条件查询列表信息，并实现翻页功能
        zcclList = getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                // 根据查询条件执行查询操作
                Query q = session.createQuery(HQL);

                // 为查询动态绑定参数

                if (!mclh.equals("")) {
                    q.setString("mclh", "%" + mclh + "%");
                }


                q.setFirstResult(beginNum);// 查询的起始位置
                q.setMaxResults(qryNum);// 每次查询取出的记录数
                // 返回查询集合
                return q.list();
            }
        });

        TurnPage turnPage = new TurnPage();
        turnPage.setNeedDispPage(needDispPage);
        turnPage.setTotalLinesNum(totalLines);
        mapRs.put("turnPage", turnPage);
        mapRs.put("queryList", zcclList);
        return mapRs;
    }
}
