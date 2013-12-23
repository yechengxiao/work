package com.business.pjbfddml;

import com.pub.util.BaseDAO;
import com.pub.util.TranFailException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PjbfddmlService {

    // Spring依赖注入 Dao组件所必需的setter方法
    void setPjbfddmlDao(PjbfddmlDao pjbfddmlDao);

    void setPjbfddnrDao(PjbfddnrDao pjbfddnrDao);

    void setDao(BaseDAO dao);

    // 获取下一个编号，这里是流水号
    // action中，String sqdh = bjthglService.getNextBh("表名", rq.substring(0, 6),
    // "");
    String getNextBh(String v_dymk, String v_rq, String v_fwzh)
            throws TranFailException;

    void add(Pjbfddml pjbfddml, List<Pjbfddnr> pjbfddnrs) throws TranFailException, Exception;

    void update(Pjbfddml pjbfddml, List<Pjbfddnr> pjbfddnrs) throws TranFailException;

    void delete(String rowFlag) throws TranFailException;

    void deletePjbfddnrsByZyh(String zyh) throws TranFailException;

    Pjbfddml getById(String zyh) throws TranFailException;

    Set<Pjbfddnr> getDdnrByZyh(String zyh) throws TranFailException;

    Map getList(PjbfddmlParam pjbfddmlParam, int linesPerPage,
                int pagesPerQuery, int needDispPage, int totalLines,
                String queryFlag, String orderField, String orderTrend)
            throws TranFailException;

    Map getBjkcBzkList(BjkcBzkParam bjkcBzkParam, int linesPerPage,
                       int pagesPerQuery, int needDispPage, int totalLines,
                       String queryFlag, String orderField, String orderTrend)
            throws TranFailException;
}