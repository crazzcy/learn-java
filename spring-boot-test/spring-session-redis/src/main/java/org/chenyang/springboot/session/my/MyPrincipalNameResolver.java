package org.chenyang.springboot.session.my;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;

/**
 * @author : ChenYang
 * @date : 2021-03-22 5:29 下午
 * @since :
 */
public class MyPrincipalNameResolver {

    private final SpelExpressionParser parser = new SpelExpressionParser();

    MyPrincipalNameResolver() {
    }

    public String resolvePrincipal(Session session) {
        String principalName = (String)session.getAttribute(FindByIndexNameSessionRepository.PRINCIPAL_NAME_INDEX_NAME);
        if (principalName != null) {
            return principalName;
        } else {
            Object authentication = session.getAttribute("SPRING_SECURITY_CONTEXT");
            if (authentication != null) {
                Expression expression = this.parser.parseExpression("authentication?.name");
                return (String)expression.getValue(authentication, String.class);
            } else {
                return null;
            }
        }
    }
}
