/*
 *
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.sun.s1peqe.ejb.cmp.preselect.ejb;

import java.util.*;
import javax.ejb.*;
import java.rmi.RemoteException;

public interface CustomerRemote extends EJBObject {
    public int getItemsForLess() throws FinderException, RemoteException;
}
