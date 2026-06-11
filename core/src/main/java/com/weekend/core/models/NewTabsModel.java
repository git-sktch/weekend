package com.weekend.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(
        adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class NewTabsModel {

    @SlingObject
    private Resource resource;

    private List<Tab> tabs = new ArrayList<>();

    @PostConstruct
    protected void init() {

        Resource tabsResource = resource.getChild("tabs");

        if (tabsResource != null) {

            for (Resource item : tabsResource.getChildren()) {

                String title = item.getValueMap().get("title", "");
                String description = item.getValueMap().get("description", "");

                tabs.add(new Tab(title, description));
            }
        }

        // Default tab if no tabs are authored
        if (tabs.isEmpty()) {
            tabs.add(new Tab("Tab1", "This is first tab"));
        }
    }

    public List<Tab> getTabs() {
        return tabs;
    }

    public static class Tab {

        private String title;
        private String description;

        public Tab(String title, String description) {
            this.title = title;
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }
    }
}