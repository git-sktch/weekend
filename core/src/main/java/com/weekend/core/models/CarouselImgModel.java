package com.weekend.core.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class CarouselImgModel {

    @SlingObject
    private Resource resource;

    private final List<CarouselItem> items = new ArrayList<>();

    @PostConstruct
    protected void init() {
        Resource itemsResource = resource.getChild("items");
        if (itemsResource != null) {
            for (Resource itemResource : itemsResource.getChildren()) {
                addItem(itemResource.getValueMap());
            }
            return;
        }

        addItem(resource.getValueMap());
    }

    private void addItem(ValueMap valueMap) {
        String image = valueMap.get("image", String.class);
        String text = valueMap.get("text", String.class);
        String textarea = valueMap.get("textarea", String.class);

        if (isNotBlank(image)) {
            items.add(new CarouselItem(image, text, textarea));
        }
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    public List<CarouselItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public boolean isMultipleItems() {
        return items.size() > 1;
    }

    public int getItemCount() {
        return items.size();
    }

    public static final class CarouselItem {
        private final String image;
        private final String text;
        private final String textarea;

        private CarouselItem(String image, String text, String textarea) {
            this.image = image;
            this.text = text;
            this.textarea = textarea;
        }

        public String getImage() {
            return image;
        }

        public String getText() {
            return text;
        }

        public String getTextarea() {
            return textarea;
        }
    }
}
