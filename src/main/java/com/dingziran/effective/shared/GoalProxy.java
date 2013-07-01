package com.dingziran.effective.shared;

import com.google.web.bindery.requestfactory.shared.EntityProxy;

public interface GoalProxy extends EntityProxy {
    public Long getId();

    public String getName();
    
    public void setName(String name);
    
    public String getDescription();
    
    public void setDescription(String description) ;
}
