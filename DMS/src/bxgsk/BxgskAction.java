package com.pub.bxgsk;

import com.opensymphony.xwork2.ActionContext;
import com.pub.util.ErrorHandleUtil;
import com.pub.util.TranFailException;
import com.pub.util.TurnPage;
import com.pub.util.tools;

import java.util.*;

public class BxgskAction extends AbstractAction {
    public String dispatchMainPage() throws Exception {
        try {
            ActionContext ctx1 = ActionContext.getContext();
            Map session = ctx1.getSession();
            if (session != null) {
                session.remove("bxgskConParas");
                session.remove("bxgskList");
            }
            result = "/WEB-INF/pages/pub/bxgsk/bxgsk_mng_list.jsp";
            return SUCCESS;
        } catch (Exception e) {
            log.error("转到 " + LOG_MODULE_NAME + " 首页时出错！", e);
            throw e;
        }
    }

    public String dispatchFwzMainPage() throws Exception {
        try {
            // 获取session
            ActionContext ctx1 = ActionContext.getContext();
            Map session = ctx1.getSession();
            if (session != null) {
                session.remove("bxgskConParas");
                session.remove("bxgskList");
            }
            result = "/WEB-INF/pages/pub/bxgsk/fwz_bxgsk_mng_list.jsp";
            return SUCCESS;
        } catch (Exception e) {
            log.error("转到 " + LOG_MODULE_NAME + " 首页时出错！", e);
            throw e;
        }
    }

    public String turnAddPage() throws TranFailException, Exception {
        try {
            result = "/WEB-INF/pages/pub/bxgsk/bxgsk_mng_add.jsp";
            return SUCCESS;
        } catch (Exception e) {
            log.error("转到 " + LOG_MODULE_NAME + " 新增页面出错！", e);
            throw e;
        }
    }

    public String turnModifyViewPage() throws TranFailException, Exception {
        try {
            bxgsk = bxgskService.getById(bxgsk.getBxgsdm());
            result = "/WEB-INF/pages/pub/bxgsk/bxgsk_mng_modify_view.jsp";
            return SUCCESS;
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(), te.getDisplayMessage(), te.getErrorLocation(), te.getErrorMsg());
            throw te;
        } catch (Exception e) {
            log.error("转到 " + LOG_MODULE_NAME + " 修改/查看信息时出错！", e);
            throw e;
        }
    }

    public String turnViewPage() throws TranFailException, Exception {
        try {
            bxgsk = bxgskService.getById(bxgsk.getBxgsdm());
            result = "/WEB-INF/pages/pub/bxgsk/fwz_bxgsk_mng_view.jsp";
            return SUCCESS;
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(), te.getDisplayMessage(), te.getErrorLocation(), te.getErrorMsg());
            throw te;
        } catch (Exception e) {
            log.error("转到 " + LOG_MODULE_NAME + " 修改/查看信息时出错！", e);
            throw e;
        }
    }

    public String add() throws TranFailException, Exception {
        try {
            if (null != bxgskService.getById(bxgsk.getBxgsdm())) {
                result = "/WEB-INF/error.jsp";
            }

            bxgskService.add(bxgsk);

            okMap.put("okTitle", "操作成功");
            okMap.put("okMsg", "新增成功！");
            // 封装返回OK页面的参数
            Vector v = new Vector();
            String[] para1 = {"queryOrder", null};
            String[] para2 = {"turnPage.needDispPage", "1"};
            v.add(para1);
            v.add(para2);

            okMap.put("okParameters", v);
            okMap.put("okReturn", baseWebPath + "/bxgsk/queryListAction.action");
            okMap.put("returnType", "close_parent_flush");// 参数returnType=close_parent_flush,表示在关闭对话框时更新父窗口页面

            result = "/ok.jsp";
            return SUCCESS;
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(), te.getDisplayMessage(), te.getErrorLocation(), te.getErrorMsg());
            // 如果是模式对话框操作报错，则需要在errorMap中增加变量winType
            errorMap.put("winType", "dialogue");
            throw te;
        } catch (Exception e) {
            // 如果是模式对话框操作报错，则需要在errorMap中增加变量winType
            errorMap.put("winType", "dialogue");
            log.error("新增 " + LOG_MODULE_NAME + " 出错!", e);
            throw e;
        }
    }

    public String delete() throws TranFailException, Exception {
        try {
            bxgskService.delete(rowflag_str);
            okMap.put("okTitle", "操作成功");
            okMap.put("okMsg", LOG_MODULE_NAME + "删除成功！");

            // 封装返回OK页面的参数
            Vector v = new Vector();
            String[] para1 = {"queryOrder", "back"};

            String[] para2 = {"turnPage.needDispPage", turnPage.getNeedDispPage() + ""};
            v.add(para1);
            v.add(para2);

            okMap.put("okParameters", v);
            okMap.put("okReturn", baseWebPath + "/bxgsk/queryListAction.action");
            result = "/ok.jsp";
            return SUCCESS;
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(), te.getDisplayMessage(), te.getErrorLocation(), te.getErrorMsg());
            throw te;
        } catch (Exception e) {
            log.error("删除" + LOG_MODULE_NAME + " 信息出错！ParaVal=" + rowflag_str, e);
            throw e;
        }
    }

    public String update() throws TranFailException, Exception {
        try {
            bxgskService.update(bxgsk);

            okMap.put("okTitle", "操作成功");
            okMap.put("okMsg", "确认成功！");
            // 封装返回OK页面的参数

            Vector v = new Vector();
            String[] para1 = {"queryOrder", "back"};
            String[] para2 = {"turnPage.needDispPage",
                    turnPage.getNeedDispPage() + ""};
            v.add(para1);
            v.add(para2);

            okMap.put("okParameters", v);
            okMap.put("okReturn", baseWebPath + "/bxgsk/queryListAction.action");

            okMap.put("returnType", "close_parent_flush");// 参数returnType=close_parent_flush,表示在关闭对话框时更新父窗口页面
            result = "/ok.jsp";
            return SUCCESS;
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(), te.getDisplayMessage(), te.getErrorLocation(), te.getErrorMsg());
            // 如果是模式对话框操作报错，则需要在errorMap中增加变量winType
            errorMap.put("winType", "dialogue");
            throw te;
        } catch (Exception e) {
            // 如果是模式对话框操作报错，则需要在errorMap中增加变量winType
            errorMap.put("winType", "dialogue");
            log.error("修改 " + LOG_MODULE_NAME + " 出错！", e);
            throw e;
        }
    }

    public String queryList() throws TranFailException, Exception {
        try {
            module_name = tools.replaceNullString(module_name);

            // 不需要重新 查询，只需要从session中取下一段数据即可。
            if (turnPage.getQueryFlag() != null
                    && turnPage.getQueryFlag().equals("old")) {
                List list = (ArrayList) session.get("bxgskList");
                turnPage.setTurnPageInfo(list.size());// 设置翻页信息

                if (module_name.equalsIgnoreCase("fwz")) {
                    result = "/WEB-INF/pages/pub/bxgsk/fwz_bxgsk_mng_list.jsp";
                } else {
                    result = "/WEB-INF/pages/pub/bxgsk/bxgsk_mng_list.jsp";
                }
                return SUCCESS;
            }

            if (queryOrder != null && queryOrder.equals("first")) {// 第一次查询，需要把查询条件保存到Session，以便在修改或删除返回后做重新查询
                Map conParaMap = new HashMap();
                conParaMap.put("bxgskParam", bxgskParam);
                conParaMap.put("turnPage", turnPage);

                // 查询条件保存到Session
                session.put("bxgskConParas", conParaMap);
                session.remove("bxgskOrderParas");
            } else if (queryOrder != null && queryOrder.equals("back")
                    || turnPage.getQueryFlag().equals("normal")) {// 如果从修改或删除页面进行重新查询，或按某一字段排序查询，需要从session中获取查询条件

                Map conParaMap = (HashMap) session.get("bxgskConParas");
                bxgskParam = (BxgskParam) conParaMap.get("bxgskParam");
                if (orderField != null && !orderField.trim().equals("")) {// 按某一字段排序查询
                    Map orderParaMap = new HashMap();
                    orderParaMap.put("orderField", orderField);
                    orderParaMap.put("orderTrend", orderTrend);
                    session.put("bxgskOrderParas", orderParaMap);
                } else if (queryOrder != null && queryOrder.equals("back")) {// 从修改或删除页面进行重新查询
                    int needDispPage = turnPage.getNeedDispPage();
                    turnPage = (TurnPage) conParaMap.get("turnPage");
                    // 需要重设needDispPage,以返回到上次修改或删除入口
                    turnPage.setNeedDispPage(needDispPage);
                }
            }
            // 当不输入任何条件时，gzxx为空，gzxx为空处理
            if (null == bxgskParam) {
                bxgskParam = new BxgskParam();
            }
            HashMap orderParaMap = (HashMap) session.get("bxgskOrderParas");

            if ((orderField == null || orderField.trim().equals(""))
                    && orderParaMap != null) {
                orderField = (String) orderParaMap.get("orderField");
            }
            if ((orderTrend == null || orderTrend.trim().equals(""))
                    && orderParaMap != null) {
                orderTrend = (String) orderParaMap.get("orderTrend");
            }

            // 获取故障现象信息列表，并实现翻页
            Map retMap = getBxgskService().getList(bxgskParam,
                    turnPage.getLinesPerPage(), turnPage.getPagesPerQuery(),
                    turnPage.getNeedDispPage(), turnPage.getTotalLinesNum(),
                    turnPage.getQueryFlag(), orderField, orderTrend);

            bxgskList = (ArrayList) retMap.get("queryList");
            if (turnPage.getQueryFlag().equals("new")) {// 如果是一次新的查询或从删除和修改返回的查询，需要重新统计；对于从删除和修改返回的查询，queryFlag=new
                turnPage.setTotalLinesNum(((TurnPage) retMap.get("turnPage"))
                        .getTotalLinesNum());
                turnPage.setNeedDispPage((((TurnPage) retMap.get("turnPage"))
                        .getNeedDispPage()));
            }
            turnPage.setTurnPageInfo(bxgskList.size());// 设置翻页信息

            // 如果需要重新查询，要把查询结果保存到session中
            if (turnPage.getQueryFlag().equals("new")
                    || turnPage.getQueryFlag().equals("normal")) {
                session.put("bxgskList", bxgskList);
            }
            if (module_name.equalsIgnoreCase("fwz")) {
                result = "/WEB-INF/pages/pub/bxgsk/fwz_bxgsk_mng_list.jsp";
            } else {
                result = "/WEB-INF/pages/pub/bxgsk/bxgsk_mng_list.jsp";
            }
            return SUCCESS;
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(), te.getDisplayMessage(), te.getErrorLocation(), te.getErrorMsg());
            throw te;
        } catch (Exception e) {
            log.error(
                    "获取" + LOG_MODULE_NAME + " 列表交易时出错！", e);
            throw e;
        }
    }


}
