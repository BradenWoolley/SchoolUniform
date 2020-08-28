package com.company;

import java.io.File;

public class Context {
    private IConvertFile convert;

    public Context(IConvertFile convert) {
        this.convert = convert;
    }

    public void executeConversion(File invoice, Order order) {
        this.convert.importFile(invoice, order);
    }
}
