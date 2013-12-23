package com.business.pjbfddml;

import com.business.bjkc.Bjkc;
import com.business.bjkc.BjkcPK;
import com.business.bjkc.BjkcService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pub.init.SysConstance;
import com.pub.util.ErrorHandleUtil;
import com.pub.util.TranFailException;
import com.pub.util.TurnPage;
import com.pub.util.tools;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class PjbfddmlAction extends ActionSupport {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(PjbfddmlAction.class);
    String baseWebPath = (String) SysConstance.getParameterSettings().get(
            "baseWebPath");
    private Map session = ActionContext.getContext().getSession();
    private PjbfddmlService pjbfddmlService; // Spring DI
    private Pjbfddml pjbfddml;// 表单直接提交对象，如pzjg.vsn
    private Pjbfddnr pjbfddnr;// 子表
    private PjbfddmlParam pjbfddmlParam;// 查询用到
    private BjkcService bjkcService;// bjkc
    private Bjkc bjkc;
    private List<Pjbfddml> ckhckjcList;
    private BjkcBzkParam bjkcBzkParam;// 新增页面，搜索备件信息
    private List<BjkcBzk> bjkcBzkList;// 返回查询结果列表
    private BjkcBzk bjkcBzk;
    private String rowflag_modify;// 修改信息时，记录需要修改的内容
    private String rowflag_str;// 删除用，传整车物料号，用"|"拼接。
    // 查询时用
    private List<Pjbfddml> pjbfddmlList;// 返回查询结果列表
    private String queryOrder;
    private String submit_type;// 用于标识ajax提交或表单提交
    private String module_str;// 其他模块中可能调用
    // 单击标题排序
    private String orderField;// 需要排序的字段
    private String orderTrend;// 升序/降序
    private TurnPage turnPage;// 用于翻页参数
    private Map okMap = new HashMap();
    private Map errorMap = new HashMap();
    private String result;// 用于返回结果jsp页面

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        PjbfddmlAction.log = log;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * 转到当前模块首页
     *
     * @param
     * @return Action常量
     */
    public String dispatchPjbfddmlMainPage() throws Exception {
        try {
            List<Pjbfddml> pjbfddmlList;

            if (session.get("ckh") != null) {
                session.remove("ckh");// 保证值是新的
            }

            pjbfddmlList = bjkcService.getCkhCkjc();
            if (pjbfddmlList != null && pjbfddmlList.size() != 0) {
                //取出第一个仓库号，在新增界面显示此仓库号。
                session.put("ckh", pjbfddmlList.get(0).getCkh());
            } else {
                session.put("ckh", "无仓库");
            }

            if (session != null) {
                session.remove("pjbfddmlConParas");
            }
            result = "/WEB-INF/pages/business/pjbfddml/pjbfddml_mng_list.jsp";
        } catch (Exception e) {
            log.error(
                    "转到 备件报废维护首页时出错！ParaVal=PjbfddmlAction.dispatchPjbfddmlMainPage()",
                    e);
            throw e;
        }
        return SUCCESS;
    }

    /**
     * 显示仓库号、仓库简称
     *
     * @return
     * @throws Exception
     */
    public String dispatchCkhCkjc() throws Exception {
        try {
            ckhckjcList = bjkcService.getCkhCkjc();
            System.out.println(ckhckjcList.get(0).getCkh() + " " + ckhckjcList.get(0).getCkjc());
            result = "/WEB-INF/pages/business/pjbfddml/ckh_ckjc.jsp";
            return SUCCESS;
        } catch (Exception e) {
            try {
                log.error("选择仓库号时出错！ParaVal=", e);
            } catch (Exception ex) {
            }
            throw e;
        }
    }

    /**
     * 转到新增页面
     *
     * @param
     * @return Action常量
     */
    public String turnAddPage() throws TranFailException, Exception {
        try {
            result = "/WEB-INF/pages/business/pjbfddml/pjbfddml_mng_add.jsp";
            return SUCCESS;
        } catch (Exception e) {
            log.error("跳转到新增页面出错！", e);
            throw e;
        }
    }

    /**
     * 服务站，查看详情，成功后转到详情页面
     *
     * @param
     * @return Action常量
     */
    public String turnViewPage() throws TranFailException, Exception {
        try {
            pjbfddml = pjbfddmlService.getById(pjbfddml.getZyh());
            result = "/WEB-INF/pages/pub/pzjg/fwz_pzjg_mng_view.jsp";
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(),
                    te.getDisplayMessage(), te.getErrorLocation(),
                    te.getErrorMsg());
            throw te;
        } catch (Exception e) {
            log.error(
                    "服务站端查询详情出错！ParaVal="
                            + tools.replaceNullString(pjbfddml.getZyh() != null ? pjbfddml
                            .getZyh() : ""), e);
            throw e;
        }
        return SUCCESS;
    }

    /**
     * 转到修改页面
     *
     * @param
     * @return Action常量
     */
    public String turnModifyPage() throws TranFailException, Exception {
        try {
            String zyh = pjbfddml.getZyh();
            pjbfddml = pjbfddmlService.getById(zyh);
            Set<Pjbfddnr> pjbfddnrs = pjbfddmlService.getDdnrByZyh(zyh);
            pjbfddml.setPjbfddnrs(pjbfddnrs);

            if (pjbfddnrs != null) {
                bjkcBzkList = new ArrayList();
                for (Pjbfddnr ddnr : pjbfddnrs) {
                    bjkcBzk = new BjkcBzk();
                    bjkcBzk.setClh(ddnr.getId().getClh());
                    bjkcBzk.setClmc(ddnr.getClmc());
                    bjkcBzk.setDw(ddnr.getDw());
                    bjkcBzk.setCls(ddnr.getCls());
                    // 处理价格
                    BigDecimal big = new BigDecimal(ddnr.getCldj());
                    bjkcBzk.setCldj(Integer.valueOf(big.toString()));
                    // 获得库存量kcl
                    String ckh = pjbfddml.getCkh().trim();
                    String clh = ddnr.getId().getClh().trim();
                    bjkc = new Bjkc();
                    bjkc = bjkcService.getBjkcById(new BjkcPK(ckh, clh));
                    bjkcBzk.setKcl(bjkc.getKcl());

                    bjkcBzkList.add(bjkcBzk);
                }
            }
            result = "/WEB-INF/pages/business/pjbfddml/pjbfddml_mng_modify_input.jsp";
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(),
                    te.getDisplayMessage(), te.getErrorLocation(),
                    te.getErrorMsg());
            throw te;
        } catch (Exception e) {
            log.error(
                    "打开修改页面出错！ParaVal="
                            + tools.replaceNullString(pjbfddml.getZyh() != null ? pjbfddml
                            .getZyh() : ""), e);
            throw e;
        }
        return SUCCESS;
    }

    /**
     * bjkc、bzk 查询信息，成功后转到配置价格选择页面
     *
     * @param
     * @return Action常量
     */
    public String turnChooseBjkcPage() throws TranFailException, Exception {
        try {
            result = "/WEB-INF/pages/business/pjbfddml/bjkc_bzk_choose_mng_list.jsp";
        } catch (Exception e) {
            log.error("打开备件查询信息出错！", e);
            throw e;
        }
        return SUCCESS;
    }

    // 生成流水号
    private String getZyh() throws TranFailException {
        Calendar now = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String rq = sdf.format(now.getTime());

        String zyh = pjbfddmlService.getNextBh("PJBFDDML", rq, "");
        return zyh;
    }

    /**
     * 新增，成功后转到OK页面
     *
     * @param
     * @return Action常量
     */
    public String add() throws TranFailException, Exception {
        try {
            Map rec = setDdmlDdnr(pjbfddml, rowflag_str);
            Pjbfddml pjbfddml = (Pjbfddml) rec.get("pjbfddml");
            List<Pjbfddnr> pjbfddnrs = (List) rec.get("pjbfddnrs");

            pjbfddmlService.add(pjbfddml, pjbfddnrs);// 先添加主表内容

            okMap.put("okTitle", "操作成功");
            okMap.put("okMsg", " 备件报废新增成功！");
            // 封装返回OK页面的参数
            Vector v = new Vector();
            String[] para1 = {"queryOrder", null};
            String[] para2 = {"turnPage.needDispPage", "1"};
            v.add(para1);
            v.add(para2);

            okMap.put("okParameters", v);
            okMap.put("okReturn", baseWebPath
                    + "/pjbfddml/queryListAction.action");
            okMap.put("returnType", "close_parent_flush");// 参数returnType=close_parent_flush,表示在关闭对话框时更新父窗口页面

            result = "/ok.jsp";
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(),
                    te.getDisplayMessage(), te.getErrorLocation(),
                    te.getErrorMsg());
            // 如果是模式对话框操作报错，则需要在errorMap中增加变量winType
            errorMap.put("winType", "dialogue");
            throw te;
        } catch (Exception e) {
            // 如果是模式对话框操作报错，则需要在errorMap中增加变量winType
            errorMap.put("winType", "dialogue");
            log.error(
                    "新增 备件报废信息交易出错！ParaVal="
                            + tools.replaceNullString(pjbfddml.getZyh()) != null ? pjbfddml
                            .getZyh() : "", e);
            throw e;
        }
        return SUCCESS;
    }

    /**
     * 修改，成功后转到OK页面
     *
     * @param
     * @return Action常量
     */
    public String update() throws TranFailException, Exception {
        try {
            Map rec = setDdmlDdnr(pjbfddml, rowflag_modify);
            Pjbfddml pjbfddml = (Pjbfddml) rec.get("pjbfddml");
            List<Pjbfddnr> pjbfddnrs = (List) rec.get("pjbfddnrs");

            pjbfddmlService.update(pjbfddml, pjbfddnrs);

            okMap.put("okTitle", "操作成功");
            okMap.put("okMsg", " 备件报废修改成功！");
            // 封装返回OK页面的参数

            Vector v = new Vector();
            String[] para1 = {"queryOrder", "back"};
            String[] para2 = {"turnPage.needDispPage",
                    turnPage.getNeedDispPage() + ""};
            v.add(para1);
            v.add(para2);

            okMap.put("okParameters", v);
            okMap.put("okReturn", baseWebPath
                    + "/pjbfddml/queryListAction.action");

            okMap.put("returnType", "close_parent_flush");// 参数returnType=close_parent_flush,表示在关闭对话框时更新父窗口页面
            result = "/ok.jsp";
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(),
                    te.getDisplayMessage(), te.getErrorLocation(),
                    te.getErrorMsg());
            // 如果是模式对话框操作报错，则需要在errorMap中增加变量winType
            errorMap.put("winType", "dialogue");
            throw te;
        } catch (Exception e) {
            // 如果是模式对话框操作报错，则需要在errorMap中增加变量winType
            errorMap.put("winType", "dialogue");
            log.error(
                    "修改 备件报废交易出错！ParaVal="
                            + tools.replaceNullString(pjbfddml.getZyh() != null ? pjbfddml
                            .getZyh() : ""), e);
            throw e;
        }
        return SUCCESS;
    }

    /**
     * 设置主表、子表的值
     * row中为封装成String的Pjbfddnr
     *
     * @param pjbfddml
     * @param row
     * @return
     * @throws TranFailException
     */
    private Map setDdmlDdnr(Pjbfddml pjbfddml, String row)
            throws TranFailException {
        // 设置仓库简称
        String ckh = pjbfddml.getCkh();
        // 设置品种
        String[] ddnr = row.split("\\|");// 先用|分隔，在用#分隔。
        int bfpz = ddnr.length;// 具体报废的备件的品种数
        pjbfddml.setBfpz(bfpz);

        BigDecimal je = null;
        Double bfje = 0.0;
        // pjbfddnr中的字段
        String clh = "";
        String clmc = "";
        String dw = "";// 单位
        String cldj = "";// 采购价,2位小数
        String cls = "";
        List<Pjbfddnr> listPjbfddnr = new ArrayList();
        for (int i = 0; i < bfpz; i++) {
            String[] ddnr1 = ddnr[i].split("#");
            clh = ddnr1[0].trim();
            clmc = ddnr1[1].trim();
            dw = ddnr1[2].trim();
            cldj = ddnr1[3].trim();
            cls = ddnr1[4].trim();
            String kcl = ddnr1[5] != null ? ddnr1[5].trim() : "0";// 报废后，kcl需要减掉。还原后kcl需要加上。

            PjbfddnrId pjbfddnrId = null;
            if (null == pjbfddml.getZyh() || "".equals(pjbfddml.getZyh())) {
                pjbfddnrId = new PjbfddnrId(pjbfddml, clh);
            } else {
                //更新时，直接删除老数据，然后再将修改后数据插入数据库
                pjbfddmlService.deletePjbfddnrsByZyh(pjbfddml.getZyh());
                pjbfddnrId = new PjbfddnrId(pjbfddml, clh);
            }
            pjbfddnr = new Pjbfddnr();
            pjbfddnr.setId(pjbfddnrId);
            pjbfddnr.setClmc(clmc);
            pjbfddnr.setDw(dw);
            pjbfddnr.setCldj(Double.parseDouble(cldj));
            pjbfddnr.setCls(Integer.parseInt(cls));

            listPjbfddnr.add(pjbfddnr);

            // 计算bfje（金额）
            bfje += Double.valueOf(cldj) * Integer.valueOf(cls);

            if ("1".equals(pjbfddml.getZt())) {// 状态为已确认，则更新bjkc的kcl
                if ("BF".equals(pjbfddml.getCzlx())) {
                    bjkc = bjkcService.getBjkcById(new BjkcPK(ckh, clh));
                    if (!"0".equals(kcl)) {
                        bjkc.setKcl(Integer.parseInt(kcl)
                                - Integer.parseInt(cls));
                        bjkcService.update(bjkc);
                    }
                } else if ("HY".equals(pjbfddml.getCzlx())) {
                    bjkc = bjkcService.getBjkcById(new BjkcPK(ckh, clh));
                    if (!"0".equals(kcl)) {
                        bjkc.setKcl(Integer.parseInt(kcl)
                                + Integer.parseInt(cls));
                        bjkcService.update(bjkc);
                    }
                }
            }
        }
        // 设置金额
        je = new BigDecimal(bfje);
        pjbfddml.setBfje(je.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

        // 设置日期、时间。add()/modify()
        SimpleDateFormat sdfRq = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdfSj = new SimpleDateFormat("HHmmss");
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        String rq = sdfRq.format(date);
        String sj = sdfSj.format(date);
        pjbfddml.setRq(rq);
        pjbfddml.setSj(sj);
        pjbfddml.setCzbz("0");// CZBZ字段意义不明
        // 设置流水号
        if (null == pjbfddml.getZyh() || "".equals(pjbfddml.getZyh())) {
            // add()
            String zyhAdd = getZyh();
            // 设置流水号
            pjbfddml.setZyh(zyhAdd);
        }
        // 返回pjbfddml，多条pjbfddnr
        Map ret = new HashMap();
        ret.put("pjbfddml", pjbfddml);
        ret.put("pjbfddnrs", listPjbfddnr);
        return ret;
    }

    /**
     * 删除 一次允许删除多个
     * 传入值为PK#zt
     *
     * @param
     * @return Action常量
     */
    public String delete() throws TranFailException, Exception {
        try {
            String[] row1 = rowflag_str.split("\\|");
            String[] row2;
            String rowflag_str2 = new String();
            for (int i = 0; i < row1.length; i++) {
                row2 = row1[i].split("#");
                if ("0".equals(row2[1])) {//筛选状态为0的数据，这样可以删除
                    rowflag_str2 += row2[0] + "|";
                }
            }
            if (!rowflag_str2.isEmpty()) {
                pjbfddmlService.delete(rowflag_str2);
                okMap.put("okTitle", "操作成功");
                okMap.put("okMsg", "状态为暂存的数据删除成功！");
            } else {
                okMap.put("okTitle", "操作失败");
                okMap.put("okMsg", "只有状态为暂存的数据可删除！");
            }
            // 封装返回OK页面的参数
            Vector v = new Vector();
            String[] para1 = {"queryOrder", "back"};
            String[] para2 = {"turnPage.needDispPage", turnPage.getNeedDispPage() + ""};
            v.add(para1);
            v.add(para2);

            okMap.put("okParameters", v);
            okMap.put("okReturn", baseWebPath
                    + "/pjbfddml/queryListAction.action");
            result = "/ok.jsp";

            return SUCCESS;
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(),
                    te.getDisplayMessage(), te.getErrorLocation(),
                    te.getErrorMsg());
            throw te;
        } catch (Exception e) {
            log.error("删除 备件报废信息出错！ParaVal=" + rowflag_str, e);
            throw e;
        }
    }

    /**
     * 获取 整车物料库信息列表，并实现翻页功能。同时对该Action的Session进行处理
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public String queryList() throws TranFailException, Exception {
        try {
            // 不需要重新 查询，只需要从session中取下一段数据即可。
            if (turnPage.getQueryFlag() != null
                    && turnPage.getQueryFlag().equals("old")) {
                List list = (ArrayList) session.get("pjbfddmlList");
                turnPage.setTurnPageInfo(list.size());// 设置翻页信息
                if (submit_type != null && submit_type.startsWith("ajax"))// ajax提交的
                {

                } else {
                    result = "/WEB-INF/pages/business/pjbfddml/pjbfddml_mng_list.jsp";
                }
                return SUCCESS;
            }

            if (queryOrder != null && queryOrder.equals("first")) {// 第一次查询，需要把查询条件保存到Session，以便在修改或删除返回后做重新查询
                Map conParaMap = new HashMap();
                conParaMap.put("pjbfddmlParam", pjbfddmlParam);
                conParaMap.put("turnPage", turnPage);

                // 查询条件保存到Session
                session.put("pjbfddmlConParas", conParaMap);
                session.remove("pjbfddmlOrderParas");
            } else if (queryOrder != null && queryOrder.equals("back")
                    || turnPage.getQueryFlag().equals("normal")) {// 如果从修改或删除页面进行重新查询，或按某一字段排序查询，需要从session中获取查询条件

                Map conParaMap = (HashMap) session.get("pjbfddmlConParas");
                pjbfddmlParam = (PjbfddmlParam) conParaMap.get("pjbfddmlParam");
                if (orderField != null && !orderField.trim().equals("")) {// 按某一字段排序查询
                    Map orderParaMap = new HashMap();
                    orderParaMap.put("orderField", orderField);
                    orderParaMap.put("orderTrend", orderTrend);
                    session.put("pjbfddmlOrderParas", orderParaMap);
                } else if (queryOrder != null && queryOrder.equals("back")) {// 从修改或删除页面进行重新查询
                    int needDispPage = turnPage.getNeedDispPage();
                    turnPage = (TurnPage) conParaMap.get("turnPage");
                    // 需要重设needDispPage,以返回到上次修改或删除入口
                    turnPage.setNeedDispPage(needDispPage);
                }
            }
            // 当不输入任何条件时，gzxx为空，gzxx为空处理
            if (pjbfddmlParam == null) {
                pjbfddmlParam = new PjbfddmlParam();
            }
            HashMap orderParaMap = (HashMap) session.get("pjbfddmlOrderParas");

            if ((orderField == null || orderField.trim().equals(""))
                    && orderParaMap != null) {
                orderField = (String) orderParaMap.get("orderField");
            }
            if ((orderTrend == null || orderTrend.trim().equals(""))
                    && orderParaMap != null) {
                orderTrend = (String) orderParaMap.get("orderTrend");
            }

            // 获取故障现象信息列表，并实现翻页
            Map retMap = pjbfddmlService.getList(pjbfddmlParam,
                    turnPage.getLinesPerPage(), turnPage.getPagesPerQuery(),
                    turnPage.getNeedDispPage(), turnPage.getTotalLinesNum(),
                    turnPage.getQueryFlag(), orderField, orderTrend);

            pjbfddmlList = (ArrayList) retMap.get("queryList");
            if (turnPage.getQueryFlag().equals("new")) {// 如果是一次新的查询或从删除和修改返回的查询，需要重新统计；对于从删除和修改返回的查询，queryFlag=new
                turnPage.setTotalLinesNum(((TurnPage) retMap.get("turnPage"))
                        .getTotalLinesNum());
                turnPage.setNeedDispPage((((TurnPage) retMap.get("turnPage"))
                        .getNeedDispPage()));
            }
            turnPage.setTurnPageInfo(pjbfddmlList.size());// 设置翻页信息

            // 如果需要重新查询，要把查询结果保存到session中
            if (turnPage.getQueryFlag().equals("new")
                    || turnPage.getQueryFlag().equals("normal")) {
                session.put("pjbfddmlList", pjbfddmlList);
            }

            if (submit_type != null && submit_type.startsWith("ajax")) {// ajax提交的
            } else {
                result = "/WEB-INF/pages/business/pjbfddml/pjbfddml_mng_list.jsp";
            }
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(),
                    te.getDisplayMessage(), te.getErrorLocation(),
                    te.getErrorMsg());
            throw te;
        } catch (Exception e) {
            log.error(
                    "获取 整车物料库列表交易时出错！ParaVal="
                            + tools.replaceNullString(pjbfddmlParam.getZyh() != null ? pjbfddmlParam
                            .getZyh() : ""), e);
            throw e;
        }
        return SUCCESS;
    }

    /**
     * 选择备件时用到的查询，并实现翻页功能。同时对该Action的Session进行处理
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public String queryBjkcBzk() throws TranFailException, Exception {
        try {
            // 不需要重新 查询，只需要从session中取下一段数据即可。
            if (turnPage.getQueryFlag() != null
                    && turnPage.getQueryFlag().equals("old")) {
                List<BjkcBzk> list = (ArrayList) session.get("bjkcBzkList");// 第一次查的时候已经把pzjgList放到session中。
                turnPage.setTurnPageInfo(list.size());// 设置翻页信息
                if (submit_type != null && submit_type.startsWith("ajax"))// ajax提交的
                {
                } else {
                    result = "/WEB-INF/pages/business/pjbfddml/bjkc_bzk_choose_mng_list.jsp";
                }
                return SUCCESS;
            }

            if (queryOrder != null && queryOrder.equals("first")) {// 第一次查询，需要把查询条件保存到Session，以便在修改或删除返回后做重新查询
                Map conParaMap = new HashMap();
                conParaMap.put("bjkcBzkParam", bjkcBzkParam);
                conParaMap.put("turnPage", turnPage);

                // 查询条件保存到Session
                session.put("bjkcBzkConParas", conParaMap);
                session.remove("bjkcBzkOrderParas");
            } else if (queryOrder != null && queryOrder.equals("back")
                    || turnPage.getQueryFlag().equals("normal")) {// 如果从修改或删除页面进行重新查询，或按某一字段排序查询，需要从session中获取查询条件

                Map conParaMap = (HashMap) session.get("bjkcBzkConParas");
                bjkcBzkParam = (BjkcBzkParam) conParaMap.get("bjkcBzkParam");
                if (orderField != null && !orderField.trim().equals("")) {// 按某一字段排序查询
                    Map orderParaMap = new HashMap();
                    orderParaMap.put("orderField", orderField);
                    orderParaMap.put("orderTrend", orderTrend);
                    session.put("bjkcBzkOrderParas", orderParaMap);
                } else if (queryOrder != null && queryOrder.equals("back")) {// 从修改或删除页面进行重新查询
                    int needDispPage = turnPage.getNeedDispPage();
                    turnPage = (TurnPage) conParaMap.get("turnPage");
                    // 需要重设needDispPage,以返回到上次修改或删除入口
                    turnPage.setNeedDispPage(needDispPage);
                }
            }
            // 当不输入任何条件时，gzxx为空，gzxx为空处理
            if (bjkcBzkParam == null) {
                bjkcBzkParam = new BjkcBzkParam();
            }
            HashMap orderParaMap = (HashMap) session.get("bjkcBzkOrderParas");

            if ((orderField == null || orderField.trim().equals(""))
                    && orderParaMap != null) {
                orderField = (String) orderParaMap.get("orderField");
            }
            if ((orderTrend == null || orderTrend.trim().equals(""))
                    && orderParaMap != null) {
                orderTrend = (String) orderParaMap.get("orderTrend");
            }

            // 获取故障现象信息列表，并实现翻页
            Map retMap = pjbfddmlService.getBjkcBzkList(bjkcBzkParam,
                    turnPage.getLinesPerPage(), turnPage.getPagesPerQuery(),
                    turnPage.getNeedDispPage(), turnPage.getTotalLinesNum(),
                    turnPage.getQueryFlag(), orderField, orderTrend);

            bjkcBzkList = (ArrayList) retMap.get("queryList");
            if (turnPage.getQueryFlag().equals("new")) {// 如果是一次新的查询或从删除和修改返回的查询，需要重新统计；对于从删除和修改返回的查询，queryFlag=new
                turnPage.setTotalLinesNum(((TurnPage) retMap.get("turnPage"))
                        .getTotalLinesNum());
                turnPage.setNeedDispPage((((TurnPage) retMap.get("turnPage"))
                        .getNeedDispPage()));
            }
            turnPage.setTurnPageInfo(bjkcBzkList.size());// 设置翻页信息

            // 如果需要重新查询，要把查询结果保存到session中
            if (turnPage.getQueryFlag().equals("new")
                    || turnPage.getQueryFlag().equals("normal")) {
                session.put("bjkcBzkList", bjkcBzkList);
            }

            if (submit_type != null && submit_type.startsWith("ajax")) {// ajax提交的
            }
            {
                result = "/WEB-INF/pages/business/pjbfddml/bjkc_bzk_choose_mng_list.jsp";
            }
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(),
                    te.getDisplayMessage(), te.getErrorLocation(),
                    te.getErrorMsg());
            throw te;
        } catch (Exception e) {
            log.error(
                    "获取备件时出错！ParaVal="
                            + tools.replaceNullString(bjkcBzkParam.getCkh() != null ? bjkcBzkParam
                            .getCkh() : "")
                            + "|"
                            + tools.replaceNullString(bjkcBzkParam.getClh() != null ? bjkcBzkParam
                            .getClh() : ""), e);
            throw e;
        }
        return SUCCESS;
    }

    //Getter/Setter
    public String getBaseWebPath() {
        return baseWebPath;
    }

    public void setBaseWebPath(String baseWebPath) {
        this.baseWebPath = baseWebPath;
    }

    public Bjkc getBjkc() {
        return bjkc;
    }

    public void setBjkc(Bjkc bjkc) {
        this.bjkc = bjkc;
    }

    public BjkcBzk getBjkcBzk() {
        return bjkcBzk;
    }

    public void setBjkcBzk(BjkcBzk bjkcBzk) {
        this.bjkcBzk = bjkcBzk;
    }

    public List<BjkcBzk> getBjkcBzkList() {
        return bjkcBzkList;
    }

    public void setBjkcBzkList(List<BjkcBzk> bjkcBzkList) {
        this.bjkcBzkList = bjkcBzkList;
    }

    public BjkcBzkParam getBjkcBzkParam() {
        return bjkcBzkParam;
    }

    public void setBjkcBzkParam(BjkcBzkParam bjkcBzkParam) {
        this.bjkcBzkParam = bjkcBzkParam;
    }

    public BjkcService getBjkcService() {
        return bjkcService;
    }

    public void setBjkcService(BjkcService bjkcService) {
        this.bjkcService = bjkcService;
    }

    public Map getErrorMap() {
        return errorMap;
    }

    public void setErrorMap(Map errorMap) {
        this.errorMap = errorMap;
    }

    public String getModule_str() {
        return module_str;
    }

    public void setModule_str(String module_str) {
        this.module_str = module_str;
    }

    public Map getOkMap() {
        return okMap;
    }

    public void setOkMap(Map okMap) {
        this.okMap = okMap;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderTrend() {
        return orderTrend;
    }

    public void setOrderTrend(String orderTrend) {
        this.orderTrend = orderTrend;
    }

    public Pjbfddml getPjbfddml() {
        return pjbfddml;
    }

    public void setPjbfddml(Pjbfddml pjbfddml) {
        this.pjbfddml = pjbfddml;
    }

    public List<Pjbfddml> getPjbfddmlList() {
        return pjbfddmlList;
    }

    public void setPjbfddmlList(List<Pjbfddml> pjbfddmlList) {
        this.pjbfddmlList = pjbfddmlList;
    }

    public PjbfddmlParam getPjbfddmlParam() {
        return pjbfddmlParam;
    }

    public void setPjbfddmlParam(PjbfddmlParam pjbfddmlParam) {
        this.pjbfddmlParam = pjbfddmlParam;
    }

    public PjbfddmlService getPjbfddmlService() {
        return pjbfddmlService;
    }

    public void setPjbfddmlService(PjbfddmlService pjbfddmlService) {
        this.pjbfddmlService = pjbfddmlService;
    }

    public Pjbfddnr getPjbfddnr() {
        return pjbfddnr;
    }

    public void setPjbfddnr(Pjbfddnr pjbfddnr) {
        this.pjbfddnr = pjbfddnr;
    }

    public String getQueryOrder() {
        return queryOrder;
    }

    public void setQueryOrder(String queryOrder) {
        this.queryOrder = queryOrder;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRowflag_modify() {
        return rowflag_modify;
    }

    public void setRowflag_modify(String rowflag_modify) {
        this.rowflag_modify = rowflag_modify;
    }

    public String getRowflag_str() {
        return rowflag_str;
    }

    public void setRowflag_str(String rowflag_str) {
        this.rowflag_str = rowflag_str;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public String getSubmit_type() {
        return submit_type;
    }

    public void setSubmit_type(String submit_type) {
        this.submit_type = submit_type;
    }

    public TurnPage getTurnPage() {
        return turnPage;
    }

    public void setTurnPage(TurnPage turnPage) {
        this.turnPage = turnPage;
    }

    public List<Pjbfddml> getCkhckjcList() {
        return ckhckjcList;
    }

    public void setCkhckjcList(List<Pjbfddml> ckhckjcList) {
        this.ckhckjcList = ckhckjcList;
    }
}