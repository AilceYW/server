package test.serverframe.armc.server.util;

import java.io.Serializable;

public class HttpClientResult implements Serializable {

    /**
     * 响应状态码
     */
    private int code;

    /**
     * 响应数据
     */
    private String content;

    public HttpClientResult(int scInternalServerError) {
        this.code = scInternalServerError;
    }

    public HttpClientResult(int statusCode, String content) {
        this.code = statusCode;
        this.content = content;
    }

    @Override
    public String toString() {
        return "HttpClientResult{" +
                "code=" + code +
                ", content='" + content + '\'' +
                '}';
    }
}

