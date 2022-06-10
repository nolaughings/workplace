package com.ruoyi.plugs.framework.web;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.framework.web.exception.GlobalExceptionHandler;
import com.ruoyi.plugs.common.exception.BusinessException;
import com.ruoyi.plugs.common.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 *
 * @author ruoyi
 */
@RestControllerAdvice
public class GlobalExceptionHandlerPlug
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Object businessException(HttpServletRequest request, BusinessException e)
    {
        log.error(e.getMessage(), e);
        if (ServletUtils.isAjaxRequest(request))
        {
            return AjaxResult.error(e.getMessage());
        }
        else
        {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("errorMessage", e.getMessage());
            modelAndView.setViewName("error/business");
            return modelAndView;
        }
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult validatedBindException(BindException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public Object plugNotFoundException(HttpServletRequest request, ResourceNotFoundException e)
    {
        log.error(e.getMessage(), e);
        if (ServletUtils.isAjaxRequest(request))
        {
            return AjaxResult.error(e.getMessage());
        }
        else
        {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("errorMessage", e.getMessage());
            modelAndView.setViewName("templateStore/404");
            return modelAndView;
        }
    }
}
