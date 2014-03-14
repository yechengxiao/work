package com.pub.bxgsk;

import com.pub.util.TranFailException;

import java.util.Map;

public interface BxgskService {

    Map getList(BxgskParam bxgskParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, String queryFlag, String orderField, String orderTrend) throws TranFailException;

    Bxgsk getById(String bxgsdm) throws TranFailException;

    void update(Bxgsk bxgsk) throws TranFailException;

    void delete(String rowFlag) throws TranFailException;

    void add(Bxgsk bxgsk) throws TranFailException;
}
