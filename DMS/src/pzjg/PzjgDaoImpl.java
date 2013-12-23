package com.pub.pzjg;

import com.pub.pzjg.abstractClass.AbstractDao;
import com.pub.util.TurnPage;
import com.pub.util.tools;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//整车物料库
public class PzjgDaoImpl extends AbstractDao implements PzjgDao {
    @Override
    public void add(Pzjg pzjg) {
        getHibernateTemplate().save(pzjg);
    }

    @Override
    public void update(Pzjg pzjg) {
        getHibernateTemplate().saveOrUpdate(pzjg);
    }

    @Override
    public void delete(String rowFlag) {
        // rowFlag形式-->33|34
        String hql = "delete from Pzjg as p where ";
        String[] row = rowFlag.split("\\|");
        for (int i = 0; i < row.length; i++) {
            hql += " (p.vsn='" + row[i] + "') or";
        }
        // 截掉末尾的 or
        hql = hql.substring(0, hql.length() - 2);

        // 删除整车物料库信息，可批量删除
        getHibernateTemplate().bulkUpdate(hql);
    }

    @Override
    public Pzjg queryById(String vsn) {
        Pzjg pzjg = (Pzjg) getHibernateTemplate().get(Pzjg.class, vsn);
        return pzjg;
    }

    @Override
    public Map queryList(PzjgParam queryParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, String queryFlag, String orderField, String orderTrend) {
        Map mapRs = new HashMap();
        List<Pzjg> list = new ArrayList();// 用于存放列表信息。
        StringBuffer hqlBuf = new StringBuffer();// 用于查询hql语句
        StringBuffer hqlBufCount = new StringBuffer();// 用于统计查询总记录数
        String con = "";//拼接语句

        // 获取查询条件
        final String vsn = new String(queryParam.getVsn() != null ? queryParam.getVsn().trim() : "");
        final String cxfl = new String(queryParam.getCxfl() != null ? queryParam.getCxfl().trim() : "");
        final String xhms = new String(queryParam.getXhms() != null ? queryParam.getXhms().trim() : "");
        final String cx = new String(queryParam.getCx() != null ? queryParam.getCx().trim() : "");
        final String clys = new String(queryParam.getClys() != null ? queryParam.getClys().trim() : "");
        final String pzlx = new String(queryParam.getPzlx() != null ? queryParam.getPzlx().trim() : "");
        final String zt = new String(queryParam.getZt() != null ? queryParam.getZt().trim() : "");

        // 组织查询hql语句
        hqlBuf.append("from Pzjg as p");
        hqlBufCount.append("select count(*) from Pzjg as p");

        // 获取数据库查询条件

        if (!vsn.equals("")) {
            con += " p.vsn like :vsn and";
        }
        if (!cxfl.equals("")) {
            con += " p.cxfl like :cxfl and";
        }
        if (!xhms.equals("")) {
            con += " p.xhms like :xhms and";
        }
        if (!cx.equals("")) {
            con += " p.cx like :cx and";
        }
        if (!clys.equals("")) {
            con += " p.clys like :clys and";
        }
        if (!pzlx.equals("")) {
            con += " p.pzlx like :pzlx and";
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
            hqlBuf.append(" order by p.vsn");
        } else {
            hqlBuf.append(" order by " + orderField);
            if (orderTrend != null && !orderTrend.equals(""))
                hqlBuf.append(" " + orderTrend);
        }

        final String hql = new String(hqlBuf);
        final String hqlCount = new String(hqlBufCount);

        // 统计查询总记录数
        Long totalLinesNum;

        if (queryFlag.equals("new")) {// 如果是一次新的查询或从删除和修改返回的查询，需要重新统计；对于从删除和修改返回的查询，queryFlag=new
            totalLinesNum = (Long) getHibernateTemplate().execute(
                    new HibernateCallback() {
                        public Object doInHibernate(Session session)
                                throws HibernateException, SQLException {
                            // 如果是全新查询，需要重新计算总记录数

                            // 计算该次查询的总记录数
                            Query q_count = session.createQuery(hqlCount);

                            // 为查询集合记录数动态绑定参数
                            if (!vsn.equals("")) {
                                q_count.setString("vsn", "%" + vsn + "%");
                            }
                            if (!cxfl.equals("")) {
                                q_count.setString("cxfl", "%" + cxfl + "%");
                            }
                            if (!xhms.equals("")) {
                                q_count.setString("xhms", "%" + xhms + "%");
                            }
                            if (!cx.equals("")) {
                                q_count.setString("cx", "%" + cx + "%");
                            }
                            if (!clys.equals("")) {
                                q_count.setString("clys", "%" + clys + "%");
                            }
                            if (!pzlx.equals("")) {
                                q_count.setString("pzlx", "%" + pzlx + "%");
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
                if (!vsn.equals("")) {
                    q.setString("vsn", "%" + vsn + "%");
                }
                if (!cxfl.equals("")) {
                    q.setString("cxfl", "%" + cxfl + "%");
                }
                if (!xhms.equals("")) {
                    q.setString("xhms", "%" + xhms + "%");
                }
                if (!cx.equals("")) {
                    q.setString("cx", "%" + cx + "%");
                }
                if (!clys.equals("")) {
                    q.setString("clys", "%" + clys + "%");
                }
                if (!pzlx.equals("")) {
                    q.setString("pzlx", "%" + pzlx + "%");
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
}
