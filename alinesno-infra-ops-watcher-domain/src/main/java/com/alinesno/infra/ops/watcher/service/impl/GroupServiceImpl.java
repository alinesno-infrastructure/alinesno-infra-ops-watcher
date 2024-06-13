package com.alinesno.infra.ops.watcher.service.impl;

import com.alinesno.infra.common.core.service.impl.IBaseServiceImpl;
import com.alinesno.infra.common.core.utils.StringUtils;
import com.alinesno.infra.ops.watcher.dto.TreeSelectDto;
import com.alinesno.infra.ops.watcher.entity.GroupEntity;
import com.alinesno.infra.ops.watcher.mapper.GroupMapper;
import com.alinesno.infra.ops.watcher.service.IGroupService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class GroupServiceImpl extends IBaseServiceImpl<GroupEntity, GroupMapper> implements IGroupService {


    @Override
    public void initGroup(long userId) {
        long count = count(new LambdaQueryWrapper<GroupEntity>().eq(GroupEntity::getOperatorId , userId)) ;
        if(count == 0){
            // 初始化一个默认分组
            GroupEntity groupEntity = new GroupEntity() ;

            groupEntity.setParentId(0L);
            groupEntity.setOperatorId(userId) ;
            groupEntity.setGroupName("默认分组") ;
            groupEntity.setGroupDesc("默认分组") ;
            groupEntity.setGroupType("default") ;

            save(groupEntity)  ;

        }
    }

    /**
     * 根据部门信息构建目录树状列表。
     * <p>
     * 本方法旨在将部门信息转换为树状结构，以适用于具有层级关系的下拉选择列表。
     * 返回的列表中，每个元素都代表一个可选的目录节点。
     *
     * @return List<TreeSelectDto> 返回一个树状结构的列表，每个节点代表一个部门。
     */
    @Override
    public List<TreeSelectDto> selectCatalogTreeList() {
        // 构建部门的树状结构
        List<GroupEntity> deptTrees = buildDeptTree(list());
        // 将部门实体转换为树选择DTO对象的列表
        return deptTrees.stream().map(TreeSelectDto::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param prompts 部门列表
     * @return 树结构列表
     */
    public List<GroupEntity> buildDeptTree(List<GroupEntity> prompts) {

        List<GroupEntity> returnList = new ArrayList<>();
        List<Long> tempList = prompts.stream().map(GroupEntity::getId).toList();

        for (GroupEntity dept : prompts) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(dept.getParentId())) {
                recursionFn(prompts, dept);
                returnList.add(dept);
            }
        }

        if (returnList.isEmpty()) {
            returnList = prompts;
        }
        return returnList;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<GroupEntity> list, GroupEntity t) {
        // 得到子节点列表
        List<GroupEntity> childList = getChildList(list, t);
        t.setChildren(childList);
        for (GroupEntity tChild : childList) {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<GroupEntity> getChildList(List<GroupEntity> list, GroupEntity t) {
        List<GroupEntity> tlist = new ArrayList<>();
        for (GroupEntity n : list) {
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<GroupEntity> list, GroupEntity t) {
        return !getChildList(list, t).isEmpty();
    }
}
