package org.xl.springboot.loop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

/**
 * @author xulei
 */
@Service
public class AService {
    public AService(@Lazy BService service) {
    }
}
