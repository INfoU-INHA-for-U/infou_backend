package com.gradu.infou.converter;

//import org.springframework.cglib.core.Converter;
import com.gradu.infou.Config.BaseResponseStatus;
import com.gradu.infou.Config.exception.BaseException;
import com.gradu.infou.Domain.Dto.Controller.Condition;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

public class StringToEnumConverter implements Converter<String, Condition> {

    @Override
    public Condition convert(String source){
        try {
            return Condition.valueOf(source);
        } catch (IllegalArgumentException e){
            throw new BaseException(BaseResponseStatus.CONDITION_INVALID);
        }
    }

}
