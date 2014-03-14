package com.pub.bxgsk;

import java.util.Map;

public interface BxgskDao {
    void add(Bxgsk bxgsk);

    void update(Bxgsk bxgsk);

    void deleteByBxgsdm(String[] bxgsdm);

    Bxgsk queryByBxgsdm(String bxgsdm);

    Map queryList(BxgskParam queryParam, int linesPerPage, int pagesPerQuery, int needDispPage, int totalLines, String queryFlag, String orderField, String orderTrend);
}
