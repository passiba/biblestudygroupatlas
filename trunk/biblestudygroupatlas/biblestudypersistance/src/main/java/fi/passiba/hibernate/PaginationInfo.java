package fi.passiba.hibernate;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;

public class PaginationInfo implements Serializable{
    
    private  Criteria crit;
    private String sortColumn;
    private boolean sortAsc;
    private int firstRow;
    private int maxResult;
    private int count;
    
    private List results;
    
    public PaginationInfo()
    {
        
    }
    public PaginationInfo(Criteria crit,int firstRow,int maxResult)
    {
        this.crit=crit;
        this.firstRow=firstRow;
        this.maxResult=maxResult;
        results=crit.setFirstResult(firstRow*maxResult).setMaxResults(maxResult+1).list();
    }

   
    public boolean isNextPage()
    {
        return (results.size() > maxResult);
    }
    public boolean isPreviousPage()
    {
        return firstRow >0;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public boolean isSortAsc() {
        return sortAsc;
    }

    public void setSortAsc(boolean sortAsc) {
        this.sortAsc = sortAsc;
    }

    public int getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(int firstRow) {
        this.firstRow = firstRow;
    }

    public int getMaxResult() {
        return maxResult;
    }

    public void setMaxResult(int maxResults) {
        this.maxResult = maxResults;
    }
}
