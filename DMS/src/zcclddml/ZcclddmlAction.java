package com.business.zcclddml;

import com.pub.util.ErrorHandleUtil;
import com.pub.util.TranFailException;
import com.pub.util.TurnPage;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * User: ycx
 * Date: 13-8-31
 * Time: 下午2:37
 */
public class ZcclddmlAction extends AbstractAction {
    @Override
    public String dispatchMainPage() throws Exception {
        try {
            if (session != null) {
                session.remove("zcclddmlConParas");
                session.remove("zcclddmlList");
            }
            result = "/WEB-INF/pages/business/zcclddml/zcclddml_mng_list.jsp";
            return SUCCESS;
        } catch (Exception e) {
            log.error("转到 " + LOG_MODULE_NAME + " 首页时出错！", e);
            throw e;
        }
    }

    @Override
    public String turnAddPage() throws TranFailException, Exception {
        try {
            result = "/WEB-INF/pages/business/zcclddml/zcclddml_mng_add.jsp";
            return SUCCESS;
        } catch (Exception e) {
            log.error("转到 " + LOG_MODULE_NAME + " 新增页面出错！", e);
            throw e;
        }
    }

    @Override
    public String turnModifyViewPage() throws TranFailException, Exception {
        try {
            zcclddml = zcclddmlService.getById(zcclddml.getZyh());
            zcclddnrList = zcclddmlService.getZcclddnrList(zcclddml.getZyh());
            result = "/WEB-INF/pages/business/zcclddml/zcclddml_mng_modify_view.jsp";
            return SUCCESS;
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(), te.getDisplayMessage(), te.getErrorLocation(), te.getErrorMsg());
            throw te;
        } catch (Exception e) {
            log.error("转到 " + LOG_MODULE_NAME + " 修改/查看信息时出错！", e);
            throw e;
        }
    }

    /**
     * 生成流水号
     *
     * @return
     * @throws TranFailException
     */
    private String getZyh() throws TranFailException, Exception {
        try {
            Calendar now = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String rq = sdf.format(now.getTime());

            String zyh = pjbfddmlService.getNextBh("ZCCLDDML", rq, "");
            return zyh;
        } catch (TranFailException e) {
            throw e;
        }
    }

    @Override
    public String add() throws TranFailException, Exception {
        try {
            zcclddml.setZyh(getZyh());//从存储过程获取流水号
            zcclddmlService.add(zcclddml);

            okMap.put("okTitle", "操作成功");
            okMap.put("okMsg", "新增成功！");
            // 封装返回OK页面的参数
            Vector v = new Vector();
            String[] para1 = {"queryOrder", null};
            String[] para2 = {"turnPage.needDispPage", "1"};
            v.add(para1);
            v.add(para2);

            okMap.put("okParameters", v);
            okMap.put("okReturn", baseWebPath + "/zcclddml/queryListAction.action");
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

    @Override
    public String queryList() throws TranFailException, Exception {
        try {
            // 不需要重新 查询，只需要从session中取下一段数据即可。
            if (turnPage.getQueryFlag() != null
                    && turnPage.getQueryFlag().equals("old")) {
                List list = (ArrayList) session.get("zcclddmlList");
                turnPage.setTurnPageInfo(list.size());// 设置翻页信息
                result = "/WEB-INF/pages/business/zcclddml/zcclddml_mng_list.jsp";
                return SUCCESS;
            }

            if (queryOrder != null && queryOrder.equals("first")) {// 第一次查询，需要把查询条件保存到Session，以便在修改或删除返回后做重新查询
                Map conParaMap = new HashMap();
                conParaMap.put("zcclddmlParam", zcclddmlParam);
                conParaMap.put("turnPage", turnPage);

                // 查询条件保存到Session
                session.put("zcclddmlConParas", conParaMap);
                session.remove("zcclddmlOrderParas");
            } else if (queryOrder != null && queryOrder.equals("back")
                    || turnPage.getQueryFlag().equals("normal")) {// 如果从修改或删除页面进行重新查询，或按某一字段排序查询，需要从session中获取查询条件

                Map conParaMap = (HashMap) session.get("zcclddmlConParas");
                zcclddmlParam = (ZcclddmlParam) conParaMap.get("zcclddmlParam");
                if (orderField != null && !orderField.trim().equals("")) {// 按某一字段排序查询
                    Map orderParaMap = new HashMap();
                    orderParaMap.put("orderField", orderField);
                    orderParaMap.put("orderTrend", orderTrend);
                    session.put("zcclddmlOrderParas", orderParaMap);
                } else if (queryOrder != null && queryOrder.equals("back")) {// 从修改或删除页面进行重新查询
                    int needDispPage = turnPage.getNeedDispPage();
                    turnPage = (TurnPage) conParaMap.get("turnPage");
                    // 需要重设needDispPage,以返回到上次修改或删除入口
                    turnPage.setNeedDispPage(needDispPage);
                }
            }
            // 当不输入任何条件时，gzxx为空，gzxx为空处理
            if (zcclddmlParam == null) {
                zcclddmlParam = new ZcclddmlParam();
            }
            HashMap orderParaMap = (HashMap) session.get("zcclddmlOrderParas");

            if ((orderField == null || orderField.trim().equals(""))
                    && orderParaMap != null) {
                orderField = (String) orderParaMap.get("orderField");
            }
            if ((orderTrend == null || orderTrend.trim().equals(""))
                    && orderParaMap != null) {
                orderTrend = (String) orderParaMap.get("orderTrend");
            }

            // 获取故障现象信息列表，并实现翻页
            Map retMap = zcclddmlService.getList(zcclddmlParam,
                    turnPage.getLinesPerPage(), turnPage.getPagesPerQuery(),
                    turnPage.getNeedDispPage(), turnPage.getTotalLinesNum(),
                    turnPage.getQueryFlag(), orderField, orderTrend);

            zcclddmlList = (ArrayList) retMap.get("queryList");
            if (turnPage.getQueryFlag().equals("new")) {// 如果是一次新的查询或从删除和修改返回的查询，需要重新统计；对于从删除和修改返回的查询，queryFlag=new
                turnPage.setTotalLinesNum(((TurnPage) retMap.get("turnPage"))
                        .getTotalLinesNum());
                turnPage.setNeedDispPage((((TurnPage) retMap.get("turnPage"))
                        .getNeedDispPage()));
            }
            turnPage.setTurnPageInfo(zcclddmlList.size());// 设置翻页信息

            // 如果需要重新查询，要把查询结果保存到session中
            if (turnPage.getQueryFlag().equals("new")
                    || turnPage.getQueryFlag().equals("normal")) {
                session.put("zcclddmlList", zcclddmlList);
            }

            for (Zcclddml z : zcclddmlList) {
                System.out.println("--------------------getShr() " + z.getShr());
            }

            result = "/WEB-INF/pages/business/zcclddml/zcclddml_mng_list.jsp";
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

    @Override
    public String update() throws TranFailException, Exception {
        try {
            zcclddmlService.update(zcclddml);

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
            okMap.put("okReturn", baseWebPath + "/zcclddml/queryListAction.action");

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

    @Override
    public String delete() throws TranFailException, Exception {
        try {
            zcclddmlService.delete(rowflag_str);
            okMap.put("okTitle", "操作成功");
            okMap.put("okMsg", LOG_MODULE_NAME + "删除成功！");

            // 封装返回OK页面的参数
            Vector v = new Vector();
            String[] para1 = {"queryOrder", "back"};

            String[] para2 = {"turnPage.needDispPage", turnPage.getNeedDispPage() + ""};
            v.add(para1);
            v.add(para2);

            okMap.put("okParameters", v);
            okMap.put("okReturn", baseWebPath + "/zcclddml/queryListAction.action");
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

    /**
     * 打开设置页面
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public String turnSettingsPage() throws TranFailException, Exception {
        try {
            if (session != null) {
                session.remove("zcclConParas");
                session.remove("zcclList");
            }

            result = "/WEB-INF/pages/business/zcclddml/zcclddml_settings_mng_list.jsp";
            return SUCCESS;
        } catch (Exception e) {
            log.error("跳转到" + LOG_MODULE_NAME + " 设置页面出错！", e);
            throw e;
        }
    }

    /**
     * 打开zccl新增页面
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public String turnZcclAddPage() throws TranFailException, Exception {
        try {
            result = "/WEB-INF/pages/business/zcclddml/zcclddml_settings_mng_add.jsp";
            return SUCCESS;
        } catch (Exception e) {
            log.error("跳转到" + LOG_MODULE_NAME + " 设置 新增页面出错！", e);
            throw e;
        }
    }

    /**
     * zccl修改兼查看详情页面
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public String turnZcclModifyViewPage() throws TranFailException, Exception {
        try {
            zcclParam.setMclh(zcclParam.getMclh().replaceAll("\\s*", ""));
            Map map = zcclddmlService.getZcclList(zcclParam, 20, 20, 20, 20, "", "", "");
            zcclList = (List<Zccl>) map.get("queryList");
            result = "/WEB-INF/pages/business/zcclddml/zcclddml_settings_mng_modify_view.jsp";
            return SUCCESS;
        } catch (Exception e) {
            log.error("跳转到" + LOG_MODULE_NAME + " 设置 查看&修改页面出错！", e);
            throw e;
        }
    }

    public String addZccl() throws TranFailException, Exception {
        try {
            // 判断主键是否重复
            List<Zccl> zcclList = zcclddmlService.getZcclByMclh(zccl.getZcclPK().getMclh());
            if (null != zcclList && zcclList.size() > 0) {
                errorMap = ErrorHandleUtil.setExceptionInfo("新建出错", "总成号已存在，请重试！", "故障模式", null);
                return ERROR;
            }

            zcclddmlService.addZccl(zccl, rowflag_str);

            okMap.put("okTitle", "操作成功");
            okMap.put("okMsg", " 新增成功！");
            // 封装返回OK页面的参数
            Vector v = new Vector();
            String[] para1 = {"queryOrder", null};
            String[] para2 = {"turnPage.needDispPage", "1"};
            v.add(para1);
            v.add(para2);

            okMap.put("okParameters", v);
            okMap.put("okReturn", baseWebPath + "/zcclddml/queryZcclListAction.action");
            okMap.put("returnType", "close_parent_flush");// 参数returnType=close_parent_flush,表示在关闭对话框时更新父窗口页面

            result = "/ok.jsp";
            return SUCCESS;
        } catch (Exception e) {
            // 如果是模式对话框操作报错，则需要在errorMap中增加变量winType
            errorMap.put("winType", "dialogue");
            log.error("新增 " + LOG_MODULE_NAME + "设置 出错！", e);
            throw e;
        }
    }

    /**
     * 查询zccl
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public String queryZcclList() throws TranFailException, Exception {
        try {
            // 不需要重新 查询，只需要从session中取下一段数据即可。
            if (turnPage.getQueryFlag() != null
                    && turnPage.getQueryFlag().equals("old")) {
                List list = (ArrayList) session.get("zcclList");
                turnPage.setTurnPageInfo(list.size());// 设置翻页信息

                result = "/WEB-INF/pages/business/zcclddml/zcclddml_settings_mng_list.jsp";
                return SUCCESS;
            }

            if (queryOrder != null && queryOrder.equals("first")) {// 第一次查询，需要把查询条件保存到Session，以便在修改或删除返回后做重新查询
                Map conParaMap = new HashMap();
                conParaMap.put("zcclParam", zcclParam);
                conParaMap.put("turnPage", turnPage);

                // 查询条件保存到Session
                session.put("zcclConParas", conParaMap);
                session.remove("zcclOrderParas");
            } else if (queryOrder != null && queryOrder.equals("back")
                    || turnPage.getQueryFlag().equals("normal")) {// 如果从修改或删除页面进行重新查询，或按某一字段排序查询，需要从session中获取查询条件

                Map conParaMap = (HashMap) session.get("zcclConParas");
                zcclParam = (ZcclParam) conParaMap.get("zcclParam");
                if (orderField != null && !orderField.trim().equals("")) {// 按某一字段排序查询
                    Map orderParaMap = new HashMap();
                    orderParaMap.put("orderField", orderField);
                    orderParaMap.put("orderTrend", orderTrend);
                    session.put("zcclOrderParas", orderParaMap);
                } else if (queryOrder != null && queryOrder.equals("back")) {// 从修改或删除页面进行重新查询
                    int needDispPage = turnPage.getNeedDispPage();
                    turnPage = (TurnPage) conParaMap.get("turnPage");
                    // 需要重设needDispPage,以返回到上次修改或删除入口
                    turnPage.setNeedDispPage(needDispPage);
                }
            }
            // 当不输入任何条件时，gzxx为空，gzxx为空处理
            if (zcclParam == null) {
                zcclParam = new ZcclParam();
            }
            HashMap orderParaMap = (HashMap) session.get("zcclOrderParas");

            if ((orderField == null || orderField.trim().equals(""))
                    && orderParaMap != null) {
                orderField = (String) orderParaMap.get("orderField");
            }
            if ((orderTrend == null || orderTrend.trim().equals(""))
                    && orderParaMap != null) {
                orderTrend = (String) orderParaMap.get("orderTrend");
            }

            // 获取故障现象信息列表，并实现翻页
            Map retMap = zcclddmlService.getZcclList(zcclParam,
                    turnPage.getLinesPerPage(), turnPage.getPagesPerQuery(),
                    turnPage.getNeedDispPage(), turnPage.getTotalLinesNum(),
                    turnPage.getQueryFlag(), orderField, orderTrend);

            zcclList = (ArrayList) retMap.get("zcclList") == null ? new ArrayList<Zccl>() : (ArrayList) retMap.get("zcclList");

            if (turnPage.getQueryFlag().equals("new")) {// 如果是一次新的查询或从删除和修改返回的查询，需要重新统计；对于从删除和修改返回的查询，queryFlag=new
                turnPage.setTotalLinesNum(((TurnPage) retMap.get("turnPage"))
                        .getTotalLinesNum());
                turnPage.setNeedDispPage((((TurnPage) retMap.get("turnPage"))
                        .getNeedDispPage()));
            }
            turnPage.setTurnPageInfo(zcclList.size());// 设置翻页信息

            // 如果需要重新查询，要把查询结果保存到session中
            if (turnPage.getQueryFlag().equals("new")
                    || turnPage.getQueryFlag().equals("normal")) {
                session.put("zcclList", zcclList);
            }
            result = "/WEB-INF/pages/business/zcclddml/zcclddml_settings_mng_list.jsp";
            return SUCCESS;
        } catch (TranFailException te) {
            errorMap = ErrorHandleUtil.setExceptionInfo(te.getErrorCode(), te.getDisplayMessage(), te.getErrorLocation(), te.getErrorMsg());
            throw te;
        } catch (Exception e) {
            log.error("获取" + LOG_MODULE_NAME + " 设置 列表出错！ParaVal=", e);
            throw e;
        }
    }

    public String updateZccl() throws TranFailException, Exception {
        try {
            zcclddmlService.updateZccl(mclh_modify, rowflag_modify);

            okMap.put("okTitle", "操作成功");
            okMap.put("okMsg", "修改成功！");
            // 封装返回OK页面的参数

            Vector v = new Vector();
            String[] para1 = {"queryOrder", "back"};
            String[] para2 = {"turnPage.needDispPage", turnPage.getNeedDispPage() + ""};
            v.add(para1);
            v.add(para2);

            okMap.put("okParameters", v);
            okMap.put("okReturn", baseWebPath + "/zcclddml/queryZcclListAction.action");
            okMap.put("returnType", "close_parent_flush");// 参数returnType=close_parent_flush,表示在关闭对话框时更新父窗口页面
            result = "/ok.jsp";
            return SUCCESS;
        } catch (Exception e) {
            // 如果是模式对话框操作报错，则需要在errorMap中增加变量winType
            errorMap.put("winType", "dialogue");
            log.error("修改 " + LOG_MODULE_NAME + " 设置 出错！method:updateZccl()", e);
            throw e;
        }
    }

    /**
     * 删除zccl
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public String deleteZccl() throws TranFailException, Exception {
        try {
            zcclddmlService.deleteZccl(rowflay_zccl);
            okMap.put("okTitle", "操作成功！");
            okMap.put("okMsg", "删除成功！");

            Vector v = new Vector();
            String[] para1 = {"queryOrder", "back"};
            String[] para2 = {"turnPage.needDispPage", turnPage.getNeedDispPage() + ""};
            v.add(para1);
            v.add(para2);

            okMap.put("okParameters", v);
            okMap.put("okReturn", baseWebPath + "/zcclddml/queryZcclListAction.action");
            result = "/ok.jsp";
            return SUCCESS;
        } catch (TranFailException e) {
            errorMap = ErrorHandleUtil.setExceptionInfo(e.getErrorCode(), e.getDisplayMessage(), e.getErrorLocation(), e.getErrorMsg());
            throw e;
        } catch (Exception e) {
            log.error("删除 " + LOG_MODULE_NAME + " 设置 出错！");
            throw e;
        }
    }

}
