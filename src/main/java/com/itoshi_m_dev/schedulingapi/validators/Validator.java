package com.itoshi_m_dev.schedulingapi.validators;

import com.itoshi_m_dev.schedulingapi.exception.ValidatorException;

public class Validator {

    public static void notNull(Object obj){
        if(obj == null){
            throw new ValidatorException("O argumento passado é nulo");
        }
    }


}
