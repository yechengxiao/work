package com.pub.pzjg;

import com.opensymphony.xwork2.ActionContext;
import com.pub.pzjg.abstractClass.AbstractAction;
import com.pub.util.ErrorHandleUtil;
import com.pub.util.TranFailException;
import com.pub.util.TurnPage;
import com.pub.util.tools;

import java.util.*;

public class PzjgAction extends AbstractAction {

    private String submit_type;// 用于标识ajax提交或表单提交
    private String module_str;// 其他模块中可能调用
    //以下变量不同
    private PzjgService pzjgService; // Spring DI
    private Pzjg pzjg;// 表单直接提交对象，如pzjg.vsn
    private PzjgParam pzjgParam;//查询参数
    private List<Pzjg> pzjgList;// 返回查询结果列表
    // 下面3个变量，用于树_下拉菜单实现
    private List<String> cxList = new ArrayList();
    private List<String> xhmsList = new ArrayList();
    private List<String> clysList = new ArrayList();

    //静态块一般用于初始化类中的静态成员；而非静态块一般用于初始化类中的非静态成员；
    //需要在对象实例化时才调用非静态块
    {
        System.out.println("初始内容。");
        okMap = new HashMap();
        errorMap = new HashMap();
    }

    public List<String> getClysList() {
        return clysList;
    }

    public void setClysList(List<String> clysList) {
        this.clysList = clysList;
    }

    public List<String> getCxList() {
        return cxList;
    }

    public void setCxList(List<String> cxList) {
        this.cxList = cxList;
    }

    public String getModule_str() {
        return module_str;
    }

    public void setModule_str(String module_str) {
        this.module_str = module_str;
    }

    public Pzjg getPzjg() {
        return pzjg;
    }

    public void setPzjg(Pzjg pzjg) {
        this.pzjg = pzjg;
    }

    public List<Pzjg> getPzjgList() {
        return pzjgList;
    }

    public void setPzjgList(List<Pzjg> pzjgList) {
        this.pzjgList = pzjgList;
    }

    public PzjgParam getPzjgParam() {
        return pzjgParam;
    }

    public void setPzjgParam(PzjgParam pzjgParam) {
        this.pzjgParam = pzjgParam;
    }

    public PzjgService getPzjgService() {
        return pzjgService;
    }

    public void setPzjgService(PzjgService pzjgService) {
        this.pzjgService = pzjgService;
    }

    public String getSubmit_type() {
        return submit_type;
    }

    public void setSubmit_type(String submit_type) {
        this.submit_type = submit_type;
    }

    public List<String> getXhmsList() {
        return xhmsList;
    }

    public void setXhmsList(List<String> xhmsList) {
        this.xhmsList = xhmsList;
    }

    /**
     * 下面是方法主体，上面是setter/getter
     */
    @Override
    public String dispatchMainPage() throws Exception {
        try {
            initPzjgStr();// 预读车系、车型描述、车辆颜色
            // 获取session
            Map session = ActionContext.getContext().getSession();
            if (session != null) {
                session.remove("pzjgConParas");
            }
            result = "/WEB-INF/pages/pub/pzjg/pzjg_mng_list.jsp";
        } catch (Exception e) {
            try {
                log.error("转到 整车物料库维护首页时出错！ParaVal=", e);
            } catch (Exception ex) {
            }
            throw e;
        }
        return SUCCESS;
    }

    @Override
    public String dispatchFwzMainPage() throws Exception {
        try {
            ActionContext ctx1 = ActionContext.getContext();
            // 获取session
            Map session = ctx1.getSession();
            if (session != null) {
                session.remove("pzjgConParas");
            }
            initPzjgStr();
            result = "/WEB-INF/pages/pub/pzjg/fwz_pzjg_mng_list.jsp";
        } catch (Exception e) {
            try {
                log.error("转到服务站整车物料库首页时出错！ParaVal=", e);
            } catch (Exception ex) {
            }
            throw e;
        }
        return SUCCESS;

    }

    @Override
    public String turnAddPage() throws TranFailException, Exception {
        try {
            result = "/WEB-INF/pages/pub/pzjg/pzjg_mng_add_input.jsp";
        } catch (Exception e) {
            log.error("跳转到 整车物料库信息新增页面出错！", e);
            throw e;
        }
        return SUCCESS;
    }

    @Override
    public String turnModifyPage() throws TranFailException, Exception {
        try {
            pzjg = pzjgService.getById(pzjg.getVsn());
            result = "/WEB-INF/pages/pub/pzjg/pzjg_mng_modify_input.jsp";
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(),
                    te.getDisplayMessage(), te.getErrorLocation(),
                    te.getErrorMsg());
            throw te;
        } catch (Exception e) {
            log.error("查询 整车物料库信息出错！ParaVal="
                    + tools.replaceNullString(pzjg.getVsn() != null ? pzjg.getVsn() : ""), e);
            throw e;
        }
        return SUCCESS;
    }

    @Override
    public String turnViewPage() throws TranFailException, Exception {
        try {
            pzjg = pzjgService.getById(pzjg.getVsn());
            result = "/WEB-INF/pages/pub/pzjg/fwz_pzjg_mng_view.jsp";
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(),
                    te.getDisplayMessage(), te.getErrorLocation(),
                    te.getErrorMsg());
            throw te;
        } catch (Exception e) {
            log.error("服务站端查询材料库信息出错！ParaVal="
                    + tools.replaceNullString(pzjg.getVsn() != null ? pzjg.getVsn() : ""), e);
            throw e;
        }
        return SUCCESS;
    }

    @Override
    public String add() throws TranFailException, Exception {
        try {
            // 判断主键是否重复
            Pzjg pzjg_old = pzjgService.getById(pzjg.getVsn());
            pzjgService.getById(pzjg.getVsn());
            if (pzjg_old != null) {
                errorMap = ErrorHandleUtil.setExceptionInfo("整车物料库新建出错",
                        "整车物料库代码重复，请重试！", "故障模式", null);
                return ERROR;
            }

            // 新增故障现象信息
            pzjgService.add(pzjg);

            okMap.put("okTitle", "操作成功");
            okMap.put("okMsg", " 整车物料库信息新增成功！");
            // 封装返回OK页面的参数
            Vector v = new Vector();
            String[] para1 = {"queryOrder", null};
            String[] para2 = {"turnPage.needDispPage", "1"};
            v.add(para1);
            v.add(para2);

            okMap.put("okParameters", v);
            okMap.put("okReturn", baseWebPath + "/pzjg/queryListAction.action");
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
                    "新增 整车物料库信息交易出错！ParaVal="
                            + tools.replaceNullString(pzjg.getVsn()) != null ? pzjg
                            .getVsn() : "", e);
            throw e;
        }
        return SUCCESS;
    }

    @Override
    public String update() throws TranFailException, Exception {
        try {
            // 修改 整车物料库信息
            pzjgService.update(pzjg);

            okMap.put("okTitle", "操作成功");
            okMap.put("okMsg", " 整车物料库信息修改成功！");
            // 封装返回OK页面的参数

            Vector v = new Vector();
            String[] para1 = {"queryOrder", "back"};
            String[] para2 = {"turnPage.needDispPage",
                    turnPage.getNeedDispPage() + ""};
            v.add(para1);
            v.add(para2);

            okMap.put("okParameters", v);
            okMap.put("okReturn", baseWebPath + "/pzjg/queryListAction.action");

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
                    "修改 整车物料库信息交易出错！ParaVal="
                            + tools.replaceNullString(pzjg.getVsn() != null ? pzjg
                            .getVsn() : ""), e);
            throw e;
        }
        return SUCCESS;
    }

    @Override
    public String delete() throws TranFailException, Exception {
        try {
            pzjgService.delete(rowflag_str);
            okMap.put("okTitle", "操作成功");
            okMap.put("okMsg", " 整车物料库信息删除成功！");

            // 封装返回OK页面的参数
            Vector v = new Vector();
            String[] para1 = {"queryOrder", "back"};
            String[] para2 = {"turnPage.needDispPage", turnPage.getNeedDispPage() + ""};
            v.add(para1);
            v.add(para2);

            okMap.put("okParameters", v);
            okMap.put("okReturn", baseWebPath + "/pzjg/queryListAction.action");
            result = "/ok.jsp";
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(),
                    te.getDisplayMessage(), te.getErrorLocation(), te.getErrorMsg());
            throw te;
        } catch (Exception e) {
            log.error("删除 整车物料库信息出错！ParaVal=" + rowflag_str, e);
            throw e;
        }
        return SUCCESS;
    }

    @Override
    public String queryList() throws TranFailException, Exception {
        try {
            initPzjgStr();// 预读车系、车型描述、车辆颜色
            ActionContext ctx1 = ActionContext.getContext();
            // 获取session
            Map session = ctx1.getSession();

            module_str = tools.replaceNullString(module_str);

            // 不需要重新 查询，只需要从session中取下一段数据即可。
            if (turnPage.getQueryFlag() != null
                    && turnPage.getQueryFlag().equals("old")) {
                List list = (ArrayList) session.get("pzjgList");
                turnPage.setTurnPageInfo(list.size());// 设置翻页信息
                if (submit_type != null && submit_type.startsWith("ajax"))// ajax提交的
                {
                    // 2013-06-07 骆丰
                    result = "/WEB-INF/pages/business/xsddml/xsddml_mng_pzjgchoose.jsp";
                } else {
                    if (module_str.equals("pub")) {
                        // result
                        // ="/WEB-INF/pages/pub/pzjg/pzjg_mng_choose.jsp";
                    } else if (module_str.equals("fwz")) {
                        result = "/WEB-INF/pages/pub/pzjg/fwz_pzjg_mng_list.jsp";
                    } else {
                        result = "/WEB-INF/pages/pub/pzjg/pzjg_mng_list.jsp";
                    }
                }
                return SUCCESS;
            }

            if (queryOrder != null && queryOrder.equals("first")) {// 第一次查询，需要把查询条件保存到Session，以便在修改或删除返回后做重新查询
                Map conParaMap = new HashMap();
                conParaMap.put("pzjgParam", pzjgParam);
                conParaMap.put("turnPage", turnPage);

                // 查询条件保存到Session
                session.put("pzjgConParas", conParaMap);
                session.remove("pzjgOrderParas");
            } else if (queryOrder != null && queryOrder.equals("back")
                    || turnPage.getQueryFlag().equals("normal")) {// 如果从修改或删除页面进行重新查询，或按某一字段排序查询，需要从session中获取查询条件

                Map conParaMap = (HashMap) session.get("pzjgConParas");
                pzjgParam = (PzjgParam) conParaMap.get("pzjgParam");
                if (orderField != null && !orderField.trim().equals("")) {// 按某一字段排序查询
                    Map orderParaMap = new HashMap();
                    orderParaMap.put("orderField", orderField);
                    orderParaMap.put("orderTrend", orderTrend);
                    session.put("pzjgOrderParas", orderParaMap);
                } else if (queryOrder != null && queryOrder.equals("back")) {// 从修改或删除页面进行重新查询
                    int needDispPage = turnPage.getNeedDispPage();
                    turnPage = (TurnPage) conParaMap.get("turnPage");
                    // 需要重设needDispPage,以返回到上次修改或删除入口
                    turnPage.setNeedDispPage(needDispPage);
                }
            }
            // 当不输入任何条件时，gzxx为空，gzxx为空处理
            if (pzjgParam == null) {
                pzjgParam = new PzjgParam();
            }
            HashMap orderParaMap = (HashMap) session.get("pzjgOrderParas");

            if ((orderField == null || orderField.trim().equals(""))
                    && orderParaMap != null) {
                orderField = (String) orderParaMap.get("orderField");
            }
            if ((orderTrend == null || orderTrend.trim().equals(""))
                    && orderParaMap != null) {
                orderTrend = (String) orderParaMap.get("orderTrend");
            }

            // 获取故障现象信息列表，并实现翻页
            Map retMap = pzjgService.getList(pzjgParam,
                    turnPage.getLinesPerPage(), turnPage.getPagesPerQuery(),
                    turnPage.getNeedDispPage(), turnPage.getTotalLinesNum(),
                    turnPage.getQueryFlag(), orderField, orderTrend);

            pzjgList = (ArrayList) retMap.get("queryList");
            if (turnPage.getQueryFlag().equals("new")) {// 如果是一次新的查询或从删除和修改返回的查询，需要重新统计；对于从删除和修改返回的查询，queryFlag=new
                turnPage.setTotalLinesNum(((TurnPage) retMap.get("turnPage"))
                        .getTotalLinesNum());
                turnPage.setNeedDispPage((((TurnPage) retMap.get("turnPage"))
                        .getNeedDispPage()));
            }
            turnPage.setTurnPageInfo(pzjgList.size());// 设置翻页信息

            // 如果需要重新查询，要把查询结果保存到session中
            if (turnPage.getQueryFlag().equals("new")
                    || turnPage.getQueryFlag().equals("normal")) {
                session.put("pzjgList", pzjgList);
            }

            if (submit_type != null && submit_type.startsWith("ajax"))// ajax提交的
                // 2013-06-07 骆丰
                result = "/WEB-INF/pages/business/xsddml/xsddml_mng_pzjgchoose.jsp";
            else {
                if (module_str.equals("pub"))
                    result = "/WEB-INF/pages/pub/pzjg/pzjg_mng_choose.jsp";
                else if (module_str.equals("fwz"))
                    result = "/WEB-INF/pages/pub/pzjg/fwz_pzjg_mng_list.jsp";
                else
                    result = "/WEB-INF/pages/pub/pzjg/pzjg_mng_list.jsp";
            }
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(),
                    te.getDisplayMessage(), te.getErrorLocation(),
                    te.getErrorMsg());
            throw te;
        } catch (Exception e) {
            log.error(
                    "获取 整车物料库列表交易时出错！ParaVal="
                            + tools.replaceNullString(pzjgParam.getVsn() != null ? pzjgParam
                            .getVsn() : "")
                            + "|"
                            + tools.replaceNullString(pzjgParam.getCx() != null ? pzjgParam
                            .getCx() : "")
                            + "|"
                            + tools.replaceNullString(pzjgParam.getClys() != null ? pzjgParam
                            .getClys() : "")
                            + "|"
                            + tools.replaceNullString(pzjgParam.getXhms() != null ? pzjgParam
                            .getXhms() : "")
                            + "|"
                            + tools.replaceNullString(pzjgParam.getCxfl() != null ? pzjgParam
                            .getCxfl() : "")
                            + "|"
                            + tools.replaceNullString(pzjgParam.getPzlx() != null ? pzjgParam
                            .getPzlx() : ""), e);
            throw e;
        }
        return SUCCESS;
    }

    /**
     * 预读取车系、车系描述、车辆颜色的数据
     *
     * @throws TranFailException
     * @throws Exception
     */
    private void initPzjgStr() throws TranFailException, Exception {
        try {
            Map<String, List<String>> map = pzjgService.getPzjgString(new PzjgParam());
            setCxList(map.get("cxList"));
            setXhmsList(map.get("xhmsList"));
            setClysList(map.get("clysList"));
        } catch (TranFailException e) {
            e.printStackTrace();
        }
    }
}