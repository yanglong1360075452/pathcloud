package com.lifetech.dhap.pathcloud.security;

import com.lifetech.dhap.pathcloud.tracking.application.MicroscopeTrackingApplication;
import com.lifetech.dhap.pathcloud.tracking.application.dto.MicroscopeTrackingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SessionDestroyedListener implements ApplicationListener<SessionDestroyedEvent> {

    private static final Logger log = LoggerFactory.getLogger(SessionDestroyedListener.class);

    @Autowired
    private MicroscopeTrackingApplication microscopeTrackingApplication;

    @Override
    public void onApplicationEvent(SessionDestroyedEvent event)
    {
        log.info("Session id:" + event.getId());
        List<SecurityContext> lstSecurityContext = event.getSecurityContexts();
        SecurityUserDetails ud;

        for (SecurityContext securityContext : lstSecurityContext)
        {
            ud = (SecurityUserDetails) securityContext.getAuthentication().getPrincipal();
            if (ud != null) {
                MicroscopeTrackingDto dto = new MicroscopeTrackingDto();
                dto.setBookingPid(ud.getId());
                microscopeTrackingApplication.endMicroscopeUse(dto);
                log.info("Destroyed User:" + ud.getUsername());
            }
        }

    }

}