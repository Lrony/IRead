package com.lrony.iread.model.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lrony on 18-6-14.
 */
@Entity
public class WYHotBean {

    /**
     * id : 3999
     * songid : 65528
     * name : 淘汰
     * songname : 陈奕迅
     * userid : 5621522
     * username : 理想乌托邦mei
     * content : 周杰伦给别人的歌也全是精品！！[强]
     * zjid : 307335057
     */

    @Id
    private String id;
    private String songid;
    private String name;
    private String songname;
    private String userid;
    private String username;
    private String content;
    private String zjid;

    @Generated(hash = 292307045)
    public WYHotBean(String id, String songid, String name, String songname,
                     String userid, String username, String content, String zjid) {
        this.id = id;
        this.songid = songid;
        this.name = name;
        this.songname = songname;
        this.userid = userid;
        this.username = username;
        this.content = content;
        this.zjid = zjid;
    }

    @Generated(hash = 1103650632)
    public WYHotBean() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSongid() {
        return songid;
    }

    public void setSongid(String songid) {
        this.songid = songid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getZjid() {
        return zjid;
    }

    public void setZjid(String zjid) {
        this.zjid = zjid;
    }
}
