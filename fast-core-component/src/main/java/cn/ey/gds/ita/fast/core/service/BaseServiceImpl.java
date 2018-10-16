package cn.ey.gds.ita.fast.core.service;


import cn.ey.gds.ita.fast.core.dao.entity.BaseEntity;
import cn.ey.gds.ita.fast.core.dao.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;

import java.util.Date;

/**
 * @Author : ITA.
 * @Description :
 * @Created : ITA. on 2017/11/30 18:58.
 * @Modify : ITA. on 2017/11/30 18:58.
 * @Version : V.0.1
 */
public class BaseServiceImpl {

    protected String getUserName() {
        String userName;
        try {
            Object principal = SecurityUtils.getSubject().getPrincipal();
            if (principal != null && principal instanceof SysUserEntity) {
                userName = ((SysUserEntity) SecurityUtils.getSubject()).getUsername();
            } else {
                userName = "";
            }
        }catch (Exception e){
            userName = "";
        }


        return userName;
    }


    protected Long getUserId() {
        Long userId;
        try {
            Object principal = SecurityUtils.getSubject().getPrincipal();
            if (principal != null && principal instanceof SysUserEntity) {
                userId = ((SysUserEntity) SecurityUtils.getSubject()).getUserId();
            } else {
                userId = 1L;
            }
        }catch (Exception e){
            userId = 1L;
        }


        return userId;
    }

    protected void setCreateInfo(BaseEntity baseEntity) {
        Long userId = getUserId();
        baseEntity.setCreateBy(userId);
        baseEntity.setUpdateBy(userId);
        baseEntity.setCreateTime(new Date());
        baseEntity.setUpdateTime(new Date());
        baseEntity.setDeleteFlag(0);
    }

    protected void setUpdateInfo(BaseEntity baseEntity) {
        baseEntity.setUpdateBy(getUserId());
        baseEntity.setUpdateTime(new Date());
    }

    protected void setDeleteInfo(BaseEntity baseEntity) {
        baseEntity.setUpdateBy(getUserId());
        baseEntity.setUpdateTime(new Date());
        baseEntity.setDeleteFlag(1);
    }

}
