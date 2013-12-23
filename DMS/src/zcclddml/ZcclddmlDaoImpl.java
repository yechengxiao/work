package com.business.zcclddml;

import com.pub.util.TurnPage;
import com.pub.util.tools;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: ycx
 * Date: 13-8-29
 * Time: 上午9:55
 */
public class ZcclddmlDaoImpl extends HibernateDaoSupport implements ZcclddmlDao {
    /**
     * 新增
     *
     * @param ddml
     */
    @Override
    public void add(Zcclddml ddml) {
        getHibernateTemplate().save(ddml);
    }

    /**
     * 修改
     *
     * @param ddml
     */
    @Override
    public void update(Zcclddml ddml) {
        getHibernateTemplate().saveOrUpdate(ddml);
    }

    /**
     * 根据主表删除子表,可以批量删除
     *
     * @param zyh
     */
    @Override
    public void deleteByZyh(String[] zyh) {
        String hql = "delete from Zcclddml as ddml where";
        for (int i = 0; i < zyh.length; i++) {
            hql += " ddml.zyh='" + zyh[i] + "' or";
        }
        hql = hql.substring(0, hql.length() - 3);
        getHibernateTemplate().bulkUpdate(hql);
    }

    /**
     * 查询，一次一条
     *
     * @param zyh
     * @return
     */
    @Override
    public Zcclddml queryByZyh(String zyh) {
        return (Zcclddml) getHibernateTemplate().get(Zcclddml.class, zyh);
    }

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
    @Override
    public Map queryList(ZcclddmlParam queryParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, String queryFlag, String orderField, String orderTrend) {
        Map mapRs = new HashMap();
        List<Zcclddml> zcclddmlList = new ArrayList();// 用于存放列表信息。
        StringBuilder hql = new StringBuilder();// 用于查询hql语句
        StringBuilder hqlCount = new StringBuilder();// 用于统计查询总记录数
        String con = "";//拼接语句

        // 获取查询条件
        final String zyh = queryParam.getZyh() != null ? queryParam.getZyh().trim() : "";
        final String ckh = queryParam.getCkh() != null ? queryParam.getCkh().trim() : "";
        final String zt = queryParam.getZt() != null ? queryParam.getZt().trim() : "";
        final String rqStart = queryParam.getRqStart() != null ? queryParam.getRqStart().trim() : "";
        final String rqEnd = queryParam.getRqEnd() != null ? queryParam.getRqEnd().trim() : "";


        // 组织查询hql语句
        hql.append("from Zcclddml as ddml");
        hqlCount.append("select count(*) from Zcclddml as ddml");

        // 获取数据库查询条件
        if (!zyh.equals("")) {
            con += " ddml.zyh like :zyh and";
        }
        if (!ckh.equals("")) {
            con += " ddml.ckh like :ckh and";
        }
        if (!zt.equals("")) {
            con += " ddml.zt like :zt and";
        }
        if (!rqStart.equals("")) {
            if (!rqEnd.equals("")) {
                con += " ddml.czsj between :rqStart and :rqEnd and";
            } else {
                con += " ddml.czsj >= :rqStart and";
            }
        } else {
            if (!rqEnd.equals("")) {
                con += " ddml.czsj <= :rqEnd and";
            }
        }

        if (!con.trim().equals("")) {
            con = con.substring(0, con.length() - 3);

            hql.append(" where " + con);
            hqlCount.append(" where " + con);
        }
        if (orderField == null || orderField.equals("")) {
            hql.append(" order by ddml.zyh");
        } else {
            hql.append(" order by " + orderField);
            if (orderTrend != null && !orderTrend.equals(""))
                hql.append(" " + orderTrend);
        }

        final String HQL = new String(hql);
        final String HQL_COUNT = new String(hqlCount);

        // 统计查询总记录数
        Long totalLinesNum;
        if (queryFlag.equals("new")) {// 如果是一次新的查询或从删除和修改返回的查询，需要重新统计；对于从删除和修改返回的查询，queryFlag=new
            totalLinesNum = (Long) getHibernateTemplate().execute(
                    new HibernateCallback() {
                        @Override
                        public Object doInHibernate(Session session)
                                throws HibernateException, SQLException {
                            // 如果是全新查询，需要重新计算总记录数

                            // 计算该次查询的总记录数
                            Query q_count = session.createQuery(HQL_COUNT);

                            // 为查询集合记录数动态绑定参数
                            if (!zyh.equals("")) {
                                q_count.setString("zyh", "%" + zyh + "%");
                            }
                            if (!ckh.equals("")) {
                                q_count.setString("ckh", "%" + ckh + "%");
                            }
                            if (!zt.equals("")) {
                                q_count.setString("zt", "%" + zt + "%");
                            }
                            if (!rqStart.equals("")) {
                                if (!rqEnd.equals("")) {
                                    q_count.setString("rqStart", rqStart);
                                    q_count.setString("rqEnd", rqEnd);
                                } else {
                                    q_count.setString("rqStart", rqStart);
                                }
                            } else {
                                if (!rqEnd.equals("")) {
                                    q_count.setString("rqEnd", rqEnd);
                                }
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
        zcclddmlList = getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                // 根据查询条件执行查询操作
                Query q = session.createQuery(HQL);

                // 为查询动态绑定参数
                if (!zyh.equals("")) {
                    q.setString("zyh", "%" + zyh + "%");
                }
                if (!ckh.equals("")) {
                    q.setString("ckh", "%" + ckh + "%");
                }
                if (!zt.equals("")) {
                    q.setString("zt", "%" + zt + "%");
                }
                if (!rqStart.equals("")) {
                    if (!rqEnd.equals("")) {
                        q.setString("rqStart", rqStart);
                        q.setString("rqEnd", rqEnd);
                    } else {
                        q.setString("rqStart", rqStart);
                    }
                } else {
                    if (!rqEnd.equals("")) {
                        q.setString("rqEnd", rqEnd);
                    }
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
        mapRs.put("queryList", zcclddmlList);
        return mapRs;
    }


}

