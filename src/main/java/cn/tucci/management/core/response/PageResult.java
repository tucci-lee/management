package cn.tucci.management.core.response;

/**
 * @author tucci.lee
 */
public class PageResult extends Result {
    private Long total;

    public PageResult(Object data, Long total) {
        super(Boolean.TRUE, ResultStatus.OK.code(), ResultStatus.OK.msg(), data);
        this.total = total;
    }

    public static PageResult ok(Object data, Long total){
        return new PageResult(data, total);
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                "} " + super.toString();
    }
}
