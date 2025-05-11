package be.ugent.objprog.minionwars.configloader;

import org.jdom2.Element;

import java.util.List;

public abstract class ConfigLoader<T> {

    private final List<Element> configElements;

    public ConfigLoader(List<Element> configElements) {
        this.configElements = configElements;
    }

    public List<Element> getConfigElements() { return configElements; }
    public abstract List<T> getElements();
}