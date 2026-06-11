package com.weekend.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.weekend.core.testcontext.AppAemContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class SimpleComponentModelTest {

    private final AemContext context = AppAemContext.newAemContext();

    @Test
    void returnsConfiguredValues() {
        Resource resource = context.create().resource("/content/simple",
                "sling:resourceType", "weekend/components/simplecomponent",
                "title", "My Title");

        SimpleComponentModel model = resource.adaptTo(SimpleComponentModel.class);

        assertEquals("My Title", model.getTitle());
    }

    @Test
    void returnsDefaultValuesWhenDialogIsEmpty() {
        Resource resource = context.create().resource("/content/simple",
                "sling:resourceType", "weekend/components/simplecomponent");

        SimpleComponentModel model = resource.adaptTo(SimpleComponentModel.class);

        assertEquals("Simple Component", model.getTitle());
    }
}
