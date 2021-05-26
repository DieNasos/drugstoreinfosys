package ru.nsu.ccfit.beloglazov.drugstoreinfosys;

import ru.nsu.ccfit.beloglazov.drugstoreinfosys.frames.TableFrame;
import java.util.List;

abstract public class CustomOperation {
    private final String name;

    public CustomOperation(String name) { this.name = name; }

    public String getName() { return name; }

    abstract public void execute(List<Object> args, TableFrame tf);
}