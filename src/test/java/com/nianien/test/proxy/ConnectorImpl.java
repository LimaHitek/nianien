package com.nianien.test.proxy;

/**
 * @author skyfalling
 */
public class ConnectorImpl implements Connector {
    @Override
    public void close() {
        System.out.println("closed...." + this.toString());
    }
}
