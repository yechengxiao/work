package com.pub.bxgsk;

import com.pub.util.TranFailException;
import com.pub.util.tools;
import org.apache.log4j.Logger;

import java.util.Map;

public class BxgskServiceImpl implements BxgskService {
    private static final String MODULE_NAME = "保险公司";
    private static Logger log = Logger.getLogger(BxgskServiceImpl.class);
    private BxgskDao bxgskDao;

    public void setBxgskDao(BxgskDao bxgskDao) {
        this.bxgskDao = bxgskDao;
    }

    @Override
    public void add(Bxgsk bxgsk) throws TranFailException {
        try {
            String userName = com.pub.user.SecurityUserHolder.getCurrentUser().getName();
            bxgsk.setCzymc(userName);
            String czrq = tools.getCurrentSimpleDate("yyyyMMdd", "");
            bxgsk.setCzrq(czrq);

            bxgskDao.add(bxgsk);
        } catch (Exception ex) {
            log.error("新增 " + MODULE_NAME + " 信息时出错！ParaVal=", ex);
            throw new TranFailException("BXGSK001", "ZcclddmlServiceImpl.add(Zcclddml zcclddml)", ex.getMessage());
        }
    }

    @Override
    public void delete(String rowFlag) throws TranFailException {
        try {
            //ZC1309000001|ZC1309000002
            String[] row = rowFlag.split("\\|");
            bxgskDao.deleteByBxgsdm(row);
        } catch (Exception e) {
            log.error("删除 " + MODULE_NAME + " 信息时出错！", e);
            throw new TranFailException("BXGSK004", "BxgskServiceImpl.delete(String rowFlag)", e.getMessage());
        }
    }

    @Override
    public void update(Bxgsk bxgsk) throws TranFailException {
        try {
            String czrq = tools.getCurrentSimpleDate("yyyyMMdd", "");
            bxgsk.setCzrq(czrq);
            bxgskDao.update(bxgsk);
        } catch (Exception e) {
            log.error("修改 " + MODULE_NAME + " 信息时出错！", e);
            throw new TranFailException("BXGSK003", "BxgskServiceImpl.update(Bxgsk bxgsk)", e.getMessage());
        }
    }

    @Override
    public Map getList(BxgskParam bxgskParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, String queryFlag, String orderField, String orderTrend) throws TranFailException {
        try {
            Map retMap = bxgskDao.queryList(bxgskParam, linesPerPage, pagesPerQuery, needDispPage, totalLines, queryFlag, orderField, orderTrend);
            return retMap;
        } catch (Exception e) {
            log.error("根据查询条件查询 " + MODULE_NAME + " 列表时出错！", e);
            throw new TranFailException("BXGSK002", "BxgskServiceImpl.getList(BxgskParam bxgskParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, String queryFlag, String orderField, String orderTrend)", e.getMessage());
        }
    }

    @Override
    public Bxgsk getById(String bxgsdm) throws TranFailException {
        try {
            return bxgskDao.queryByBxgsdm(bxgsdm);
        } catch (Exception ex) {
            log.error("根据流水号获取 " + MODULE_NAME + " 信息时出错！", ex);
            throw new TranFailException("BXGSK002", "BxgskServiceImpl.getById(String zyh)", ex.getMessage());
        }
    }
}
