package cn.edu.zucc.sso.service.impl;

import cn.edu.zucc.sso.dao.PermissionDao;
import cn.edu.zucc.sso.exception.BaseException;
import cn.edu.zucc.sso.pojo.BeanPermission;
import cn.edu.zucc.sso.resultformat.ResultFormat;
import cn.edu.zucc.sso.service.PermissionService;
import cn.edu.zucc.sso.utils.BaseUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author crabxyj
 * @date 2019/12/25 14:13
 */
@Service("permissionServiceImpl")
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, BeanPermission> implements PermissionService {

    @Resource(name = "permissionDao")
    private PermissionDao dao;

    private final WebApplicationContext applicationContext;

    @Autowired
    public PermissionServiceImpl(WebApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public IPage<BeanPermission> loadPage(String type,int page,int pageSize){
        Page<BeanPermission> page1 = new Page<>(page, pageSize);
        if (type==null||"".equals(type.trim())){
            return page(page1);
        }
        QueryWrapper<BeanPermission> query = new QueryWrapper<>();
        query.eq("type",type);
        return page(page1,query);
    }

    @Override
    public List<BeanPermission> load(@NotNull String type){
        QueryWrapper<BeanPermission> query = new QueryWrapper<>();
        query.eq("type",type);
        return list(query);
    }

    @Override
    public BeanPermission add(BeanPermission permission) throws BaseException {
        QueryWrapper<BeanPermission> query = new QueryWrapper<>();
        query.eq("type", permission.getName());
        query.eq("name",permission.getName());
        if (getOne(query) != null) {
            throw new BaseException("当前权限已存在");
        }
        save(permission);
        return permission;
    }

    private void add(Collection<BeanPermission> permissions)throws BaseException {
        for (BeanPermission permission : permissions){
            if (StringUtils.isEmpty(permission.getName())||StringUtils.isEmpty(permission.getType())){
                throw new BaseException("权限内容不能为空");
            }
        }
        saveBatch(permissions);
    }

    @Override
    public void delete(int permissionId) throws BaseException {
        List<Integer> roleIds = dao.selectRoleIdByPermissionId(permissionId);
        if(!roleIds.isEmpty()){
            throw new BaseException("当前权限正在被使用，无法删除");
        }
        removeById(permissionId);
    }

    @Override
    public void resetPortPermission() throws BaseException {
        List<BeanPermission> permissionInDataBase = load("type");
        Set<@NotNull(message = "name 不能为空") String> urls = permissionInDataBase.stream()
                .map(BeanPermission::getName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        List<String> nowUrls = loadPortPermission();
        Set<BeanPermission> addPermissions = nowUrls.stream()
                .filter(url -> !urls.contains(url))
                .map(url->new BeanPermission().setType("port").setName(url))
                .collect(Collectors.toSet());

        add(addPermissions);


        Set<Integer> ids = permissionInDataBase.stream()
                .filter(p -> !nowUrls.contains(p.getName()))
                .map(BeanPermission::getPermissionId)
                .collect(Collectors.toSet());
        removeByIds(ids);
    }

    @Override
    public Set<BeanPermission> getByRoleId(Collection<Integer> roleIds) {
        Set<Integer> collect = roleIds.stream()
                .filter(id -> id > 0)
                .collect(Collectors.toSet());

        if (collect.isEmpty()) {
            return new HashSet<>();
        }

        String ids = BaseUtils.toSqlString(collect);
        List<BeanPermission> permissions = dao.selectByRoleId(ids);
        return new HashSet<>(permissions);
    }


    public List<String> loadPortPermission() {
        RequestMappingHandlerMapping mapping = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        //获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> methods = mapping.getHandlerMethods();
        List<String> urls = new LinkedList<>();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : methods.entrySet()) {
            HandlerMethod method = m.getValue();
            //如果没有 ResultFormat注解 跳过
            ResultFormat resultFormat = method.getMethodAnnotation(ResultFormat.class);
            if (resultFormat == null) {
                resultFormat = method.getMethod().getDeclaringClass().getAnnotation(ResultFormat.class);
                if (resultFormat == null) {
                    continue;
                }
            }
            PatternsRequestCondition condition = m.getKey().getPatternsCondition();
            urls.addAll(condition.getPatterns());
        }
        return urls;
    }

    public JSONArray getAllUrlDetail() {

        RequestMappingHandlerMapping mapping = applicationContext.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        //获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> methods = mapping.getHandlerMethods();
        JSONArray ja = new JSONArray();
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : methods.entrySet()) {
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            PatternsRequestCondition condition = info.getPatternsCondition();
            //类名
            String className = method.getMethod().getDeclaringClass().getName();
            if (!className.startsWith("cn.edu.zucc.sso")) {
                continue;
            }
            String methodName = method.getMethod().getName();

            JSONObject json = new JSONObject();
            json.put("urls", condition.getPatterns());
            json.put("className", className);
            json.put("methodName", methodName);
            ja.add(json);
        }
        return ja;
    }

}
