package gyqw.test;

import gyqw.util.Logger;
import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.Annotation;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

/**
 * @author fred
 * 2019-12-09 11:57 AM
 */
public class JavaProxyTest {
    private Logger logger = new Logger();

    @Test
    public void javassist() {
        try {
            ClassPool cp = ClassPool.getDefault();
            CtClass ctClass = cp.makeClass("gyqw.test.JavassistDemoClass");
            ClassFile ccFile = ctClass.getClassFile();
            ConstPool constpool = ccFile.getConstPool();

            // 参数  1：属性类型  2：属性名称  3：所属类CtClass
            CtField ctField = new CtField(cp.get("java.lang.String"), "name", ctClass);
            ctField.setModifiers(Modifier.PRIVATE);

            // 注解
            FieldInfo fieldInfo = ctField.getFieldInfo();
            // 属性附上注解
            AnnotationsAttribute fieldAttr = new AnnotationsAttribute(constpool, AnnotationsAttribute.visibleTag);
            Annotation autowired = new Annotation("java.lang.Deprecated", constpool);
            fieldAttr.addAnnotation(autowired);
            fieldInfo.addAttribute(fieldAttr);

            // 设置name属性的get set方法
            ctClass.addMethod(CtNewMethod.setter("setName", ctField));
            ctClass.addMethod(CtNewMethod.getter("getName", ctField));
            ctClass.addField(ctField, CtField.Initializer.constant("defaultName"));

            Class<?> c = ctClass.toClass();
            Object obj = c.newInstance();

            Method method = obj.getClass().getMethod("getName");
            // 调用字节码生成类的execute方法
            logger.info(method.invoke(obj));
        } catch (Exception e) {
        }
    }
}
