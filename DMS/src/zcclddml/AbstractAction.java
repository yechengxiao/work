package com.business.zcclddml;

import com.business.pjbfddml.PjbfddmlService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pub.init.SysConstance;
import com.pub.util.TranFailException;
import com.pub.util.TurnPage;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: ycx
 * Date: 13-8-31
 * Time: 下午2:39
 */
public abstract class AbstractAction extends ActionSupport {
    static final String LOG_MODULE_NAME = "拆零组装";
    static Logger log = Logger.getLogger(ZcclddmlAction.class);
    Map session = ActionContext.getContext().getSession();
    String result;// 用于返回结果jsp页面
    String rowflag_str;// 删除用，传PK，用"|"拼接。设置功能，新增时，表示zclh，用#，|拼接。
    String rowflay_zccl;//删除用,传zccl mclh
    String queryOrder;//有first  back两种
    String orderField;// 单击标题排序。需要排序的字段
    String orderTrend;// 单击标题排序。升序/降序
    Map okMap = new HashMap();
    Map errorMap = new HashMap();
    TurnPage turnPage = new TurnPage();// 用于翻页参数
    String baseWebPath = (String) SysConstance.getParameterSettings().get("baseWebPath");
    String module_name;// 模块名称
    //service
    ZcclddmlService zcclddmlService;
    PjbfddmlService pjbfddmlService;
    //param
    ZcclParam zcclParam;
    ZcclddmlParam zcclddmlParam;
    //list
    List<String> mclhList;
    List<Zcclddml> zcclddmlList;
    List<Zccl> zcclList;
    List<Zcclddnr> zcclddnrList;
    //entity
    Zccl zccl;
    Zcclddml zcclddml;
    //String
    String rowflag_modify;
    String mclh_modify;
    String otherCall;//其他页面调用

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        AbstractAction.log = log;
    }

    /**
     * 转到 总部端首页
     *
     * @return
     * @throws Exception
     */
    public abstract String dispatchMainPage() throws Exception;

    /**
     * 转到 新增页面
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public abstract String turnAddPage() throws TranFailException, Exception;

    /**
     * 转到 修改/详情页面
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public abstract String turnModifyViewPage() throws TranFailException, Exception;

    /**
     * 新增 成功后转到OK页面
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public abstract String add() throws TranFailException, Exception;

    /**
     * 修改 成功后转到OK页面
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public abstract String update() throws TranFailException, Exception;

    /*以下setter/getter方法*/

    /**
     * 根据PK删除 一次允许删除多个
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public abstract String delete() throws TranFailException, Exception;

    /**
     * 获取信息列表，并实现翻页功能。同时对该Action的Session进行处理。
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public abstract String queryList() throws TranFailException, Exception;

    public String getBaseWebPath() {
        return baseWebPath;
    }

    public void setBaseWebPath(String baseWebPath) {
        this.baseWebPath = baseWebPath;
    }

    public Map getErrorMap() {
        return errorMap;
    }

    public void setErrorMap(Map errorMap) {
        this.errorMap = errorMap;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
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

    public String getRowflag_str() {
        return rowflag_str;
    }

    public void setRowflag_str(String rowflag_str) {
        this.rowflag_str = rowflag_str;
    }

    public TurnPage getTurnPage() {
        return turnPage;
    }

    public void setTurnPage(TurnPage turnPage) {
        this.turnPage = turnPage;
    }

    public String getRowflay_zccl() {
        return rowflay_zccl;
    }

    public void setRowflay_zccl(String rowflay_zccl) {
        this.rowflay_zccl = rowflay_zccl;
    }

    public void setZcclddmlService(ZcclddmlService zcclddmlService) {
        this.zcclddmlService = zcclddmlService;
    }

    public List<String> getMclhList() {
        return mclhList;
    }

    public void setMclhList(List<String> mclhList) {
        this.mclhList = mclhList;
    }

    public ZcclParam getZcclParam() {
        return zcclParam;
    }

    public void setZcclParam(ZcclParam zcclParam) {
        this.zcclParam = zcclParam;
    }

    public List<Zcclddml> getZcclddmlList() {
        return zcclddmlList;
    }

    public void setZcclddmlList(List<Zcclddml> zcclddmlList) {
        this.zcclddmlList = zcclddmlList;
    }

    public ZcclddmlParam getZcclddmlParam() {
        return zcclddmlParam;
    }

    public void setZcclddmlParam(ZcclddmlParam zcclddmlParam) {
        this.zcclddmlParam = zcclddmlParam;
    }

    public Zccl getZccl() {
        return zccl;
    }

    public void setZccl(Zccl zccl) {
        this.zccl = zccl;
    }

    public Zcclddml getZcclddml() {
        return zcclddml;
    }

    public void setZcclddml(Zcclddml zcclddml) {
        this.zcclddml = zcclddml;
    }

    public List<Zccl> getZcclList() {
        return zcclList;
    }

    public void setZcclList(List<Zccl> zcclList) {
        this.zcclList = zcclList;
    }

    public String getRowflag_modify() {
        return rowflag_modify;
    }

    public void setRowflag_modify(String rowflag_modify) {
        this.rowflag_modify = rowflag_modify;
    }

    public String getMclh_modify() {
        return mclh_modify;
    }

    public void setMclh_modify(String mclh_modify) {
        this.mclh_modify = mclh_modify;
    }

    public String getOtherCall() {
        return otherCall;
    }

    public void setOtherCall(String otherCall) {
        this.otherCall = otherCall;
    }

    public void setPjbfddmlService(PjbfddmlService pjbfddmlService) {
        this.pjbfddmlService = pjbfddmlService;
    }

    public List<Zcclddnr> getZcclddnrList() {
        return zcclddnrList;
    }

    public void setZcclddnrList(List<Zcclddnr> zcclddnrList) {
        this.zcclddnrList = zcclddnrList;
    }
}
