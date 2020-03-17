package utils;

public enum ErrorCode {
    OK(20000, "OK"),
    NOT_AUTH_CLIENT(00010, "user not exist"),// vivo手机购买用户
    NOT_AUTH_USER(20002, "Not auth user"),// vivo售后工作人员
    BUSINESS_ERROR(40000, "Business Error"),
    INTERNAL_ERROR(50000, "Internal Error"),
    ;

    private int code;
    private String desc;

    ErrorCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
