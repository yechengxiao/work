package com.business.pjbfddml;

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
 * module_name:备件报废
 * user:ycx
 * date:13-8-26
 * 数据库表明：主表pjdfddml，子表：pjdfddnr
 */
public class PjbfddmlDaoImpl extends HibernateDaoSupport implements PjbfddmlDao {
    /**
     * @param pjbfddml
     */
    @Override
    public void add(Pjbfddml pjbfddml) {
        getHibernateTemplate().save(pjbfddml);
    }

    /**
     * @param pjbfddml
     */
    @Override
    public void update(Pjbfddml pjbfddml) {
        getHibernateTemplate().saveOrUpdate(pjbfddml);
    }

    /**
     * @param rowFlag
     */
    @Override
    public void delete(String rowFlag) {
        // rowFlag形式-->33|34
        String hql = "delete from Pjbfddml as p where ";
        String row[] = rowFlag.split("\\|");
        String str = new String();// 装流水号，删除子表数据时使用
        for (String zyh : row) {
            hql += "(p.zyh='" + zyh + "') or";

            Pjbfddml ddml = queryById(zyh);
            str += ddml.getZyh() + "|";
        }

        //deletePjbfddnr(str);

        // 截掉末尾的 or
        hql = hql.substring(0, hql.length() - 2);
        // 删除整车物料库信息，可批量删除
        getHibernateTemplate().bulkUpdate(hql);
    }

    /**
     * @param zyh
     * @return
     */
    @Override
    public Pjbfddml queryById(String zyh) {
        Pjbfddml pjbfddml = (Pjbfddml) getHibernateTemplate().get(
                Pjbfddml.class, zyh);
        return pjbfddml;
    }

    /**
     * 查询所有。备件报废管理页面调用的查询方法。
     *
     * @param pjbfddmlParam 备件报废信息 ，linesPerPage 每页显示的条数，pagesPerQuery
     *                      每次查询缓冲页数，needDispPage 指定将要显示哪一页，totalLinesNum
     *                      总的结果集记录数，queryFlag 查询标志，beginNum 查询起始位置, qryNum 查询记录数
     * @return 多个配件，以列表形式返回
     */
    @Override
    public Map queryList(PjbfddmlParam pjbfddmlParam, int linesPerPage,
                         int pagesPerQuery, int needDispPage, int totalLines,
                         final String queryFlag, String orderField, String orderTrend) {

        Map mapRs = new HashMap();
        List<Pjbfddml> list = new ArrayList();// 用于存放列表信息。
        StringBuffer hqlBuf = new StringBuffer();// 用于查询hql语句
        StringBuffer hqlBufCount = new StringBuffer();// 用于统计查询总记录数
        String con = "";// 查询语句后半部

        // 获取查询条件
        final String rqStart = new String(
                pjbfddmlParam.getRqStart() != null ? pjbfddmlParam.getRqStart()
                        .trim() : "");
        final String rqEnd = new String(
                pjbfddmlParam.getRqEnd() != null ? pjbfddmlParam.getRqEnd()
                        .trim() : "");
        final String zyh = new String(
                pjbfddmlParam.getZyh() != null ? pjbfddmlParam.getZyh().trim()
                        : "");
        final String ckh = new String(
                pjbfddmlParam.getCkh() != null ? pjbfddmlParam.getCkh().trim()
                        : "");
        final String czlx = new String(
                pjbfddmlParam.getCzlx() != null ? pjbfddmlParam.getCzlx()
                        .trim() : "");
        final String zt = new String(
                pjbfddmlParam.getZt() != null ? pjbfddmlParam.getZt().trim()
                        : "");

        // 组织查询hql语句
        hqlBuf.append("from Pjbfddml as p");
        hqlBufCount.append("select count(*) from Pjbfddml as p");

        // 获取数据库查询条件
        if (!rqStart.equals("")) {
            if (!rqEnd.equals("")) {
                con += " p.rq between :rqStart and :rqEnd and";
            } else {
                con += " p.rq >= :rqStart and";
            }
        } else {
            if (!rqEnd.equals("")) {
                con += " p.rq <= :rqEnd and";
            }
        }

        if (!zyh.equals("")) {
            con += " p.zyh like :zyh and";
        }
        if (!ckh.equals("")) {
            con += " p.ckh like :ckh and";
        }
        if (!czlx.equals("")) {
            con += " p.czlx like :czlx and";
        }
        if (!zt.equals("")) {
            con += " p.zt like :zt and";
        }

        if (!con.trim().equals("")) {
            con = con.substring(0, con.length() - 3);

            hqlBuf.append(" where " + con);
            hqlBufCount.append(" where " + con);
        }
        if (orderField == null || orderField.equals("")) {
            hqlBuf.append(" order by p.zyh");
        } else {
            hqlBuf.append(" order by " + orderField);
            if (orderTrend != null && !orderTrend.equals(""))
                hqlBuf.append(" " + orderTrend);
        }

        final String hql = new String(hqlBuf);
        final String hqlCount = new String(hqlBufCount);

        // 统计查询总记录数
        Long totalLinesNum = new Long("0");

        if (queryFlag.equals("new")) {// 如果是一次新的查询或从删除和修改返回的查询，需要重新统计；对于从删除和修改返回的查询，queryFlag=new
            totalLinesNum = (Long) getHibernateTemplate().execute(
                    new HibernateCallback() {
                        public Object doInHibernate(Session session)
                                throws HibernateException, SQLException {
                            // 如果是全新查询，需要重新计算总记录数

                            // 计算该次查询的总记录数
                            Query q_count = session.createQuery(hqlCount);

                            // 为查询集合记录数动态绑定参数
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
                            if (!zyh.equals("")) {
                                q_count.setString("zyh", "%" + zyh + "%");
                            }
                            if (!ckh.equals("")) {
                                q_count.setString("ckh", "%" + ckh + "%");
                            }
                            if (!czlx.equals("")) {
                                q_count.setString("czlx", "%" + czlx + "%");
                            }
                            if (!zt.equals("")) {
                                q_count.setString("zt", "%" + zt + "%");
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
        list = getHibernateTemplate().executeFind(new HibernateCallback() {
            public Object doInHibernate(Session session)
                    throws HibernateException, SQLException {

                // 根据查询条件执行查询操作
                Query q = session.createQuery(hql);

                // 为查询动态绑定参数
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
                if (!zyh.equals("")) {
                    q.setString("zyh", "%" + zyh + "%");
                }
                if (!ckh.equals("")) {
                    q.setString("ckh", "%" + ckh + "%");
                }
                if (!czlx.equals("")) {
                    q.setString("czlx", "%" + czlx + "%");
                }
                if (!zt.equals("")) {
                    q.setString("zt", "%" + zt + "%");
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
        mapRs.put("queryList", list);
        return mapRs;
    }

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
    @Override
    public Map queryBjkcBzk(BjkcBzkParam bjkcBzkParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, final String queryFlag) {
        List<BjkcBzk> bjkcBzkList = new ArrayList<BjkcBzk>();
        Map mapRs = new HashMap();

        StringBuffer queryBuf = new StringBuffer();
        StringBuffer hqlBufCount = new StringBuffer();// 统计查询总记录数
        String con = "";// 查询语句后半部

        final String ckh = bjkcBzkParam.getCkh() == null ? "" : bjkcBzkParam.getCkh().trim();
        final String clh = bjkcBzkParam.getClh() == null ? "" : bjkcBzkParam.getClh().trim();
        final String scwlh = bjkcBzkParam.getScwlh() == null ? "" : bjkcBzkParam.getScwlh().trim();
        final String clmc = bjkcBzkParam.getClmc() == null ? "" : bjkcBzkParam.getClmc().trim();
//String clh, String clmc, String scwlh, String dw, Integer kcl, Integer ydl, Integer yll, Integer cgs, String sycx, String zt, Integer mcyl, String memo, Integer cldj
        queryBuf.append("select new com.business.pjbfddml.BjkcBzk(bjkc.bjkcPK.clh,bzk.clmc,bzk.scwlh,bzk.dw,bjkc.kcl,bjkc.ydl,bjkc.yll,bjkc.cgs,bzk.sycx,bzk.zt,bzk.mcyl,bzk.memo,bjkc.cldj) from com.business.bjkc.Bjkc as bjkc,com.pub.bzk.Bzk as bzk ");
        hqlBufCount.append("select count(*) from com.business.bjkc.Bjkc as bjkc,com.pub.bzk.Bzk as bzk");

        if (!"".equals(ckh)) {
            con += " bjkc.bjkcPK.ckh like :ckh and";
        }
        if (!"".equals(clmc)) {
            con += " bzk.clmc like :clmc and";
        }
        if (!"".equals(scwlh)) {
            con += " bzk.scwlh like :scwlh and";
        }
        if (!"".trim().equals(clh)) {
            con += " bjkc.bjkcPK.clh like :clh and";
        }

        if (!con.trim().equals("")) {
            con = con.substring(0, con.length() - 3);
            queryBuf.append(" where " + con + " and bzk.bzkPK.clh=bjkc.bjkcPK.clh and bjkc.kcl > :kcl");
            hqlBufCount.append(" where " + con + " and bzk.bzkPK.clh=bjkc.bjkcPK.clh and bjkc.kcl > :kcl");
        } else {
            queryBuf.append(" where bzk.bzkPK.clh=bjkc.bjkcPK.clh and bjkc.kcl > :kcl");
            hqlBufCount.append(" where bzk.bzkPK.clh=bjkc.bjkcPK.clh and bjkc.kcl > :kcl");
        }

        final String query = new String(queryBuf);
        final String hqlCount = new String(hqlBufCount);

        // 统计查询总记录数
        Long totalLinesNum = new Long("0");
        if (queryFlag.equals("new")) {// 如果是一次新的查询或从删除和修改返回的查询，需要重新统计；对于从删除和修改返回的查询，queryFlag=new
            totalLinesNum = (Long) getHibernateTemplate().execute(
                    new HibernateCallback() {
                        public Object doInHibernate(Session session)
                                throws HibernateException, SQLException {
                            // 计算该次查询的总记录数
                            Query q_count = session.createQuery(hqlCount);

                            // 为查询集合记录数动态绑定参数
                            if (!"".equals(ckh)) {
                                q_count.setString("ckh", "%" + ckh + "%");
                            }
                            if (!"".equals(clmc)) {
                                q_count.setString("clmc", "%" + clmc + "%");
                            }
                            if (!"".equals(scwlh)) {
                                q_count.setString("scwlh", "%" + scwlh + "%");
                            }
                            if (!"".trim().equals(clh)) {
                                q_count.setString("clh", "%" + clh + "%");
                            }
                            q_count.setParameter("kcl", 0);

                            // 返回查询集合记录数
                            return (Long) q_count.uniqueResult();
                        }
                    });
            totalLines = totalLinesNum.intValue();
            int totalPages = 0;
            if (totalLines % linesPerPage != 0) {
                totalPages = totalLines / linesPerPage + 1;
            } else {
                totalPages = totalLines / linesPerPage;
            }
            if (needDispPage > totalPages) {
                needDispPage = totalPages;
            }
        }


        Map queryScopeMap = tools.getTurnPageScope(linesPerPage, pagesPerQuery,
                needDispPage, totalLines);
        final int beginNum = Integer.parseInt(queryScopeMap.get("beginNum")
                .toString());// 查询起始位置
        final int qryNum = Integer.parseInt((String) queryScopeMap
                .get("qryNum").toString());// 查询记录数

        bjkcBzkList = (List<BjkcBzk>) getHibernateTemplate().execute(
                new HibernateCallback() {
                    public Object doInHibernate(Session s)
                            throws HibernateException, SQLException {
                        Query q = s.createQuery(query);


                        if (!"".equals(ckh)) {
                            q.setString("ckh", "%" + ckh + "%");
                        }
                        if (!"".equals(clmc)) {
                            q.setString("clmc", "%" + clmc + "%");
                        }
                        if (!"".equals(scwlh)) {
                            q.setString("scwlh", "%" + scwlh + "%");
                        }
                        if (!"".trim().equals(clh)) {
                            q.setString("clh", "%" + clh + "%");
                        }
                        q.setParameter("kcl", 0);


                        q.setFirstResult(beginNum);// 查询的起始位置
                        q.setMaxResults(qryNum);// 每次查询取出的记录数
                        return q.list();// 返回查询集合
                    }
                });
        TurnPage turnPage = new TurnPage();
        turnPage.setNeedDispPage(needDispPage);
        turnPage.setTotalLinesNum(totalLines);
        mapRs.put("turnPage", turnPage);
        mapRs.put("queryList", bjkcBzkList);
        return mapRs;
    }
}
