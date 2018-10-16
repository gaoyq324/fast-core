package cn.ey.gds.ita.fast.core.controller;


import java.io.Serializable;

/**
 * Created by PW152NJ on 2017/6/14.
 */
public class RequestUrlDto implements Serializable {
    private static final long serialVersionUID = 247714666080613254L;

    private String url;
    private String serviceName;
    private String method;
    private String inputParam;
    private String returnParam;

    private String inputParamDto;
    private String returnParamDto;

    private String desc;
    private String beanName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getInputParam() {
        return inputParam;
    }

    public void setInputParam(String inputParam) {
        this.inputParam = inputParam;
    }

    public String getReturnParam() {
        return returnParam;
    }

    public void setReturnParam(String returnParam) {
        this.returnParam = returnParam;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getInputParamDto() {
        return inputParamDto;
    }

    public void setInputParamDto(String inputParamDto) {
        this.inputParamDto = inputParamDto;
    }

    public String getReturnParamDto() {
        return returnParamDto;
    }

    public void setReturnParamDto(String returnParamDto) {
        this.returnParamDto = returnParamDto;
    }
}
