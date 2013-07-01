// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.dingziran.effective.domain;

import com.dingziran.effective.domain.Goal;
import com.dingziran.effective.domain.GoalDataOnDemand;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect GoalDataOnDemand_Roo_DataOnDemand {
    
    declare @type: GoalDataOnDemand: @Component;
    
    private Random GoalDataOnDemand.rnd = new SecureRandom();
    
    private List<Goal> GoalDataOnDemand.data;
    
    public Goal GoalDataOnDemand.getNewTransientGoal(int index) {
        Goal obj = new Goal();
        setDescription(obj, index);
        setName(obj, index);
        return obj;
    }
    
    public void GoalDataOnDemand.setDescription(Goal obj, int index) {
        String description = "description_" + index;
        obj.setDescription(description);
    }
    
    public void GoalDataOnDemand.setName(Goal obj, int index) {
        String name = "name_" + index;
        obj.setName(name);
    }
    
    public Goal GoalDataOnDemand.getSpecificGoal(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Goal obj = data.get(index);
        Long id = obj.getId();
        return Goal.findGoal(id);
    }
    
    public Goal GoalDataOnDemand.getRandomGoal() {
        init();
        Goal obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Goal.findGoal(id);
    }
    
    public boolean GoalDataOnDemand.modifyGoal(Goal obj) {
        return false;
    }
    
    public void GoalDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Goal.findGoalEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Goal' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Goal>();
        for (int i = 0; i < 10; i++) {
            Goal obj = getNewTransientGoal(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}
