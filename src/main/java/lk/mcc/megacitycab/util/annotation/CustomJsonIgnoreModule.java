package lk.mcc.megacitycab.util.annotation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;

import java.util.ArrayList;
import java.util.List;

public class CustomJsonIgnoreModule extends SimpleModule {
    public CustomJsonIgnoreModule() {
        super(CustomJsonIgnoreModule.class.getSimpleName());

        setSerializerModifier(new BeanSerializerModifier() {
            @Override
            public List<BeanPropertyWriter> changeProperties(SerializationConfig config, BeanDescription beanDesc, List<BeanPropertyWriter> beanProperties) {
                for (BeanPropertyWriter writer : new ArrayList<>(beanProperties)) {
                    BeanPropertyDefinition propertyDefinition = beanDesc.findProperties().stream()
                            .filter(property -> property.getName().equals(writer.getName())).findFirst().orElse(null);
                    IgnoreJsonProperty customJsonIgnore = propertyDefinition != null ? propertyDefinition.getAccessor()
                            .getAnnotation(IgnoreJsonProperty.class) : null;

                    if (customJsonIgnore != null) {
                        beanProperties.remove(writer);
                        beanProperties.add(new CustomBeanPropertyWriter(writer));
                    }
                }
                return beanProperties;
            }
        });
    }

    public static class CustomBeanPropertyWriter extends BeanPropertyWriter {
        public CustomBeanPropertyWriter(BeanPropertyWriter writer) {
            super(writer);
        }

        @Override
        public void serializeAsField(Object bean, JsonGenerator gen, SerializerProvider provider) throws Exception {
            gen.writeFieldName(getName());
            gen.writeString("...");
        }
    }
}
