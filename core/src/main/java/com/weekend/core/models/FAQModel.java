package com.weekend.core.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;

@Model(adaptables = Resource.class)
public class FAQModel {

    @ChildResource(name = "faqs")
    private Resource faqs;

    private List<FAQItem> faqList;

    @PostConstruct
    protected void init() {

        faqList = new ArrayList<>();

        if (faqs != null) {

            for (Resource item : faqs.getChildren()) {

                faqList.add(new FAQItem(
                        item.getValueMap().get("question", ""),
                        item.getValueMap().get("answer", "")
                ));
            }
        }
    }

    public List<FAQItem> getFaqList() {
        return faqList;
    }

    public static class FAQItem {

        private final String question;
        private final String answer;

        public FAQItem(String question, String answer) {
            this.question = question;
            this.answer = answer;
        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }
    }
}