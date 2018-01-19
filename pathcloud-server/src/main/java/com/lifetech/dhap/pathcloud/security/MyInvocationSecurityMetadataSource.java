package com.lifetech.dhap.pathcloud.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 安全资源（URL）和角色映射关系处理器
 * 
 * @author Eric
 */

public class MyInvocationSecurityMetadataSource
				implements FilterInvocationSecurityMetadataSource {
	private static Logger log = LoggerFactory
			.getLogger(MyInvocationSecurityMetadataSource.class);
	
	private static Map<String, Collection<ConfigAttribute>> resourceMap
						= new LinkedHashMap<>();
	
	private boolean expire = false; // 过期标识
	
	private RequestMatcher requestMatcher; // 匹配规则
	  
    private String matcher = "ant"; // 规则标识 

	/**
	 * 初始化资源配置
	 * 
	 * spring 调用该方法的方式有2种
	 * 方式1，方法上加注解：
	 * @PostConstruct
	 * 
	 * 方式2，配置文件中 init-method 属性指定：
	 * <bean id="securityMetadataSource" init-method="initResource"
	 *  class="com.*.SecurityMetadataSource"/>
	 */
	@PostConstruct
	public void initResource(){
		resourceMap.clear();
	}

//	/**
//     * 将List<GroupVO>集合转换为框架需要的Collection<ConfigAttribute>集合
//     *
//     * @param roles
//     * @return Collection<ConfigAttribute>
//     */
//    private Collection<ConfigAttribute> list2Collection(List<AdminRole> roles) {
//        List<ConfigAttribute> list = new ArrayList<>();
//
//        for (AdminRole role : roles)
//            list.add(new SecurityConfig(role.getName()));
//        return list;
//    }
	
    @Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		
		HttpServletRequest request = ((FilterInvocation) object).getRequest();
  
        // 检测是否刷新了资源  
        if (isExpire()) {  
            // 清空原本资源  
        	resourceMap.clear();  
            expire = false;  
            log.debug("resources are expired");
        }  
  
        // 如果资源Map为空的时候则重新加载一次  
        if (null == resourceMap || resourceMap.isEmpty()) {
			reloadResource();
		}

        // 检测请求与当前资源匹配的正确性
        if(resourceMap != null){
            Iterator iterator = resourceMap.entrySet().iterator();
            for (;iterator.hasNext();) {
                @SuppressWarnings("unchecked")
                Map.Entry<String, Collection<ConfigAttribute>> entry = (Map.Entry) iterator.next();
                if (matcher.toLowerCase().equals("ant")) {
                    requestMatcher = new AntPathRequestMatcher(entry.getKey());
                }else if (matcher.toLowerCase().equals("regex")) {
                    requestMatcher = new RegexRequestMatcher(entry.getKey(), request
                            .getMethod(), true);
                }
                if (requestMatcher.matches(request)) {
					return entry.getValue();
				}
            }
        }

        return null;
	}

    @Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		Set<ConfigAttribute> allAttributes = new HashSet<>();
		
		for (Map.Entry<String, Collection<ConfigAttribute>> entry : resourceMap.entrySet()) {
            allAttributes.addAll(entry.getValue());
        }

        return allAttributes;
	}

    @Override
	public boolean supports(Class<?> clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}
	
	public void reloadResource(){
		this.initResource();
	}
	
	public void setMatcher(String matcher) {  
        this.matcher = matcher;  
    }  
  
    public boolean isExpire() {  
        return expire;  
    }  
  
    public void expireNow() {  
        this.expire = true;  
    }  
}
