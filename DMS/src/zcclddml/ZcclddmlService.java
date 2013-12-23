package com.business.zcclddml;

import com.pub.util.TranFailException;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ycx
 * Date: 13-8-31
 * Time: 下午3:02
 * To change this template use File | Settings | File Templates.
 */
public interface ZcclddmlService {
    Zcclddml getById(String zyh) throws TranFailException;

    void add(Zcclddml zcclddml) throws TranFailException;

    void addZccl(Zccl zccl, String zclhs) throws TranFailException;

    void update(Zcclddml zcclddml) throws TranFailException, Exception;

    void delete(String rowFlag) throws TranFailException;

    Map getList(ZcclddmlParam zcclddmlParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, String queryFlag, String orderField, String orderTrend) throws TranFailException;

    Map getZcclList(ZcclParam zcclParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, String queryFlag, String orderField, String orderTrend) throws TranFailException, Exception;

    void deleteZccl(String rowFlag) throws TranFailException;

    List<Zccl> getZcclByMclh(String mclh) throws TranFailException;

    void updateZccl(String... strings) throws TranFailException, Exception;

    List<Zcclddnr> getZcclddnrList(String zyh) throws TranFailException, Exception;
}
