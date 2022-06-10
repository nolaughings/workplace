package com.ruoyi.plugs.common.exception;

/**
 * 不存在的资源异常
 *
 * @author ruoyi
 */
public class ResourceNotFoundException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    protected final String message;

    public ResourceNotFoundException(String message)
    {
        this.message = message;
    }

    public ResourceNotFoundException(String message, Throwable e)
    {
        super(message, e);
        this.message = message;
    }

    @Override
    public String getMessage()
    {
        return message;
    }
}
