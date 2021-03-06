/**
 * 
 */
package com.caiyuna.witness.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * @author Ldl 
 * @since 1.0.0
 */
// @Entity
public class Scene implements Serializable {

    /**
     * 字段或域定义：<code>serialVersionUID</code>
     */
    private static final long serialVersionUID = -7294011112497135793L;

    @Id
    private String id;
    private String publisher;// 发布人
    private String avatarUrl;// 头像
    private Double latitude;// 维度
    private Double longitude;// 经度
    private String locationAddress;// 所处地址
    private CustomLocation customLocation;
    /*
     * @JsonIgnore(value = true)
     * private Date createTime;
     */

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

    public static class CustomLocation {
        private Double latitude;// 维度
        private Double longitude;// 经度

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
            return "CustomLocation [latitude=" + latitude + ", longitude=" + longitude + "]";
        }

    }

    public CustomLocation getCustomLocation() {
        return customLocation;
    }

    public void setCustomLocation(CustomLocation customLocation) {
        this.customLocation = customLocation;
    }

    @Override
    public String toString() {
        return "Scene [id=" + id + ", publisher=" + publisher + ", avatarUrl=" + avatarUrl + ", latitude=" + latitude + ", longitude=" + longitude
                + ", locationAddress=" + locationAddress + ", customLocation=" + customLocation + ", imagedesclist=" + imagedesclist + "]";
    }

}
