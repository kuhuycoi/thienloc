package com.resources.facade;

import com.resources.entity.Action;
import com.resources.pagination.admin.DefaultAdminPagination;
public class ActionFacade extends AbstractFacade{
    
    public ActionFacade(){
        super(Action.class);
    }
    
    

    @Override
    public void pageData(DefaultAdminPagination pagination) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
