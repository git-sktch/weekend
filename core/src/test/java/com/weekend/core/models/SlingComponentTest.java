package com.weekend.core.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.weekend.core.testcontext.AppAemContext;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class SlingComponentTest {

    private final AemContext context = AppAemContext.newAemContext();

    @Test
    void returnsCompositeMultifieldItems() {
        Resource resource = context.create().resource("/content/sling-component",
                "sling:resourceType", "weekend/components/slingcomponentjava");
        Resource items = context.create().resource(resource, "items");
        context.create().resource(items, "item0",
                "title", "First Item",
                "image", "/content/dam/weekend/first.jpg");
        context.create().resource(items, "item1",
                "title", "Second Item",
                "image", "/content/dam/weekend/second.jpg");

        SlingComponent model = resource.adaptTo(SlingComponent.class);
        List<SlingComponent.Item> authoredItems = model.getItems();

        assertEquals(2, authoredItems.size());
        assertEquals("First Item", authoredItems.get(0).getTitle());
        assertEquals("/content/dam/weekend/first.jpg", authoredItems.get(0).getImage());
        assertEquals("Second Item", authoredItems.get(1).getTitle());
        assertEquals("/content/dam/weekend/second.jpg", authoredItems.get(1).getImage());
    }

    @Test
    void returnsEmptyListWhenNoItemsAreAuthored() {
        Resource resource = context.create().resource("/content/sling-component",
                "sling:resourceType", "weekend/components/slingcomponentjava");

        SlingComponent model = resource.adaptTo(SlingComponent.class);

        assertTrue(model.getItems().isEmpty());
    }
}
