package com.cwks.bizcore.comm.vo;

import java.util.List;

public class ComboTreePojo {
    private static final long serialVersionUID = 1L;
    public String id;
    public String text;
    public List<ComboTreePojo> children;
    public String parentId;

    public ComboTreePojo() {
    }

    public ComboTreePojo(String id, String text, List<ComboTreePojo> children) {
        this.id = id;
        this.text = text;
        this.children = children;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ComboTreePojo> getChildren() {
        return this.children;
    }

    public void setChildren(List<ComboTreePojo> children) {
        this.children = children;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
