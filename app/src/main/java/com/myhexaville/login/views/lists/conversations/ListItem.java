package com.myhexaville.login.views.lists.conversations;

public class ListItem {
    private String head;
    private String mess;

    public ListItem(String head, String mess) {
        this.head = head;
        this.mess = mess;
    }

    public String getNum() {
        return head;
    }

    public String getMess() {
        return mess;
    }
}
