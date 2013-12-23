package com.pub.pzjg.abstractClass;

import com.opensymphony.xwork2.ActionSupport;
import com.pub.init.SysConstance;
import com.pub.pzjg.PzjgAction;
import com.pub.util.TranFailException;
import com.pub.util.TurnPage;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * User: ycx
 * Date: 13-7-30
 * Time: 下午2:01
 * 说明：抽象类。通用方法定义，子类按需重写此类中的方法。
 */

public abstract class AbstractAction extends ActionSupport {
    protected static Logger log = Logger.getLogger(PzjgAction.class);
    protected String queryOrder;//有first  back两种
    protected String orderField;// 单击标题排序。需要排序的字段
    protected String orderTrend;// 单击标题排序。升序/降序
    protected TurnPage turnPage;// 用于翻页参数
    protected Map okMap;
    protected Map errorMap;
    protected String result;// 用于返回结果jsp页面
    protected String rowflag_str;// 删除用，传整车物料号，用"|"拼接。
    protected String baseWebPath = (String) SysConstance.getParameterSettings().get("baseWebPath");

    //以下setter/getter方法
    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        AbstractAction.log = log;
    }

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
    //以上setter/getter方法

    /**
     * 转到 总部端首页
     *
     * @return
     * @throws Exception
     */
    public abstract String dispatchMainPage() throws Exception;

    /**
     * 转到 服务站端首页
     *
     * @return
     * @throws Exception
     */
    public abstract String dispatchFwzMainPage() throws Exception;

    /**
     * 转到 新增页面
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public abstract String turnAddPage() throws TranFailException, Exception;

    /**
     * 转到 修改页面
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public abstract String turnModifyPage() throws TranFailException, Exception;

    /**
     * 转到 查看详情页面
     *
     * @return
     * @throws TranFailException
     * @throws Exception
     */
    public abstract String turnViewPage() throws TranFailException, Exception;

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

}
