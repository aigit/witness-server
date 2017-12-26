/**
 * 
 */
package com.caiyuna.witness.pos;

import java.util.List;

/**
 * @author Ldl 
 * @since 1.0.0
 */
public class Scene {

    private String id;
    private String publisher;// 发布人
    private String avatarUrl;// 头像
    private Double latitude;// 维度
    private Double longitude;// 经度
    private String locationAddress;// 所处地址

    private List<String> imagedesclist;// 图片描述地址

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Scene [id=" + id + ", publisher=" + publisher + ", avatarUrl=" + avatarUrl + ", latitude=" + latitude + ", longitude=" + longitude
                + ", locationAddress=" + locationAddress + ", imagedesclist=" + imagedesclist + "]";
    }

}
