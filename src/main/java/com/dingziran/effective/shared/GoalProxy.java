package com.dingziran.effective.shared;

import com.dingziran.effective.domain.Goal;
import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.EntityProxyId;
import com.google.web.bindery.requestfactory.shared.ProxyFor;
@ProxyFor(Goal.class)
public interface GoalProxy extends EntityProxy {
    public Long getId();

    public String getName();
    
    public void setName(String name);
    
    public String getDescription();
    
    public void setDescription(String description) ;

    EntityProxyId<GoalProxy> stableId();
}
