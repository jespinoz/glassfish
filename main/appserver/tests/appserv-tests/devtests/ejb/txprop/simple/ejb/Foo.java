package com.sun.s1asdev.ejb.txprop.simple;

import javax.ejb.*;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;

public interface Foo extends EJBObject {
    void callHello() throws RemoteException;
}
