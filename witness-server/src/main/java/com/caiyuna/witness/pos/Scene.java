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

    private String publisher;// 发布人
    private String avatarUrl;// 头像
    private String latitude;// 维度
    private String longitude;// 经度
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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
        return "Scene [publisher=" + publisher + ", avatarUrl=" + avatarUrl + ", latitude=" + latitude + ", longitude=" + longitude
                + ", locationAddress=" + locationAddress + ", imagedesclist=" + imagedesclist + "]";
    }

}
