package com.company;

import java.io.File;

public interface IConvertFile {
    void importFile(File invoice, Order order);

    void printFile(File invoice);
}
