package com.business.pjbfddml;

import com.pub.util.BaseDAO;
import com.pub.util.TranFailException;
import com.pub.util.tools;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;

//备件报废
public class PjbfddmlServiceImpl implements PjbfddmlService {
    private static Logger log = Logger.getLogger(PjbfddmlServiceImpl.class);
    private PjbfddmlDao pjbfddmlDao;
    private PjbfddnrDao pjbfddnrDao;
    private BaseDAO dao;// 用来获取流水号

    // Spring依赖注入 Dao组件所必需的setter方法
    @Override
    public void setPjbfddmlDao(PjbfddmlDao pjbfddmlDao) {
        this.pjbfddmlDao = pjbfddmlDao;
    }

    @Override
    public void setPjbfddnrDao(PjbfddnrDao pjbfddnrDao) {
        this.pjbfddnrDao = pjbfddnrDao;
    }

    @Override
    public void setDao(BaseDAO dao) {
        this.dao = dao;
    }

    // 获取下一个编号，这里是流水号
    // action中，String sqdh = bjthglService.getNextBh("表名", rq.substring(0, 6),
    // "");
    @Override
    public String getNextBh(String v_dymk, String v_rq, String v_fwzh)
            throws TranFailException {
        return dao.getNextBh(v_dymk, v_rq, v_fwzh);
    }

    @Override
    public void add(Pjbfddml pjbfddml, List<Pjbfddnr> pjbfddnrs) throws TranFailException, Exception {
        try {
            pjbfddmlDao.add(pjbfddml);

            for (Pjbfddnr ddnr : pjbfddnrs) {// 先新增主表，再新增主表
                pjbfddnrDao.add(ddnr);
            }
        } catch (Exception ex) {
            log.error(
                    "新增 备件报废时出错！ParaVal="
                            + tools.replaceNullString(pjbfddml.getZyh() != null ? pjbfddml
                            .getZyh() : ""), ex);
            throw new TranFailException("PJBFDDML001",
                    "PjbfddmlServiceImpl.add(Pjbfddml pjbfddml, List<Pjbfddnr> pjbfddnrs)",
                    ex.getMessage());
        }
    }

    /**
     * 修改。
     *
     * @param ddml
     * @param ddnrList
     * @throws TranFailException
     */
    @Override
    public void update(Pjbfddml ddml, List<Pjbfddnr> ddnrList) throws TranFailException {
        try {
            // 为需要修改的 整车物料库信息设置修改值
            pjbfddmlDao.update(ddml);
            Pjbfddml ddml1Old = getById(ddml.getZyh());

            ddml1Old.setCkh(ddml.getCkh());
            ddml1Old.setCkjc(ddml.getCkjc());
            ddml1Old.setCzbz(ddml.getCzbz());
            ddml1Old.setCzlx(ddml.getCzlx());
            ddml1Old.setCzr(ddml.getCzr());
            ddml1Old.setMemo(ddml.getMemo() == null ? "" : ddml.getMemo());
            ddml1Old.setRq(ddml.getRq());
            ddml1Old.setSj(ddml.getSj());
            ddml1Old.setZt(ddml.getZt());
            ddml1Old.setBfje(ddml.getBfje());
            ddml1Old.setBfpz(ddml.getBfpz());

            for (Pjbfddnr ddnr : ddnrList) {
                //更新时，直接删除老数据，然后再将修改后数据插入数据库
                for (Pjbfddnr p : ddnrList) {
                    pjbfddnrDao.add(p);
                }
            }
        } catch (Exception ex) {
            log.error(
                    "修改 备件报废时出错！ParaVal="
                            + tools.replaceNullString(ddml.getCkh() != null ? ddml
                            .getCkh() : ""), ex);
            throw new TranFailException(
                    "PJBFDDML002",
                    "updateServiceImpl.updatePjbfddml(Pjbfddml pjbfddml, List<Part> partList)",
                    ex.getMessage());
        }
    }

    /**
     * 删除。
     *
     * @param rowFlag 多个id拼接，用"|"分隔
     * @return
     */
    @Override
    public void delete(String rowFlag) throws TranFailException {
        try {
            pjbfddnrDao.deletePjbfddnrs(rowFlag);//先删子表，再删主表
            pjbfddmlDao.delete(rowFlag);
        } catch (Exception ex) {
            log.error(" 删除 备件报废时出错！ParaVal=" + rowFlag, ex);
            throw new TranFailException("PJBFDDML003",
                    "PjbfddmlServiceImpl.deletePjbfddml(String rowFlag)",
                    ex.getMessage());
        }
    }

    /**
     * 根据流水号删除子表数据。可删除多条。
     *
     * @param zyh
     * @throws TranFailException
     */
    @Override
    public void deletePjbfddnrsByZyh(String zyh) throws TranFailException {
        try {
            pjbfddnrDao.deletePjbfddnrs(zyh);
        } catch (Exception e) {
            log.error(" 根据流水号删除子表数据。时出错！ParaVal=" + zyh, e);
        }
    }

    /**
     * @param zyh
     * @return
     * @throws TranFailException
     */
    @Override
    public Pjbfddml getById(String zyh) throws TranFailException {
        try {
            Pjbfddml pjbfddml = pjbfddmlDao.queryById(zyh);
            return pjbfddml;
        } catch (Exception ex) {
            log.error(
                    "根据流水号获取 备件报废时出错！ParaVal="
                            + tools.replaceNullString(zyh != null ? zyh : ""),
                    ex);
            throw new TranFailException("PJBFDDML004",
                    "PjbfddmlServiceImpl.getPjbfddmlById(String zyh)",
                    ex.getMessage());
        }
    }

    /**
     * 返回主表对应的子表内容集合
     *
     * @param zyh
     * @return
     * @throws TranFailException
     */
    @Override
    public Set<Pjbfddnr> getDdnrByZyh(String zyh) throws TranFailException {

        try {
            return pjbfddnrDao.queryByZyh(zyh);
        } catch (Exception e) {
            log.error(
                    "根据流水号获取 pjbfddnr出错！ParaVal="
                            + tools.replaceNullString(zyh != null ? zyh : ""),
                    e);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param pjbfddmlParam
     * @param linesPerPage
     * @param pagesPerQuery
     * @param needDispPage
     * @param totalLines
     * @param queryFlag
     * @param orderField
     * @param orderTrend
     * @return
     * @throws TranFailException
     */
    @Override
    public Map getList(PjbfddmlParam pjbfddmlParam, int linesPerPage,
                       int pagesPerQuery, int needDispPage, int totalLines,
                       String queryFlag, String orderField, String orderTrend)
            throws TranFailException {
        try {
            Map queryMap = pjbfddmlDao.queryList(pjbfddmlParam, linesPerPage,
                    pagesPerQuery, needDispPage, totalLines, queryFlag,
                    orderField, orderTrend);
            return queryMap;
        } catch (Exception ex) {
            log.error(
                    "根据查询条件查询 备件报废时出错！ParaVal="
                            + tools.replaceNullString(pjbfddmlParam.getCkh() != null ? pjbfddmlParam
                            .getCkh() : "")
                    // + "|"
                    // + tools.replaceNullString(pjbfddmlParam.getCx() != null ?
                    // pjbfddmlParam
                    // .getCx() : "")
                    , ex);
            throw new TranFailException(
                    "PJBFDDML005",
                    "PjbfddmlServiceImpl.getPjbfddmlList(Pjbfddml pjbfddml, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLinesNum, String queryFlag, String orderField, String orderTrend)",
                    ex.getMessage());
        }
    }

    /**
     * @param bjkcBzkParam
     * @param linesPerPage
     * @param pagesPerQuery
     * @param needDispPage
     * @param totalLines
     * @param queryFlag
     * @param orderField
     * @param orderTrend
     * @return
     * @throws TranFailException
     */
    @Override
    public Map getBjkcBzkList(BjkcBzkParam bjkcBzkParam, int linesPerPage,
                              int pagesPerQuery, int needDispPage, int totalLines,
                              String queryFlag, String orderField, String orderTrend)
            throws TranFailException {
        try {
            Map queryMap = pjbfddmlDao.queryBjkcBzk(bjkcBzkParam, linesPerPage,
                    pagesPerQuery, needDispPage, totalLines, queryFlag);
            return queryMap;
        } catch (Exception ex) {
            log.error(
                    "根据查询条件查询 备件信息时出错！ParaVal="
                            + tools.replaceNullString(bjkcBzkParam.getCkh() != null ? bjkcBzkParam
                            .getCkh() : ""), ex);
            throw new TranFailException(
                    "PJBFDDML006",
                    "PjbfddmlServiceImpl.getBjkcBzkList(BjkcBzkParam bjkcBzkParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLinesNum, String queryFlag, String orderField, String orderTrend)",
                    ex.getMessage());
        }
    }
}
