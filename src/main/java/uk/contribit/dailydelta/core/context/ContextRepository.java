package uk.contribit.dailydelta.core.context;

public interface ContextRepository  {
    Contexts findOne(String word);

    Contexts save(Contexts contexts);
}
