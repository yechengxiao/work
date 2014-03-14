package com.pub.bxgsk;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.pub.init.SysConstance;
import com.pub.util.TurnPage;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractAction extends ActionSupport {
    static final String LOG_MODULE_NAME = "保险公司";
    static Logger log = Logger.getLogger(BxgskAction.class);
    Map session = ActionContext.getContext().getSession();
    String result;// 用于返回结果jsp页面
    String rowflag_str;// 删除用，传PK，用"|"拼接。设置功能，新增时，表示zclh，用#，|拼接。
    String queryOrder;//有first  back两种
    String orderField;// 单击标题排序。需要排序的字段
    String orderTrend;// 单击标题排序。升序/降序
    Map okMap = new HashMap();
    Map errorMap = new HashMap();
    TurnPage turnPage = new TurnPage();// 用于翻页参数
    String baseWebPath = (String) SysConstance.getParameterSettings().get("baseWebPath");
    String module_name;// 模块名称
    //service
    BxgskService bxgskService;
    //param
    BxgskParam bxgskParam;
    //list
    List<String> mclhList;
    List<Bxgsk> bxgskList;
    //entity
    Bxgsk bxgsk;
    //String
    String rowflag_modify;
    String otherCall;//其他页面调用
    //String mclh_modify;
    // String rowflay_zccl;//删除用,传zccl mclh

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        AbstractAction.log = log;
    }

    public static String getLogModuleName() {
        return LOG_MODULE_NAME;
    }

    public String getBaseWebPath() {
        return baseWebPath;
    }

    public void setBaseWebPath(String baseWebPath) {
        this.baseWebPath = baseWebPath;
    }

    public Bxgsk getBxgsk() {
        return bxgsk;
    }

    public void setBxgsk(Bxgsk bxgsk) {
        this.bxgsk = bxgsk;
    }

    public List<Bxgsk> getBxgskList() {
        return bxgskList;
    }

    public void setBxgskList(List<Bxgsk> bxgskList) {
        this.bxgskList = bxgskList;
    }

    public BxgskParam getBxgskParam() {
        return bxgskParam;
    }

    public void setBxgskParam(BxgskParam bxgskParam) {
        this.bxgskParam = bxgskParam;
    }

    public BxgskService getBxgskService() {
        return bxgskService;
    }

    public void setBxgskService(BxgskService bxgskService) {
        this.bxgskService = bxgskService;
    }

    public Map getErrorMap() {
        return errorMap;
    }

    public void setErrorMap(Map errorMap) {
        this.errorMap = errorMap;
    }

    public List<String> getMclhList() {
        return mclhList;
    }

    public void setMclhList(List<String> mclhList) {
        this.mclhList = mclhList;
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

    public String getOtherCall() {
        return otherCall;
    }

    public void setOtherCall(String otherCall) {
        this.otherCall = otherCall;
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

    public TurnPage getTurnPage() {
        return turnPage;
    }

    public void setTurnPage(TurnPage turnPage) {
        this.turnPage = turnPage;
    }
}
