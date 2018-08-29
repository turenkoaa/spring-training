package deprecated;

import lombok.SneakyThrows;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class DeprecatedAuditBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    @SneakyThrows
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName);
            String beanClassName = beanDefinition.getBeanClassName();
            Class<?> clazz = Class.forName(beanClassName);

            if (clazz.isAnnotationPresent(DeprecatedClass.class)){
                DeprecatedClass annotation = clazz.getAnnotation(DeprecatedClass.class);
                Class alternative = annotation.alternative();
                beanDefinition.setBeanClassName(alternative.getName());
            }
        }

    }
}
