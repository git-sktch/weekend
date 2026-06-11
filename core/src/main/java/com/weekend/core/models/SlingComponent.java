package com.weekend.core.models;

import java.util.Collections;
import java.util.List;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class SlingComponent {

    @ChildResource(name = "items")
    private List<Item> items;

    public List<Item> getItems() {
        return items == null
                ? Collections.emptyList()
                : items;
    }

    @Model(
            adaptables = Resource.class,
            defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
    )
    public static class Item {

        @ValueMapValue
        private String title;

        @ValueMapValue
        private String image;

        public String getTitle() {
            return title;
        }

        public String getImage() {
            return image;
        }
    }
}