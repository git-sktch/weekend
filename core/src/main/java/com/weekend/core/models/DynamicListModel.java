package com.weekend.core.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import org.apache.sling.models.annotations.Model;

import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class DynamicListModel {

    @ValueMapValue
    private String parentPath;

    @ValueMapValue
    private int limit;

    @SlingObject
    private ResourceResolver resourceResolver;

    private List<Page> pages = new ArrayList<>();

    @PostConstruct
    protected void init() {

        PageManager pageManager = resourceResolver.adaptTo(PageManager.class);

        Page parentPage = pageManager.getPage(parentPath);

        if (parentPage != null) {

            Iterator<Page> children = parentPage.listChildren();

            int count = 0;

            while (children.hasNext() && count < limit) {

                pages.add(children.next());

                count++;
            }
        }
    }

    public List<Page> getPages() {
        return pages;
    }
}