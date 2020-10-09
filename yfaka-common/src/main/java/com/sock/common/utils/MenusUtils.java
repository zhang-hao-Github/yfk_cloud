package com.sock.common.utils;

import com.sock.common.entity.YfkPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: yfaka-cloud
 * @author: @ZhangHao
 * @create: 2020-10-07 19:39
 **/
public class MenusUtils {


    //递归 封装 菜单
    public static List<YfkPermission> BuildMenus(List<YfkPermission> YfkPermissions) {
        List<YfkPermission> finalNode = new ArrayList<YfkPermission>();

        for (YfkPermission yfkPermissionNode : YfkPermissions) {

            if ("0".equals(yfkPermissionNode.getPid())) {
                yfkPermissionNode.setLevel(1);
                finalNode.add(selectChdilren(yfkPermissionNode, YfkPermissions));
            }

        }

        return finalNode;

    }

    private static YfkPermission selectChdilren(YfkPermission yfkPermissionNode, List<YfkPermission> yfkPermissionsList) {

        yfkPermissionNode.setChildren(new ArrayList<YfkPermission>());

        for (YfkPermission yfkPermission : yfkPermissionsList) {
            if (yfkPermissionNode.getId().equals(yfkPermission.getPid())) {
                yfkPermission.setLevel(yfkPermissionNode.getLevel() + 1);

//                log.info("当前层级{}==及当前节点值{}", yfkPermission.getLevel(), yfkPermission);
                yfkPermissionNode.getChildren().add(selectChdilren(yfkPermission, yfkPermissionsList));
            }
        }

        return yfkPermissionNode;

    }
}
