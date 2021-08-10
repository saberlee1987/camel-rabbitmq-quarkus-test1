package com.saber;

import java.util.Objects;

public class ResponseDto {

    private String message;
    private Integer code;
    private String body;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseDto that = (ResponseDto) o;
        return Objects.equals(message, that.message) && Objects.equals(code, that.code) && Objects.equals(body, that.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, code, body);
    }

    @Override
    public String toString() {
        return "ResponseDto{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", body='" + body + '\'' +
                '}';
    }
}
