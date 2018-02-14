/**
 * 
 */
package com.caiyuna.witness.vo;

import java.util.List;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public class SceneView {
    
    private String id;
    private String publisher;// 发布人
    private String publisherAvatarUrl;// 头像
    private String locationAddress;// 所处地址

    private List<String> imagedesclist;// 图片描述地址

    /**
     * 构造函数
     * @param id
     * @param publisher
     * @param publisherAvatarUrl
     * @param locationAddress
     * @param imagedesclist
     */
    public SceneView(String id, String publisher, String publisherAvatarUrl, String locationAddress, List<String> imagedesclist) {
        super();
        this.id = id;
        this.publisher = publisher;
        this.publisherAvatarUrl = publisherAvatarUrl;
        this.locationAddress = locationAddress;
        this.imagedesclist = imagedesclist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublisherAvatarUrl() {
        return publisherAvatarUrl;
    }

    public void setPublisherAvatarUrl(String publisherAvatarUrl) {
        this.publisherAvatarUrl = publisherAvatarUrl;
    }

    public String getLocationAddress() {
        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {
        this.locationAddress = locationAddress;
    }

    public List<String> getImagedesclist() {
        return imagedesclist;
    }

    public void setImagedesclist(List<String> imagedesclist) {
        this.imagedesclist = imagedesclist;
    }

    @Override
    public String toString() {
        return "SceneView [id=" + id + ", publisher=" + publisher + ", publisherAvatarUrl=" + publisherAvatarUrl + ", locationAddress="
                + locationAddress + ", imagedesclist=" + imagedesclist + "]";
    }

}
