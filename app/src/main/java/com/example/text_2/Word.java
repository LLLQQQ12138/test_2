package com.example.text_2;

public class Word {
    String English;
    String Chinese;
    String Detail;

    public Word(String en) {
        English = en;
    }
    public Word(String en,String ch,String detail) {
        English = en;
        Chinese = ch;
        Detail = detail;
    }

    public String getEnglish() {
        return English;
    }

    public void setEnglish(String en) {
        English = en;
    }

    public String getChinese() {
        return Chinese;
    }

    public void setChinese(String ch) {
        Chinese = ch;
    }
    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }
}
