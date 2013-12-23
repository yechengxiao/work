package com.pub.pzjg;

import com.pub.pzjg.abstractClass.AbstractService;
import com.pub.util.TranFailException;
import com.pub.util.tools;
import org.apache.log4j.Logger;

import java.util.*;

//整车物料库
public class PzjgServiceImpl extends AbstractService implements PzjgService {

    private static Logger log = Logger.getLogger(PzjgServiceImpl.class);
    private PzjgDao pzjgDao;

    // Spring依赖注入 Dao组件所必需的setter方法
    public void setPzjgDao(PzjgDao pzjgDao) {
        this.pzjgDao = pzjgDao;
    }

    @Override
    public void add(Pzjg pzjg) throws TranFailException {
        try {
            pzjgDao.add(pzjg);
        } catch (Exception ex) {
            log.error(
                    "新增 整车物料库信息时出错！ParaVal="
                            + tools.replaceNullString(pzjg.getVsn() != null ? pzjg
                            .getVsn() : ""), ex);
            throw new TranFailException("PZJG001",
                    "pzjgServiceImpl.addPzjg(Pzjg pzjg)", ex.getMessage());
        }
    }

    @Override
    public void update(Pzjg pzjg) throws TranFailException {
        try {
            // 为需要修改的 整车物料库信息设置修改值
            pzjgDao.update(pzjg);
        } catch (Exception ex) {
            log.error(
                    "修改 整车物料库信息时出错！ParaVal="
                            + tools.replaceNullString(pzjg.getVsn() != null ? pzjg
                            .getVsn() : ""), ex);
            throw new TranFailException(
                    "PZJG002",
                    "updateServiceImpl.updatePzjg(Pzjg pzjg, List<Part> partList)",
                    ex.getMessage());
        }
    }

    @Override
    public void delete(String rowFlag) throws TranFailException {
        try {
            pzjgDao.delete(rowFlag);
        } catch (Exception ex) {
            log.error(" 整车物料库信息删除时出错！ParaVal=" + rowFlag, ex);
            throw new TranFailException("PZJG003",
                    "PzjgServiceImpl.deletePzjg(String rowFlag)",
                    ex.getMessage());
        }
    }

    @Override
    public Pzjg getById(String vsn) throws TranFailException {
        try {
            Pzjg pzjg = pzjgDao.queryById(vsn);
            return pzjg;
        } catch (Exception ex) {
            log.error(
                    "根据物料号获取 整车物料库信息时出错！ParaVal="
                            + tools.replaceNullString(vsn != null ? vsn : ""),
                    ex);
            throw new TranFailException("PZJG004",
                    "PzjgServiceImpl.getPzjgById(String vsn)", ex.getMessage());
        }
    }

    @Override
    public Map getList(PzjgParam queryParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, String queryFlag, String orderField, String orderTrend) throws TranFailException {
        try {
            Map queryMap = pzjgDao.queryList(queryParam, linesPerPage,
                    pagesPerQuery, needDispPage, totalLines, queryFlag,
                    orderField, orderTrend);
            return queryMap;
        } catch (Exception ex) {
            log.error(
                    "根据查询条件查询 整车物料库列表时出错！ParaVal="
                            + tools.replaceNullString(queryParam.getVsn() != null ? queryParam
                            .getVsn() : "")
                            + "|"
                            + tools.replaceNullString(queryParam.getCx() != null ? queryParam
                            .getCx() : "")
                            + "|"
                            + tools.replaceNullString(queryParam.getClys() != null ? queryParam
                            .getClys() : "")
                            + "|"
                            + tools.replaceNullString(queryParam.getXhms() != null ? queryParam
                            .getXhms() : "")
                            + "|"
                            + tools.replaceNullString(queryParam.getCxfl() != null ? queryParam
                            .getCxfl() : "")
                            + "|"
                            + tools.replaceNullString(queryParam.getPzlx() != null ? queryParam
                            .getPzlx() : ""), ex);
            throw new TranFailException(
                    "PZJG005",
                    "PzjgServiceImpl.getPzjgList(Pzjg pzjg, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLinesNum, String queryFlag, String orderField, String orderTrend)",
                    ex.getMessage());
        }
    }

    /**
     * 获取去重的cx，xhms，clys
     *
     * @return
     * @throws TranFailException
     */
    @Override
    public Map<String, List<String>> getPzjgString(PzjgParam pzjgParam) throws TranFailException {
        try {
            Map<String, Object> map = pzjgDao.queryList(pzjgParam, 20, 10, 1, 0, "new", "", "");//像是一次新的查询，查出所有数据
            List<Pzjg> pzjgList = (List<Pzjg>) map.get("queryList");
            List<String> cxList = new ArrayList<String>();
            List<String> xhmsList = new ArrayList<String>();
            List<String> clysList = new ArrayList<String>();
            Map retMap = new HashMap();

            for (Pzjg pzjg : pzjgList) {
                cxList.add(pzjg.getCx());
                xhmsList.add(pzjg.getXhms());
                clysList.add(pzjg.getClys());
            }
            cxList = new ArrayList<String>(new LinkedHashSet<String>(cxList));//去重并保留原有排序
            xhmsList = new ArrayList<String>(new LinkedHashSet<String>(xhmsList));
            clysList = new ArrayList<String>(new LinkedHashSet<String>(clysList));
            retMap.put("cxList", cxList);
            retMap.put("xhmsList", xhmsList);
            retMap.put("clysList", clysList);
            return retMap;
        } catch (Exception e) {
            try {
                log.error(
                        "整车物料库查找出错！ ParaVal="
                                + tools.replaceNullString("树的替代查询"), e);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            throw new TranFailException(
                    "PZJG005",
                    "PzjgServiceImpl.getPzjgList(Pzjg pzjg, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLinesNum, String queryFlag, String orderField, String orderTrend)",
                    e.getMessage());
        }
    }
}
