package com.dingziran.effective.shared;

import java.util.List;

import com.dingziran.effective.domain.Goal;
import com.dingziran.effective.server.Contact;
import com.google.web.bindery.requestfactory.shared.InstanceRequest;
import com.google.web.bindery.requestfactory.shared.Request;
import com.google.web.bindery.requestfactory.shared.RequestContext;
import com.google.web.bindery.requestfactory.shared.RequestFactory;
import com.google.web.bindery.requestfactory.shared.Service;


public interface Factory extends RequestFactory
{
    ContactRequest createContactRequest ();
    GoalRequest createGoalRequest();

    @Service(value = Contact.class)
    public interface ContactRequest extends RequestContext
    {
        Request<Integer> count ();
        Request<ContactProxy> find (Long id);
        Request<List<ContactProxy>> findAllContacts ();

        InstanceRequest<ContactProxy, Void> persist ();
        InstanceRequest<ContactProxy, Void> remove ();
    }
    @Service(value=Goal.class)
    public interface GoalRequest extends RequestContext
    {
        Request<List<GoalProxy>> findGoalEntries(int firstResult, int maxResults);
        Request<Long> countGoals();
    }
}
