package cn.itcast.travel.domain;

import java.util.List;

public class PageBean<T> {
    private int totalEntry;
    private int totalPage;
    private int entryPerPage;
    private int currentPage;
    private List<T> pageContent;

    public int getTotalEntry() {
        return totalEntry;
    }

    public void setTotalEntry(int totalEntry) {
        this.totalEntry = totalEntry;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getEntryPerPage() {
        return entryPerPage;
    }

    public void setEntryPerPage(int entryPerPage) {
        this.entryPerPage = entryPerPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getPageContent() {
        return pageContent;
    }

    public void setPageContent(List<T> pageContent) {
        this.pageContent = pageContent;
    }
}
