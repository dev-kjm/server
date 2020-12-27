package com.motoo.server.global.config;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //String token = jwtTokenProvider.resolveToken((HttpServletRequest) request);
        String token = jwtTokenProvider.resolveAccessToken((HttpServletRequest) request);
        log.info("accessToken :::: " + token);
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                log.info("token :: pass" );
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (ServiceException e) {

            //401에러 response
//            ObjectMapper objectMapper = new ObjectMapper();
//            ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
//
//            httpServletResponse.setStatus(e.getHttpStatus().value());
//            httpServletResponse.getWriter().write(objectMapper.writeValueAsString(errorResponse));
//            httpServletResponse.getWriter().flush();
//            httpServletResponse.getWriter().close();
        }
    }

}
