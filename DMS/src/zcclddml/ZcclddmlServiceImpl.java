package com.business.zcclddml;


import com.business.bjkc.Bjkc;
import com.business.bjkc.BjkcDao;
import com.business.bjkc.BjkcPK;
import com.business.pjbfddml.BjkcBzk;
import com.business.pjbfddml.BjkcBzkParam;
import com.business.pjbfddml.PjbfddmlDao;
import com.pub.user.SecurityUserHolder;
import com.pub.user.User;
import com.pub.util.TranFailException;
import com.pub.util.tools;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: ycx
 * Date: 13-8-29
 * Time: 下午3:24
 */
public class ZcclddmlServiceImpl implements ZcclddmlService {
    private static final String MODULE_NAME = "拆零组装";
    private static Logger log = Logger.getLogger(ZcclddmlServiceImpl.class);
    private String rqsj = tools.getCurrentSimpleDate("yyyyMMddHHmmss", null);
    private ZcclDao zcclDao;
    private ZcclddmlDao zcclddmlDao;
    private ZcclddnrDao zcclddnrDao;
    private PjbfddmlDao pjbfddmlDao;
    private BjkcDao bjkcDao;

    public void setZcclDao(ZcclDao zcclDao) {
        this.zcclDao = zcclDao;
    }

    public void setZcclddmlDao(ZcclddmlDao zcclddmlDao) {
        this.zcclddmlDao = zcclddmlDao;
    }

    public void setZcclddnrDao(ZcclddnrDao zcclddnrDao) {
        this.zcclddnrDao = zcclddnrDao;
    }

    public void setPjbfddmlDao(PjbfddmlDao pjbfddmlDao) {
        this.pjbfddmlDao = pjbfddmlDao;
    }

    public void setBjkcDao(BjkcDao bjkcDao) {
        this.bjkcDao = bjkcDao;
    }

    @Override
    public void add(Zcclddml zcclddml) throws TranFailException {
        try {
            //zcclddml包括：zyh:ZC1309000002 ckh:C01 ckjc:默认仓库 clh:037-1701013（子表根据此号新增） clmc:导油槽(变速器壳体) cls:1.0 lx:CL zt:0  czymc:卓尔  memo:
            //需要处理的:clzjz;cfhjz;  dw;cldj;czsj;     这2个更新时处理：shr;shsj;             ddh;ywm;th;
            Zccl zccl;
            double zcljz = 0.0;
            double mclhCls = zcclddml.getCls();

            List<Zccl> zcclList = zcclDao.queryByMclh(zcclddml.getClh());
            for (int i = 0; i < zcclList.size(); i++) {
                zccl = zcclList.get(i);
                //取出zcl对象
                Bjkc bjkc = bjkcDao.queryById(new BjkcPK(zcclddml.getCkh(), zccl.getZcclPK().getZclh()));
                double cldjZcl = bjkc.getCldj();
                //更新cldj为最新的价格。
                if (zccl.getCldj() != cldjZcl) {
                    zccl.setCldj(cldjZcl);
                }
                zcljz += (zccl.getCldj()) * (zccl.getCls());//计算zcl的价值

                int zclhCls = zccl.getCls() * (int) mclhCls;
                zccl.setCls(zclhCls);

                //循环插入子表数据
                Zccl zcclTemp = zcclList.get(i);
                Zcclddnr zcclddnr = new Zcclddnr();
                ZcclddnrPK zcclddnrPK = new ZcclddnrPK();
                //设置子表属性值
                zcclddnrPK.setZyh(zcclddml.getZyh());
                zcclddnrPK.setClh(zcclTemp.getZcclPK().getZclh());
                zcclddnr.setZcclddnrPK(zcclddnrPK);
                zcclddnr.setClmc(zcclTemp.getZclmc());
                zcclddnr.setDw(zcclTemp.getDw());
                zcclddnr.setCldj(zcclTemp.getCldj());
                double cls = zcclTemp.getCls();
                if ("CL".equals(zcclddml.getLx())) {//总成 拆零成 分
                    zcclddnr.setCls(cls);
                } else {//分总成 组装成 总
                    zcclddnr.setCls(-cls);
                }
                zcclddnrDao.add(zcclddnr);
            }

            //取出mcl对象
            List<BjkcBzk> bjkcBzkListMcl = (List<BjkcBzk>) pjbfddmlDao.queryBjkcBzk(new BjkcBzkParam(zcclddml.getCkh(), zcclddml.getClh()), 20, 20, 20, 20, "").get("queryList");
            BjkcBzk bjkcBzkMcl = bjkcBzkListMcl.get(0);
            //设置主表属性值
            zcclddml.setDw(bjkcBzkMcl.getDw());
            double cldj = bjkcBzkMcl.getCldj();
            zcclddml.setCldj(cldj);

            zcclddml.setCzsj(rqsj);
            double cfhjz = bjkcBzkMcl.getCldj() * mclhCls;//总成价值,为材料本身价值？
            zcclddml.setCfhjz(cfhjz);
            double clzjz = zcljz * mclhCls;//拆零价值
            zcclddml.setClzjz(clzjz);
            if ("CL".equals(zcclddml.getLx())) {//总成 拆零成 分,总的材料数为负。分 组装成 总，子弹材料数为负。
                zcclddml.setCls(-mclhCls);
            } else {
                zcclddml.setCls(mclhCls);
            }
            zcclddmlDao.add(zcclddml);
        } catch (Exception ex) {
            log.error("新增 " + MODULE_NAME + " 信息时出错！ParaVal=", ex);
            throw new TranFailException("ZCCLDDML001", "ZcclddmlServiceImpl.add(Zcclddml zcclddml)", ex.getMessage());
        }
    }

    @Override
    public Map getList(ZcclddmlParam zcclddmlParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, String queryFlag, String orderField, String orderTrend) throws TranFailException {
        try {
            Map retMap = zcclddmlDao.queryList(zcclddmlParam, linesPerPage, pagesPerQuery, needDispPage, totalLines, queryFlag, orderField, orderTrend);
            return retMap;
        } catch (Exception e) {
            log.error("根据查询条件查询 " + MODULE_NAME + " 列表时出错！", e);
            throw new TranFailException("ZCCLDDML002", "ZcclddmlServiceImpl.getList(ZcclddmlParam zcclddmlParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, String queryFlag, String orderField, String orderTrend)", e.getMessage());
        }
    }

    @Override
    public Zcclddml getById(String zyh) throws TranFailException {
        try {
            return zcclddmlDao.queryByZyh(zyh);
        } catch (Exception ex) {
            log.error("根据流水号获取 " + MODULE_NAME + " 信息时出错！", ex);
            throw new TranFailException("ZCCLDDML002", "ZcclddmlServiceImpl.getById(String zyh)", ex.getMessage());
        }
    }

    @Override
    public void update(Zcclddml zcclddml) throws TranFailException {
        try {
            String zyh = zcclddml.getZyh();
            Zcclddml ddml = zcclddmlDao.queryByZyh(zyh);

            //设置zcclddml
            User user = SecurityUserHolder.getCurrentUser();
            ddml.setShr(user.getName());
            ddml.setShsj(rqsj);
            ddml.setZt("1");
            zcclddmlDao.update(ddml);

            //修改bjkc中的cls
            String ckh = zcclddml.getCkh().trim();
            Bjkc bjkc = bjkcDao.queryById(new BjkcPK(ckh, ddml.getClh().trim()));
            if (null != bjkc) {
                //mclh
                double cls = ddml.getCls();
                bjkc.setKcl(bjkc.getKcl() + (int) cls);
                bjkcDao.update(bjkc);
                //zclh
                List<Zcclddnr> zcclddnrList = zcclddnrDao.queryByZyh(zyh);
                for (int i = 0; i < zcclddnrList.size(); i++) {
                    double clsZcl = zcclddnrList.get(i).getCls();
                    Bjkc bjkcZcl = bjkcDao.queryById(new BjkcPK(ckh, zcclddnrList.get(i).getZcclddnrPK().getClh()));
                    bjkcZcl.setKcl(bjkcZcl.getKcl() + (int) clsZcl);
                    bjkcDao.update(bjkcZcl);
                }
            }
        } catch (Exception e) {
            log.error("修改 " + MODULE_NAME + " 信息时出错！", e);
            throw new TranFailException("ZCCLDDML003", "ZcclddmlServiceImpl.update(Zcclddml zcclddml)", e.getMessage());
        }
    }

    @Override
    public void delete(String rowFlag) throws TranFailException {
        try {
            //ZC1309000001|ZC1309000002
            String[] row = rowFlag.split("\\|");
            zcclddmlDao.deleteByZyh(row);//先删子表，再删主表
            zcclddnrDao.deleteByZyh(row);
        } catch (Exception e) {
            log.error("删除 " + MODULE_NAME + " 信息时出错！", e);
            throw new TranFailException("ZCCLDDML004", "ZcclddmlServiceImpl.delete(String rowFlag)", e.getMessage());
        }
    }

    @Override
    public List<Zcclddnr> getZcclddnrList(String zyh) throws TranFailException {
        try {
            return zcclddnrDao.queryByZyh(zyh);
        } catch (Exception ex) {
            log.error(MODULE_NAME + " 获取子表数据列表时出错！ParaVal=", ex);
            throw new TranFailException("ZCCLDDNR005", "ZcclddmlServiceImpl.getZcclddnrList(String zyh)", ex.getMessage());
        }
    }

    @Override
    public void addZccl(Zccl zccl, String zclhAll) throws TranFailException {
        try {
            Zccl z;
            ZcclPK zcclPK;
            String[] zclhs = zclhAll.split("\\|");

            for (int i = 0; i < zclhs.length; i++) {
                z = new Zccl();
                zcclPK = new ZcclPK();

                String[] zclh = zclhs[i].split("#");//#内容：clh clmc cls dw dj
                if (!zccl.getZcclPK().getMclh().equals(zclh[0])) {
                    zcclPK.setMclh(zccl.getZcclPK().getMclh());
                    zcclPK.setZclh(zclh[0]);
                    z.setZcclPK(zcclPK);
                    z.setCls(Integer.valueOf(zclh[2]));
                    z.setMclmc(zccl.getMclmc());
                    z.setZclmc(zclh[1]);
                    z.setDw(zclh[3]);
                    z.setCldj(Double.valueOf(zclh[4]));
                    zcclDao.add(z);//hibernate会有缓存，如果使用同一个对象而不是每次new一下的话，最后保存的数据其实是一样的，达不到保存多条的目的。
                }
            }
        } catch (Exception e) {
            log.error("zccl新增时出错！", e);
            throw new TranFailException("ZCCL001", "ZcclddmlServiceImpl.addZccl(Zccl zccl, String zclhAll)", e.getMessage());
        }
    }

    /**
     * 通过mclh来查询zccl
     *
     * @param mclh
     * @return
     * @throws TranFailException
     */
    @Override
    public List<Zccl> getZcclByMclh(String mclh) throws TranFailException {
        try {
            return zcclDao.queryByMclh(mclh);
        } catch (Exception ex) {
            log.error("zccl根据总成号查询时出错!", ex);
            throw new TranFailException("ZCCL002", "ZcclddmlServiceImpl.getZcclByMclh(String mclh)", ex.getMessage());
        }
    }

    /**
     * 返回的map中有翻页信息，含有对象的zcclList，其中zccl为根据总成号去重后的数据。
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
     * @throws TranFailException
     * @throws Exception
     */
    @Override
    public Map getZcclList(ZcclParam zcclParam, int linesPerPage, int pagesPerQuery, int needDispPage,
                           int totalLines, String queryFlag, String orderField, String orderTrend) throws TranFailException {
        try {
            Map retMap;
            List<Zccl> tempList;

            retMap = zcclDao.queryList(zcclParam, linesPerPage, pagesPerQuery, needDispPage, totalLines, queryFlag, orderField, orderTrend);
            if (null != retMap && null != retMap.get("queryList")) {
                tempList = (List<Zccl>) retMap.get("queryList");
                if (tempList.size() > 0) {
                    String zclhInfo = "";
                    int count = 0;
                    List<String> mclhList = new ArrayList<String>(), mclhMcList = new ArrayList<String>(), zclhInfoList = new ArrayList<String>();
                    //算出zccl表中重复的值，并分组。看zccl表结构。
                    for (int i = 0; i < tempList.size(); i++) {
                        zclhInfo = new String();
                        Zccl zcclTemp = new Zccl();
                        for (int j = i; j < tempList.size(); j++) {
                            if (tempList.get(i).getZcclPK().getMclh().equals(tempList.get(j).getZcclPK().getMclh())) {
                                zcclTemp = tempList.get(j);
                                //zcclddml新增中需要显示zclh相关信息，这里先封装起来，前台可以拆分
                                zclhInfo += zcclTemp.getZcclPK().getZclh() + "#" + zcclTemp.getZclmc() + "#"
                                        + zcclTemp.getDw() + "#" + zcclTemp.getCldj() + "#" + zcclTemp.getCls() + "|";
                                count = j;
                            } else {
                                break;
                            }
                        }
                        mclhList.add(zcclTemp.getZcclPK().getMclh());
                        mclhMcList.add(zcclTemp.getMclmc());
                        zclhInfoList.add(zclhInfo);
                        i = count;
                        if (count + 1 == tempList.size()) {
                            break;
                        }
                    }
                    Zccl zccl;
                    ZcclPK zcclPK;
                    List<Zccl> zcclList = new ArrayList();
                    for (int i = 0; i < mclhList.size(); i++) {
                        zccl = new Zccl();
                        zcclPK = new ZcclPK();

                        zcclPK.setMclh(mclhList.get(i));
                        zccl.setZcclPK(zcclPK);
                        zccl.setMclmc(mclhMcList.get(i));
                        //设置上面循环后拼装的数据，如：035-1701016#带垫圈螺栓#个#2.75#1|037-1701013#导油槽(变速器壳体)#个#4.52#1|
                        zccl.setZclmc(zclhInfoList.get(i));

                        zcclList.add(zccl);
                    }
                    retMap.put("zcclList", zcclList);
                }
            }
            return retMap;
        } catch (Exception ex) {
            log.error("zccl根据查询条件查询列表时出错！", ex);
            throw new TranFailException("ZCCL002", "ZcclddmlServiceImpl.getZcclList(ZcclParam zcclParam, int linesPerPage, int pagesPerQuery, int needDispPage,int totalLines, String queryFlag, String orderField, String orderTrend)", ex.getMessage());
        }
    }

    @Override
    public void updateZccl(String... strings) throws TranFailException {
        try {
            String mclh = strings[0];//038-1702033#一号拨叉
            String[] mclhs = mclh.split("#");
            String zclhs1 = strings[1];
            String[] zclhs2 = zclhs1.split("\\|");
            Zccl zccl;
            ZcclPK zcclPK;
            zcclDao.deleteByMclh(new String[]{mclhs[0]});
            for (int i = 0; i < zclhs2.length; i++) {
                zccl = new Zccl();
                zcclPK = new ZcclPK();
                String[] zclhs3 = zclhs2[i].split("#");//035-1701016#带垫圈螺栓#1#个#2.75
                zcclPK.setMclh(mclhs[0]);
                zcclPK.setZclh(zclhs3[0]);

                zccl.setCls(Integer.valueOf(zclhs3[2]));
                zccl.setZcclPK(zcclPK);
                zccl.setDw(zclhs3[3]);
                zccl.setCldj(Double.valueOf(zclhs3[4]));
                zccl.setMclmc(mclhs[1]);
                zccl.setZclmc(zclhs3[1]);
                zcclDao.add(zccl);
            }
        } catch (Exception ex) {
            log.error("zccl修改出错！", ex);
            throw new TranFailException("ZCCL003", "ZcclddmlServiceImpl.updateZccl(String... strings)", ex.getMessage());
        }
    }

    @Override
    public void deleteZccl(String rowFlag) throws TranFailException {
        try {
            String[] mclhs = rowFlag.split("\\|");
            zcclDao.deleteByMclh(mclhs);
        } catch (Exception ex) {
            log.error("zccl删除错误！", ex);
            throw new TranFailException("ZCCL004", "ZcclddmlServiceImpl.deleteZccl(String rowFlag)", ex.getMessage());
        }
    }


}
