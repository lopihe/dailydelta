package uk.contribit.dailydelta.core.context;


import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

public final class Contexts {
    @Id
    private final String word;
    private boolean ignored;
    private final Collection<Context> contexts = new HashSet<>();
    private final Collection<Context> exposedContexts = Collections.unmodifiableCollection(contexts);

    public Contexts(String word) {
        this(word, new HashSet<>(), false);
    }

    @PersistenceConstructor
    public Contexts(String word, Collection<Context> contexts, Boolean ignored) {
        this.word = word;
        this.ignored = ignored == null ? false : ignored;
        contexts.addAll(contexts);
    }

    public String getWord() {
        return word;
    }

    public Collection<Context> getContexts() {
        return exposedContexts;
    }

    public void add(Context context) {
        contexts.add(context);
    }

    public void addAll(Contexts newContexts) {
        contexts.addAll(newContexts.getContexts());
    }

    public boolean isIgnored() {
        return ignored;
    }

    public void setIgnored(boolean ignored) {
        this.ignored = ignored;
    }

    @Override
    public String toString() {
        return "Contexts{" +
                "word='" + word + '\'' +
                ", ignored=" + ignored +
                ", contexts=" + contexts +
                '}';
    }
}
