package miniproject.KUrestaurant.web;

import lombok.Data;

@Data
public class PageInfo {
    private int pageNum;
    private int start;
    private int end;
    private int totalPages;
    private String nameCondition;
    private String categoryCondition;
    private String sort;
    private boolean isFirstPage;
    private boolean isLastPage;

    public PageInfo(int pageNum, int totalPages, String nameCondition, String categoryCondition, String sort) {
        this.pageNum = pageNum;
        this.totalPages = totalPages;
        this.nameCondition = nameCondition;
        this.categoryCondition = categoryCondition;
        this.sort = sort;
        this.isFirstPage = pageNum/5 == 0;
        this.isLastPage = pageNum/5 == (totalPages-1)/5;
        this.start = (int) pageNum/5*5;
        this.end = (this.isLastPage) ? Math.max(totalPages-1, 0) : this.start + 4;
    }
}
