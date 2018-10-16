package cn.ey.gds.ita.fast.core.dao.entity;

import java.util.Date;

/**
 * @Author : ITA.
 * @Description :
 * @Created : ITA. on 2017/11/30 19:02.
 * @Modify : ITA. on 2017/11/30 19:02.
 * @Version : V.0.1
 */
public class BaseEntity {
    //
    //创建人
    private Long createBy;
    //创建时间
    private Date createTime;
    //修改人
    private Long updateBy;
    //修改时间
    private Date updateTime;
    //删除标记
    private Integer deleteFlag;

    /**
     * 设置：创建人
     */
    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }
    /**
     * 获取：创建人
     */
    public Long getCreateBy() {
        return createBy;
    }
    /**
     * 设置：创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    /**
     * 获取：创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }
    /**
     * 设置：修改人
     */
    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }
    /**
     * 获取：修改人
     */
    public Long getUpdateBy() {
        return updateBy;
    }
    /**
     * 设置：修改时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    /**
     * 获取：修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }
    /**
     * 设置：删除标记
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }
    /**
     * 获取：删除标记
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }
}
