package org.chenyang.study.concurrency.prodcons;

/**
 * 吐司
 * @author ChenYang
 * @date 2019-02-23 10:11
 **/

public class Toast {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Toast(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
