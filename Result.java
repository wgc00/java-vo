package com.wgc.todo.vo1;

        import com.github.pagehelper.Page;

        import java.util.ArrayList;
        import java.util.List;

public class Result<T> {

    private int code;   //返回状态码
    private T data;     //数据/T表示是模板
    private Page<T> page;   //页面
    private String error;
    private Throwable throwable;    //返回的错误
    private List<ErrorDetail> errorDetails;
    private long timeDifference;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Page<T> getPage() {
        return page;
    }

    public void setPage(Page<T> page) {
        this.page = page;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public List<ErrorDetail> getErrorDetails() {
        return errorDetails;
    }

    public void setErrorDetails(List<ErrorDetail> errorDetails) {
        this.errorDetails = errorDetails;
    }

    public long getTimeDifference() {
        return timeDifference;
    }

    public void setTimeDifference(long timeDifference) {
        this.timeDifference = timeDifference;
    }


    //指挥者
    public static Result ok(Object data) {
        Builder<Object> builder = new DefaultBuilders<>();
        return builder.ok(data);
    }

    public static Result ok(Object data, Page page) {
        Builder<Object> builder = new DefaultBuilders<>();
        return builder.ok(data, page);
    }

    public static Result errorStatusCode(int status) {
        Builder<Object> builder = new DefaultBuilders<>();
        return builder.errorStatusCode(status);
    }
    public static Result errorStatusCode(int status, String message) {
        Builder<Object> builder = new DefaultBuilders<>();
        return builder.errorStatusCode(status, message);
    }

    public static Builder status(int status) {
        return new DefaultBuilders().status(status);
    }

    public static Builder addError(String message) {

        Builder<Object> builder = new DefaultBuilders<>();
        builder.addError(message);
        return builder;
    }

    public static Builder addError(String type, String message) {
        Builder<Object> builder = new DefaultBuilders<>();
        builder.addError(type, message);
        return builder;
    }

    //接口
    public interface Builder<T> {
        //先想好接口在去写实现类
        Result<T> ok(T data);
        Result<T> ok(T data, Page<T> page);
        Result<T> errorStatusCode(int status);
        Result<T> errorStatusCode(int status, String message);
        Builder<T> status(int stateCode);
        Builder<T> addError(String err);
        Builder<T> addError(String type, String message);
    }

    //内部的实现类
    private static class DefaultBuilders<T> implements Builder<T> {
        private Result<T> result = new Result<>();
        private ErrorDetail detail = new ErrorDetail();


        @Override
        public Builder<T> status(int stateCode) {
            result.setCode(stateCode);
            return this;
        }

        @Override
        public Result<T> ok(T data) {
            result.setCode(200);
            result.setData(data);
            result.setErrorDetails(null);
            result.setThrowable(null);
            result.setTimeDifference(System.currentTimeMillis());
            return result;
        }

        @Override
        public Result<T> errorStatusCode(int status) {
            result.setCode(status);
            return result;
        }

        @Override
        public Result<T> errorStatusCode(int status, String message) {
            result.setError(message);
            errorStatusCode(status);
            return result;
        }

        @Override
        public Result<T> ok(T data, Page<T> page) {
            ok(data);
            result.setPage(page);
            return result;
        }

        @Override
        public Builder<T> addError(String err) {
            List<ErrorDetail> errorDetails = result.getErrorDetails();
            if (errorDetails == null) {
                errorDetails = new ArrayList<>();
            }
            result.setPage(null);
            result.setData(null);
            result.setTimeDifference(0);
            detail.setErrMassage(err);
            errorDetails.add(detail);
            return this;
        }

        @Override
        public Builder<T> addError(String type, String message) {
            detail.setType(type);
            addError(message);
            return this;
        }


    }

}

class ErrorDetail {
    private String type;
    private String errMassage;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getErrMassage() {
        return errMassage;
    }

    public void setErrMassage(String errMassage) {
        this.errMassage = errMassage;
    }

}
